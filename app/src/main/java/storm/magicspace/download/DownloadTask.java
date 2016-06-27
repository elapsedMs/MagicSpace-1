package storm.magicspace.download;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import storm.magicspace.download.db.ThreadDAO;
import storm.magicspace.download.db.ThreadDaoImpl;
import storm.magicspace.event.LengthEvent;

/**
 * Created by gdq on 16/6/26.
 */
public class DownloadTask extends Thread {
    private Context context;
    private FileInfo fileInfo;
    private ThreadDAO threadDAO;
    private int mFinished;
    public boolean isPause = false;

    public DownloadTask(Context context, FileInfo fileInfo) {
        this.context = context;
        this.fileInfo = fileInfo;
        threadDAO = new ThreadDaoImpl(context);
    }

    public void download() {
        //读取数据库信息
        List<ThreadInfo> list = threadDAO.getThreads(fileInfo.url);
        ThreadInfo threadInfo;
        if (list.size() == 0) {
            threadInfo = new ThreadInfo(0, fileInfo.url, 0, fileInfo.length, 0);
        } else {
            threadInfo = list.get(0);
        }
        new DownloadThread(threadInfo).start();
    }

    private class DownloadThread extends Thread {
        private ThreadInfo threadInfo;

        public DownloadThread(ThreadInfo threadInfo) {
            this.threadInfo = threadInfo;
        }

        @Override
        public void run() {
            super.run();
            HttpURLConnection httpURLConnection = null;
            InputStream inputStream = null;
            RandomAccessFile randomAccessFile = null;
            //想数据库插入县城信息
            if (!threadDAO.isExists(threadInfo.url, threadInfo.id))
                threadDAO.insert(threadInfo);
            try {
                httpURLConnection = (HttpURLConnection) new URL(threadInfo.url).openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("GET");
                //设置下载位置
                int start = threadInfo.start + threadInfo.finished;
                Log.d("gdq", "下载起始位置:" + start);
                httpURLConnection.setRequestProperty("Range", "bytes=" + start + "- " + threadInfo.end);
                Log.d("gdq", "1");
                //设置文件写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH, fileInfo.fileName);
                randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.seek(threadInfo.finished);
                //开始下载
                mFinished += threadInfo.finished;
                long time = System.currentTimeMillis();
                if (httpURLConnection.getResponseCode() == HttpStatus.SC_OK) {
                    //读取数据
                    inputStream = httpURLConnection.getInputStream();
                    byte[] bytes = new byte[1024 * 4];
                    int len = -1;
                    while ((len = inputStream.read(bytes)) != -1) {
                        //写入文件
                        randomAccessFile.write(bytes, 0, len);
                        mFinished += len;
                        Log.d("gdq", "" + mFinished);
//                        if (System.currentTimeMillis() - time > 5000) {
//                            time = System.currentTimeMillis();
                        //更新界面进度
//                        LengthEvent lengthEvent = new LengthEvent();
//                        lengthEvent.len = mFinished * 100 / fileInfo.length;
//                        EventBus.getDefault().post(lengthEvent);
//                        }

                        Intent intent = new Intent();
                        intent.setAction(DownloadService.ACTION_UPDATE);
                        intent.putExtra("finished", mFinished * 100 / threadInfo.end);
                        context.sendBroadcast(intent);

                        //暂停，保存进度
                        if (isPause) {
                            threadDAO.update(threadInfo.url, threadInfo.id, mFinished);
                            return;
                        }
                    }
                    Log.d("gdq", "5");
                    threadDAO.delete(threadInfo.url, threadInfo.id);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (httpURLConnection != null)
                        httpURLConnection.disconnect();
                    if (randomAccessFile != null)
                        randomAccessFile.close();
                    if (inputStream != null)
                        inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
