package storm.magicspace.download;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import org.apache.commons.httpclient.HttpStatus;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import storm.magicspace.base.BaseService;
import storm.magicspace.event.FileInfoEvent;

/**
 * Created by gdq on 16/6/25.
 */
public class DownloadService extends BaseService {
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/magic/download/";
    private FileInfo fileInfo;
    private DownloadTask downloadTask;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FileInfoEvent fileInfoEvent) {
        Log.d("gdq", "接受文件长度");
        downloadTask = new DownloadTask(this, fileInfoEvent.fileInfo);
        downloadTask.download();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("gdq", "onStartCommand");
        String action = intent.getAction();
        fileInfo = (FileInfo) intent.getSerializableExtra("file_info");
        if (action.equals(ACTION_START)) {
            new LengthThread(fileInfo).start();
        } else if (action.equals(ACTION_STOP)) {
            if (downloadTask != null)
                downloadTask.isPause = true;
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
                if (httpURLConnection.getResponseCode() == HttpStatus.SC_OK) {
                    //获取文件changdu
                    len = httpURLConnection.getContentLength();
                }
                if (len <= 0) return;

                boolean isMk = false;
                File dir = new File(DOWNLOAD_PATH);
                if (dir.exists()) {
                    isMk = true;
                } else {
                    isMk = dir.mkdirs();
                }
                if (!isMk) return;
                //本地创建文件
                File file = new File(dir, fileInfo.fileName);
                randomAccessFile = new RandomAccessFile(file, "rwd");
                //设置文件长度
                randomAccessFile.setLength(len);
                //返回长度
                fileInfo.length = len;
                EventBus.getDefault().post(fileInfo);

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
