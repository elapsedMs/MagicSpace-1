package com.gdq.multhreaddownload.download;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.gdq.multhreaddownload.download.bean.FileInfo;
import com.gdq.multhreaddownload.download.event.FileInfoEvent;

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


/**
 * Created by gdq on 16/6/25.
 */
public class DownloadService extends Service {
    //路径
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/gdq/download/";
    //下载状态
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String ACTION_FINISH = "ACTION_FINISH";
    //外部传入参数
    private FileInfo fileInfo;
    //存储下载任务
    private Map<String, DownloadTask> downloadTaskMap = new LinkedHashMap<>();

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
        super.onCreate();
    }

    /**
     * 获取长度后回调
     * @param fileInfoEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FileInfoEvent fileInfoEvent) {
        DownloadTask downloadTask = new DownloadTask(this, fileInfoEvent.fileInfo, 3);
        downloadTask.downLoad();
        downloadTaskMap.put(fileInfo.id, downloadTask);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        fileInfo = (FileInfo) intent.getSerializableExtra("file_info");

        if (action.equals(ACTION_START)) {
            DownloadTask.sExecutorService.execute(new LengthThread(fileInfo));
        } else if (action.equals(ACTION_STOP)) {
            DownloadTask task = downloadTaskMap.get(fileInfo.id);
            if (task != null) {
                //控制下载while循环暂停
                task.isPause = true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 获取文件长度
     */
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
                httpURLConnection.setReadTimeout(4000);
                httpURLConnection.setRequestMethod("GET");
                //为了正常获取文件长度
                httpURLConnection.setRequestProperty("Accept-Encoding", "identity");
                httpURLConnection.connect();

                int len = 0;
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //获取文件长度
                    len = httpURLConnection.getContentLength();
                }
                if (len < 0) {
                    Toast.makeText(DownloadService.this, "获取文件长度失败", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isMk;
                File dir = new File(DOWNLOAD_PATH);
                isMk = dir.exists() || dir.mkdirs();
                if (!isMk) return;

                //本地创建文件
                File file = new File(dir, fileInfo.fileName);
                randomAccessFile = new RandomAccessFile(file, "rwd");
                //设置文件长度
                randomAccessFile.setLength(len);
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
