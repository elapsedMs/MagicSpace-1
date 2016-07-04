package com.gdq.multhreaddownload.download;

import android.content.Context;
import android.content.Intent;

import com.gdq.multhreaddownload.download.bean.FileInfo;
import com.gdq.multhreaddownload.download.bean.ThreadInfo;
import com.gdq.multhreaddownload.download.db.ThreadDAO;
import com.gdq.multhreaddownload.download.db.ThreadDaoImpl;

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

import storm.magicspace.event.LengthEvent;

/**
 * Created by gdq on 16/6/26.
 */
public class DownloadTask {
    private Context mContext;
    private FileInfo mFileInfo;
    private ThreadDAO mDao;
    //总进度
    private volatile int mFinised;
    //线程下载暂停开关
    public boolean isPause;
    //分段下载的线程数量
    private int mThreadCount;
    private List<DownloadThread> mDownloadThreadList;
    //线程池，由于大量下载任务
    public static ExecutorService sExecutorService = Executors.newCachedThreadPool();

    public DownloadTask(Context mContext, FileInfo mFileInfo, int count) {
        this.mContext = mContext;
        this.mFileInfo = mFileInfo;
        this.mThreadCount = count;
        mDao = new ThreadDaoImpl(mContext);
    }

    /**
     * 执行下载
     */
    public void downLoad() {
        // 读取数据库的线程信息
        List<ThreadInfo> threads = mDao.getThreadsByUrl(mFileInfo.url);
        ThreadInfo threadInfo;

        //未下载任务或已完成任务数据库不会存在其信息
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
                // 下载前存入数据库
                mDao.insertThread(threadInfo);
            }
        }

        mDownloadThreadList = new ArrayList<>();
        // 启动多个线程进行下载
        for (ThreadInfo info : threads) {
            DownloadThread downloadThread = new DownloadThread(info);
            DownloadTask.sExecutorService.execute(downloadThread);
            mDownloadThreadList.add(downloadThread);
        }
    }

    /**
     * 下载线程
     */
    private class DownloadThread extends Thread {
        private ThreadInfo mThreadInfo;
        // 标志线程执行完毕
        public boolean isFinished = false;

        public DownloadThread(ThreadInfo mInfo) {
            this.mThreadInfo = mInfo;
        }

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
                int start = mThreadInfo.start + mThreadInfo.finished;
                connection.setRequestProperty("Range", "bytes=" + start + "-" + mThreadInfo.end);
                connection.connect();

                // 设置文件写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH, mFileInfo.fileName);
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);

                Intent intent = new Intent();
                intent.setAction(DownloadService.ACTION_UPDATE);

                //累加总进度
                mFinised += mThreadInfo.finished;

                // 开始下载 success:206 error:416
                if (connection.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                    inputStream = connection.getInputStream();
                    byte buf[] = new byte[1024 << 2];
                    int len;
                    long time = System.currentTimeMillis();
                    // 读取数据
                    while ((len = inputStream.read(buf)) != -1) {
                        // 写入文件
                        raf.write(buf);
                        // 累加总进度
                        mFinised += len;
                        // 更新单个线程的进度
                        mThreadInfo.finished = (mThreadInfo.finished + len);

                        if (System.currentTimeMillis() - time > 1000) {
                            time = System.currentTimeMillis();
                            int f = mFinised * 100 / mFileInfo.length;
                            if (f > mFileInfo.finished) {
                                //发送消息更新UI 广播，EventBus 等方式
                                intent.putExtra("finished", f);
                                intent.putExtra("id", mFileInfo.position);
                                mContext.sendBroadcast(intent);

                                EventBus.getDefault().post(new LengthEvent());
                            }
                        }

                        // 在下载暂停时，保存下载进度
                        if (isPause) {
                            mFileInfo.finished = mFinised * 100 / mFileInfo.length;
                            mFileInfo.isDownLoadFinish = false;
                            mFileInfo.isStart = false;
                            mDao.updateUnFinishFileByContentId(mFileInfo);
                            mDao.updateThread(mThreadInfo.url, mThreadInfo.id, mThreadInfo.finished);
                            return;
                        }
                    }

                    //Thread下载完成
                    //标示下载完成
                    isFinished = true;
                    //检测是否该文件的所有线程都完成
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

        //所有的线程是否执行完毕
        if (allFinished) {
            // 删除下载记录
            mDao.deleteThread(mFileInfo.url);
            //删除未完成文件表
            mDao.deleteUnFinishFileByContentId(mFileInfo.id);
            mFileInfo.finished = 100;
            mFileInfo.isStart = false;
            mFileInfo.isDownLoadFinish = true;
            //添加已完成文件表
            mDao.insertFinishFileifNotExists(mFileInfo);

            //发送消息下载任务完成 广播，EventBus 等方式
            Intent intent = new Intent(DownloadService.ACTION_FINISH);
            intent.putExtra("fileInfo", mFileInfo);
            mContext.sendBroadcast(intent);
        }
    }
}