package storm.magicspace.download;

/**
 * Created by gdq on 16/6/26.
 */
public class  ThreadInfo {
    public int id;
    public String url;
    public int start;
    public int end;
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
