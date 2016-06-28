package storm.magicspace.download.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import storm.magicspace.download.ThreadInfo;

/**
 * Created by gdq on 16/6/26.
 */
public class ThreadDaoImpl implements ThreadDAO {

    private DBHelper dbHelper;

    public ThreadDaoImpl(Context context) {
        this.dbHelper = DBHelper.getInstance(context);
    }

    @Override
    public synchronized void insert(ThreadInfo threadInfo) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("insert into thread_info(thread_id,url,start,end,finished) values (?,?,?,?,?) ", new Object[]{
                threadInfo.id, threadInfo.url, threadInfo.start, threadInfo.end, threadInfo.finished
        });
        database.close();
    }

    @Override
    public synchronized void delete(String url) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("delete from thread_info where url = ? ", new Object[]{
                url
        });
        database.close();
    }

    @Override
    public synchronized void update(String url, int id, int finished) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("update thread_info set finished = ? where url = ? and thread_id = ?", new Object[]{
                finished, url, id
        });
        database.close();
    }

    @Override
    public List<ThreadInfo> getThreads(String url) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from thread_info where url = ?", new String[]{url});
        List<ThreadInfo> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.id = cursor.getInt(cursor.getColumnIndex("thread_id"));
            threadInfo.url = cursor.getString(cursor.getColumnIndex("url"));
            threadInfo.start = cursor.getInt(cursor.getColumnIndex("start"));
            threadInfo.end = cursor.getInt(cursor.getColumnIndex("end"));
            threadInfo.finished = cursor.getInt(cursor.getColumnIndex("finished"));
            list.add(threadInfo);
        }
        database.close();
        cursor.close();
        return list;
    }

    @Override
    public boolean isExists(String url, int id) {
        boolean isExists = false;
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from thread_info where url = ? and thread_id = ?", new String[]{url, "" + id});
        isExists = cursor.moveToNext();
        database.close();
        cursor.close();
        return isExists;
    }
}
