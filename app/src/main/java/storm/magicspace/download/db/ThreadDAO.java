package storm.magicspace.download.db;

import java.util.List;

import storm.magicspace.download.ThreadInfo;

/**
 * Created by gdq on 16/6/26.
 */
public interface ThreadDAO {
    public void insert(ThreadInfo threadInfo);

    public void delete(String url, int id);

    public void update(String url, int id,int finished);

    public List<ThreadInfo> getThreads(String url);

    public boolean isExists(String url, int id);

}
