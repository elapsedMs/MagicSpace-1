package storm.magicspace.download;

import java.io.Serializable;

/**
 * Created by gdq on 16/6/25.
 */
public class FileInfo implements Serializable {
    public String id;
    public String url;
    public int length;
    public String fileName;
    public volatile int finished;
    public boolean isStart;
    public boolean isDownLoadFinish;
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
