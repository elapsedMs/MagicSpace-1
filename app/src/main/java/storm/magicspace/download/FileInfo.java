package storm.magicspace.download;

import java.io.Serializable;

/**
 * Created by gdq on 16/6/25.
 */
public class FileInfo implements Serializable{
    public int id;
    public String url;
    public int length;
    public String fileName;
    public int finished;

    public FileInfo(int id, String url, int length, String fileName, int finished) {
        this.id = id;
        this.url = url;
        this.length = length;
        this.fileName = fileName;
        this.finished = finished;
    }
}
