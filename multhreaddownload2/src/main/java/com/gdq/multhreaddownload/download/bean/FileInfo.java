package com.gdq.multhreaddownload.download.bean;

import java.io.Serializable;

/**
 * Created by gdq on 16/6/25.
 */
public class FileInfo implements Serializable {
    public String id;
    public String url;
    public int length;
    public String fileName;
    //完成进度百分比
    public volatile int finished;
    //是否在执行下载中
    public boolean isStart;
    //是否下载完毕
    public boolean isDownLoadFinish;
    //用于在list等列表中保存当前位置
    public int position;

    public FileInfo() {
    }

    public FileInfo(String id, String url, int length, String fileName, int finished, boolean isStart, boolean isDownLoadFinish) {
        this.id = id;
        this.url = url;
        this.length = length;
        this.fileName = fileName;
        this.finished = finished;
        this.isStart = isStart;
        this.isDownLoadFinish = isDownLoadFinish;
    }
}
