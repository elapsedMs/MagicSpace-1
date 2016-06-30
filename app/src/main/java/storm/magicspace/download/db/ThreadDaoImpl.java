package storm.magicspace.download.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import storm.magicspace.download.FileInfo;
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
    public synchronized void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("insert into thread_info(thread_id,url,start,end,finished) values (?,?,?,?,?) ", new Object[]{
                threadInfo.id, threadInfo.url, threadInfo.start, threadInfo.end, threadInfo.finished
        });
        database.close();
    }

    @Override
    public synchronized void deleteThread(String url) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("delete from thread_info where url = ? ", new Object[]{
                url
        });
        database.close();
    }

    @Override
    public synchronized void updateThread(String url, int id, int finished) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("update thread_info set finished = ? where url = ? and thread_id = ?", new Object[]{
                finished, url, id
        });
        database.close();
    }

    @Override
    public List<ThreadInfo> getThreadsByUrl(String url) {
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
    public boolean isThreadExists(String url, int id) {
        boolean isExists = false;
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from thread_info where url = ? and thread_id = ?", new String[]{url, "" + id});
        isExists = cursor.moveToNext();
        database.close();
        cursor.close();
        return isExists;
    }

    @Override
    public synchronized void insertUnFinishFileifNotExists(FileInfo fileInfo) {
        if (isUnFinishExists(fileInfo.id)) return;
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("insert into un_finish_file_info(file_id ,url ,length ,file_name ,finished , is_start ,isDownLoadFinish ) values(?,?,?,?,?,?,?)", new Object[]{
                fileInfo.id, fileInfo.url, fileInfo.length, fileInfo.fileName, fileInfo.finished, fileInfo.isStart ? 1 : 0, fileInfo.isDownLoadFinish ? 1 : 0
        });
        database.close();
    }

    @Override
    public synchronized void insertFinishFileifNotExists(FileInfo fileInfo) {
        if (isFinishFileExists(fileInfo.id))
            return;
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("insert into finish_file_info(file_id ,url ,length ,file_name ,finished , is_start ,isDownLoadFinish ) values(?,?,?,?,?,?,?)", new Object[]{
                fileInfo.id, fileInfo.url, fileInfo.length, fileInfo.fileName, fileInfo.finished, fileInfo.isStart ? 1 : 0, fileInfo.isDownLoadFinish ? 1 : 0
        });
        database.close();
    }

    @Override
    public synchronized void deleteUnFinishFileByContentId(String contentId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("delete from un_finish_file_info where file_id=?", new Object[]{
                contentId
        });
        database.close();
    }

    @Override
    public synchronized void deleteFinishFileByContentId(String contentId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("delete from finish_file_info where file_id=?", new Object[]{
                contentId
        });
        database.close();
    }

    @Override
    public List<FileInfo> getAllFinishFile() {
        List<FileInfo> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from finish_file_info", new String[]{});
        while (cursor.moveToNext()) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.id = cursor.getString(cursor.getColumnIndex("file_id"));
            fileInfo.url = cursor.getString(cursor.getColumnIndex("url"));
            fileInfo.length = cursor.getInt(cursor.getColumnIndex("length"));
            fileInfo.fileName = cursor.getString(cursor.getColumnIndex("file_name"));
            fileInfo.finished = cursor.getInt(cursor.getColumnIndex("finished"));
            fileInfo.isStart = (cursor.getInt(cursor.getColumnIndex("is_start")) == 1);
            fileInfo.isDownLoadFinish = (cursor.getInt(cursor.getColumnIndex("isDownLoadFinish")) == 1);
            list.add(fileInfo);
        }
        cursor.close();
        database.close();
        return list;
    }

    @Override
    public List<FileInfo> getAllUnFinishFile() {
        List<FileInfo> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from un_finish_file_info", new String[]{});
        while (cursor.moveToNext()) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.id = cursor.getString(cursor.getColumnIndex("file_id"));
            fileInfo.url = cursor.getString(cursor.getColumnIndex("url"));
            fileInfo.length = cursor.getInt(cursor.getColumnIndex("length"));
            fileInfo.fileName = cursor.getString(cursor.getColumnIndex("file_name"));
            fileInfo.finished = cursor.getInt(cursor.getColumnIndex("finished"));
            fileInfo.isStart = (cursor.getInt(cursor.getColumnIndex("is_start")) == 1);
            fileInfo.isDownLoadFinish = (cursor.getInt(cursor.getColumnIndex("isDownLoadFinish")) == 1);
            list.add(fileInfo);
        }
        cursor.close();
        database.close();
        return list;
    }

    @Override
    public synchronized void updateFinishFileByContentId(FileInfo fileInfo) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("update finish_file_info set isDownLoadFinish=? ,finished=?,is_start=? where  file_id=?", new Object[]{fileInfo.isDownLoadFinish ? 1 : 0, fileInfo.finished, fileInfo.isStart ? 1 : 0, fileInfo.id});
        database.close();
    }

    @Override
    public void updateUnFinishFileByContentId(FileInfo fileInfo) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("update un_finish_file_info set isDownLoadFinish=? ,finished=?,is_start=? where  file_id=?", new Object[]{fileInfo.isDownLoadFinish ? 1 : 0, fileInfo.finished, fileInfo.isStart ? 1 : 0, fileInfo.id});
        database.close();
    }

    public boolean isFinishFileExists(String contentid) {
        boolean isExists = false;
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from finish_file_info where file_id = ? ", new String[]{contentid});
        isExists = cursor.moveToNext();
        database.close();
        cursor.close();
        return isExists;
    }

    public boolean isUnFinishExists(String contentid) {
        boolean isExists = false;
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from un_finish_file_info where file_id = ? ", new String[]{contentid});
        isExists = cursor.moveToNext();
        database.close();
        cursor.close();
        return isExists;
    }

}
