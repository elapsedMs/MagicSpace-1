package storm.commonlib.common.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Field;

import storm.commonlib.common.CommonConstants;

public class ViewUtil {

    public static void showToast(Context context, String text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    public static void showToast(Context context, int text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    public static void showToast(Context context, String text) {
        ViewUtil.showToast(context, text, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, int text) {
        ViewUtil.showToast(context, text, Toast.LENGTH_SHORT);
    }

    public static <T extends View> T getView(View view) {
        return view == null ? null : (T) view;
    }

    public static <T extends View> T findView(Activity activity, int id) {
        return ViewUtil.getView(activity.findViewById(id));
    }

    public static <T extends View> T findView(View view, int id) {
        return ViewUtil.getView(view.findViewById(id));
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * DP转换为PX
     *
     * @param context 上下文
     * @param dp      需要转换的DP值
     * @return PX值
     */
    public static float dip2px(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
            Rect frame = new Rect();
            ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            statusBarHeight = frame.top;
        }
        return statusBarHeight;
    }

    //================进度条dialog======================
    private static ProgressDialog mProgressDialog;

    /**
     * 显示进度条
     *
     * @param context          上下文环境
     * @param title            标题
     * @param message          信息
     * @param indeterminate    是否指定进度条
     * @param onCancelListener 取消处理
     * @return 进度条对象
     */
    public static ProgressDialog showProgress(Context context,
                                              CharSequence title, CharSequence message, boolean indeterminate,
                                              DialogInterface.OnCancelListener onCancelListener) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIndeterminate(indeterminate);
        if (onCancelListener == null) {
            dialog.setCancelable(false);
        } else {
            dialog.setCancelable(true);
            dialog.setOnCancelListener(onCancelListener);
        }
        // dialog.setDefaultButton(false);

        closeProgress();
        mProgressDialog = dialog;

        dialog.show();
        return dialog;
    }

    /**
     * 关闭进度显示
     */
    public static void closeProgress() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteDir(File file) {
        if (file == null || !file.exists()) return;
        if (file.isDirectory()) {
            for (File item : file.listFiles()) {
                deleteDir(item);
            }
            file.delete();
        } else {
            file.delete();
        }
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    public static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    public static void call(Activity activity) {
        activity.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + CommonConstants.LITTLE_HELPER_PHONE_NUMBER)));
    }

}