package com.gdq.multhreaddownload.download.bean;

/**
 * Created by gdq on 16/6/26.
 */
public class ThreadInfo {
    public int id;
    public String url;
    //下载开始位置
    public int start;
    //下载结束位置
    public int end;
    //完成字节数
    public int finished;

    public ThreadInfo() {
    }

    public ThreadInfo(int id, String url, int start, int end, int finished) {
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
        this.finished = finished;
    }
}
