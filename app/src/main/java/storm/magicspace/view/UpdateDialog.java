package storm.magicspace.view;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import storm.commonlib.common.util.ClickUtil;
import storm.commonlib.common.util.LogUtil;
import storm.magicspace.R;
import storm.magicspace.bean.CheckUpdate;

/**
 * Created with IntelliJ IDEA.
 * User: Mobimind
 * Date: 12-11-14
 * Time: 下午3:54
 * To change this template use File | Settings | File Templates.
 */
public class UpdateDialog {
    private static final String TAG = UpdateDialog.class.getSimpleName();
    private AlertDialog mDialog;
    private Context mContext;
    private CheckUpdate checkUpdate;
    private OnDialogCancelListener onDialogCancelListener;

    public UpdateDialog(Context context, CheckUpdate checkUpdate, OnDialogCancelListener onDialogCancelListener) {
        this.mContext = context;
        this.checkUpdate = checkUpdate;
        AssembleDialog();
        this.onDialogCancelListener = onDialogCancelListener;

    }

    private void AssembleDialog() {
        View view = View.inflate(mContext, R.layout.ym_layout_update_ordinarydialog, null);
        TextView title = (TextView) view.findViewById(R.id.tv_update_ordinary_title);
        title.setText("更新版本  " + checkUpdate.versionCode);
        WebView context = (WebView) view.findViewById(R.id.webview_update_ordinary_context);
        final String mimeType = "text/html";
        final String encoding = "utf-8";
        context.loadDataWithBaseURL(null, checkUpdate.versionMessage, mimeType, encoding, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        if (Integer.parseInt(checkUpdate.forceInstall) > 0) {
            dialogBuilder.setTitle("重要更新");
            dialogBuilder.setMessage("这是一次很重要的更新，您的当前版本不可使用，请立刻下载。");
            dialogBuilder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (ClickUtil.isFastClick()) {
                        return;
                    }
                    downloadAndInstall(checkUpdate);
                }
            });
        }
        if (Integer.parseInt(checkUpdate.forceInstall) == 0) {
            dialogBuilder.setTitle("更新软件");
            dialogBuilder.setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onDialogCancelListener.onCancel();
                }
            });
            dialogBuilder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (ClickUtil.isFastClick()) {
                        return;
                    }
                    downloadAndInstall(checkUpdate);
                }
            });
        }
        dialogBuilder.setView(view);
        // 返回键不能关闭dialog
        dialogBuilder.setCancelable(false);
        dialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                    return true;
                } else {
                    return false; //默认返回 false
                }
            }
        });

        mDialog = dialogBuilder.create();
    }

    public void showDialog() {
        if (mDialog != null && mContext != null)
            mDialog.show();
    }

    public void closeDialog() {
        if (mDialog != null && mContext != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public void downloadAndInstall(CheckUpdate checkUpdate) {
//        if(!ExistSDCard()){
//            Toast.makeText(mContext, "SD卡不存在", Toast.LENGTH_LONG).show();
//            return;
//        }
        String url = checkUpdate.apkPath;
        LogUtil.d(TAG, "url = " + url);
        String title = "magicspace";// = appInfo.getName();
        String description = title + ".apk";
        String apkName = title + ".apk";

        File fileDownloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!fileDownloads.exists()) fileDownloads.mkdirs();
        final File fileApk = new File(fileDownloads, apkName);
        if (fileApk.exists()) fileApk.delete();

        try {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setTitle(title);
            request.setDescription(description);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, apkName);
            request.setShowRunningNotification(true);
            if (Build.VERSION.SDK_INT >= 11) {    // 仅11以上版本支持
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            request.setVisibleInDownloadsUi(true);

            final DownloadManager dm = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
            final long enqueue = dm.enqueue(request);
            mContext.registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                        DownloadManager.Query query = new DownloadManager.Query();
                        query.setFilterById(enqueue);
                        Cursor c = dm.query(query);
                        if (c.moveToFirst()) {
                            int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                            if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                                String path = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                                Intent i = new Intent();
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.setAction(Intent.ACTION_VIEW);
                                i.setDataAndType(Uri.parse(path), "application/vnd.android.package-archive");

                                context.startActivity(i);
                                context.unregisterReceiver(this);
                            }
                        }
                    }
                }
            }, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        } catch (Exception e) {
            Toast.makeText(mContext, "下载失败，请访问官网下载安装最新版本", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * SD卡剩余空间
     *
     * @return
     */
    public long getSDFreeSize() {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; //单位MB
    }

    /**
     * SD卡是否存在
     *
     * @return
     */
    private boolean ExistSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    public interface OnDialogCancelListener {
        void onCancel();
    }
}
