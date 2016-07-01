package storm.magicspace.download;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.httpclient.HttpStatus;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import storm.magicspace.base.BaseService;
import storm.magicspace.event.FileInfoEvent;

/**
 * Created by gdq on 16/6/25.
 */
public class DownloadService extends BaseService {
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/magic/download/";
    public static final String ACTION_FINISH = "ACTION_FINISH";
    private FileInfo fileInfo;
    private Map<String, DownloadTask> downloadTaskMap = new LinkedHashMap<>();

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
        super.onCreate();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FileInfoEvent fileInfoEvent) {
        Log.d("gdq", "接受文件长度");
        DownloadTask downloadTask = new DownloadTask(this, fileInfoEvent.fileInfo, 3);
        downloadTask.downLoad();
        downloadTaskMap.put(fileInfo.id, downloadTask);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        Log.d("gdq", "onStartCommand     Action :" + action);
        fileInfo = (FileInfo) intent.getSerializableExtra("file_info");

        if (action.equals(ACTION_START)) {
            if (fileInfo.length == 0) {
                DownloadTask.sExecutorService.execute(new LengthThread(fileInfo));
            } else {
                DownloadTask downloadTask = new DownloadTask(this, fileInfo, 3);
                downloadTask.downLoad();
                downloadTaskMap.put(fileInfo.id, downloadTask);
            }
        } else if (action.equals(ACTION_STOP)) {
            DownloadTask task = downloadTaskMap.get(fileInfo.id);
            if (task != null) {
                task.isPause = true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private class LengthThread extends Thread {
        FileInfo fileInfo;

        public LengthThread(FileInfo fileInfo) {
            this.fileInfo = fileInfo;
        }

        @Override
        public void run() {
            super.run();
            HttpURLConnection httpURLConnection = null;
            RandomAccessFile randomAccessFile = null;
            try {
                //链接网络
                URL url = new URL(fileInfo.url);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("GET");
                int len = 0;
                Log.d("gdq", "获取文件长度返回值:" + httpURLConnection.getResponseCode());
                if (httpURLConnection.getResponseCode() == HttpStatus.SC_OK) {
                    //获取文件changdu
                    len = httpURLConnection.getContentLength();
                    Log.d("gdq", "长度:" + len);
                }
                if (len <= 0) {
                    Toast.makeText(DownloadService.this, "获取文件长度失败", 1).show();
                    return;
                }

                boolean isMk = false;
                File dir = new File(DOWNLOAD_PATH);
                if (dir.exists()) {
                    isMk = true;
                } else {
                    isMk = dir.mkdirs();
                }
                if (!isMk) return;
                Log.d("gdq", "创建文件");
                //本地创建文件
                File file = new File(dir, fileInfo.fileName);
                randomAccessFile = new RandomAccessFile(file, "rwd");
                //设置文件长度
                randomAccessFile.setLength(len);
                Log.d("gdq", "准备返回");
                //返回长度
                fileInfo.length = len;
                FileInfoEvent fileInfoEvent = new FileInfoEvent();
                fileInfoEvent.fileInfo = fileInfo;
                EventBus.getDefault().post(fileInfoEvent);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (httpURLConnection != null)
                        httpURLConnection.disconnect();
                    if (randomAccessFile != null)
                        randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().register(this);
    }
}
