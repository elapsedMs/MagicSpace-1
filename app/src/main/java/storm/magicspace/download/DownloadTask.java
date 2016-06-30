package storm.magicspace.download;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.apache.commons.httpclient.HttpStatus;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import storm.magicspace.download.db.ThreadDAO;
import storm.magicspace.download.db.ThreadDaoImpl;
import storm.magicspace.event.LengthEvent;

/**
 * Created by gdq on 16/6/26.
 */
public class DownloadTask {
    private Context mContext = null;
    private FileInfo mFileInfo = null;
    private ThreadDAO mDao = null;
    private volatile int mFinised = 0;
    public boolean isPause = false;
    private int mThreadCount = 1;  // 线程数量
    private List<DownloadThread> mDownloadThreadList = null; // 线程集合
    public static ExecutorService sExecutorService = Executors.newCachedThreadPool();

    /**
     * @param mContext
     * @param mFileInfo
     */
    public DownloadTask(Context mContext, FileInfo mFileInfo, int count) {
        this.mContext = mContext;
        this.mFileInfo = mFileInfo;
        this.mThreadCount = count;
        mDao = new ThreadDaoImpl(mContext);
    }

    public void downLoad() {
        // 读取数据库的线程信息
        List<ThreadInfo> threads = mDao.getThreadsByUrl(mFileInfo.url);
        ThreadInfo threadInfo = null;

        if (0 == threads.size()) {
            // 计算每个线程下载长度
            int len = mFileInfo.length / mThreadCount;
            for (int i = 0; i < mThreadCount; i++) {
                // 初始化线程信息对象
                threadInfo = new ThreadInfo(i, mFileInfo.url,
                        len * i, (i + 1) * len - 1, 0);

                if (mThreadCount - 1 == i)  // 处理最后一个线程下载长度不能整除的问题
                {
                    threadInfo.end = (mFileInfo.length);
                }

                // 添加到线程集合中
                threads.add(threadInfo);
                mDao.insertThread(threadInfo);
            }
        }

        mDownloadThreadList = new ArrayList<DownloadTask.DownloadThread>();
        // 启动多个线程进行下载
        for (ThreadInfo info : threads) {
            DownloadThread downloadThread = new DownloadThread(info);
            //            downloadThread.start();
            DownloadTask.sExecutorService.execute(downloadThread);
            mDownloadThreadList.add(downloadThread);
        }
    }

    /**
     * 下载线程
     *
     * @author Yann
     * @date 2015-8-8 上午11:18:55
     */
    private class DownloadThread extends Thread {
        private ThreadInfo mThreadInfo = null;
        public boolean isFinished = false;  // 线程是否执行完毕

        /**
         * @param mInfo
         */
        public DownloadThread(ThreadInfo mInfo) {
            this.mThreadInfo = mInfo;
        }

        /**
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile raf = null;
            InputStream inputStream = null;

            try {
                URL url = new URL(mThreadInfo.url);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                // 设置下载位置
                int start = mThreadInfo.start + mThreadInfo.finished;
                connection.setRequestProperty("Range",
                        "bytes=" + start + "-" + mThreadInfo.end);
                // 设置文件写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH,
                        mFileInfo.fileName);
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);
                Intent intent = new Intent();
                intent.setAction(DownloadService.ACTION_UPDATE);
                mFinised += mThreadInfo.finished;
                Log.i("mFinised", mThreadInfo.id + "finished = " + mThreadInfo.finished);
                // 开始下载
                Log.d("gdq", "" + connection.getResponseCode());
                if (connection.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT || connection.getResponseCode() == HttpStatus.SC_OK) {
                    Log.d("zcs", "Thread id: " + mThreadInfo.id); // 读取数据
                    inputStream = connection.getInputStream();
                    byte buf[] = new byte[1024 << 2];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    Log.d("zcs", "Thread id: " + mThreadInfo.id + "    len:" + inputStream.read(buf)); // 读取数据
                    ;
                    while ((len = inputStream.read(buf)) != -1) {
                        // 写入文件
                        raf.write(buf, 0, len);
                        // 累加整个文件完成进度
                        mFinised += len;
                        // 累加每个线程完成的进度
                        mThreadInfo.finished = (mThreadInfo.finished + len);
                        Log.d("hkh", "Thread Id:" + mThreadInfo.id + "      已完成：" + mFinised + "    总长度：" + mFileInfo.length + "   len:" + len);
                        if (System.currentTimeMillis() - time > 1000) {
                            time = System.currentTimeMillis();
                            int f = mFinised * 100 / mFileInfo.length;
                            Log.d("gzq", f + "");
                            if (f > mFileInfo.finished) {
                                intent.putExtra("finished", f);
                                intent.putExtra("id", mFileInfo.id);
                                mContext.sendBroadcast(intent);
                                EventBus.getDefault().post(new LengthEvent());
                            }
                        }

                        // 在下载暂停时，保存下载进度
                        if (isPause) {
                            mDao.updateUnFinishFileByContentId(mFileInfo);
                            mDao.updateThread(mThreadInfo.url,
                                    mThreadInfo.id,
                                    mThreadInfo.finished);

                            Log.i("mThreadInfo", mThreadInfo.id + "finished = " + mThreadInfo.finished);

                            return;
                        }
                    }

                    // 标识线程执行完毕
                    isFinished = true;
                    checkAllThreadFinished();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (raf != null) {
                        raf.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断所有的线程是否执行完毕
     *
     * @return void
     * @author Yann
     * @date 2015-8-9 下午1:19:41
     */
    private synchronized void checkAllThreadFinished() {
        boolean allFinished = true;

        // 遍历线程集合，判断线程是否都执行完毕
        for (DownloadThread thread : mDownloadThreadList) {
            if (!thread.isFinished) {
                allFinished = false;
                break;
            }
        }

        if (allFinished) {
            // 删除下载记录
            mDao.deleteThread(mFileInfo.url);
            mDao.deleteUnFinishFileByContentId(mFileInfo.id);
            mDao.insertFinishFileifNotExists(mFileInfo);
            // 发送广播知道UI下载任务结束
            Intent intent = new Intent(DownloadService.ACTION_FINISH);
            intent.putExtra("fileInfo", mFileInfo);
            mContext.sendBroadcast(intent);
        }
    }
}