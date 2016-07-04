package com.gdq.multhreaddownload.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gdq on 16/6/26.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "download.db";
    private static final int VERSION = 2;
    private final String CREATE_THREAD_TABLE = "create table thread_info(_id integer primary key autoincrement,thread_id integer," +
            "url text ,start integer,end integer ,finished integer)";
    private final String CREATE_FINISH_FILE_TABLE = "create table finish_file_info(_id integer primary key autoincrement ,file_id text,url text,length integer," +
            "file_name text,finished integer, is_start integer,isDownLoadFinish integer)";
    private final String CREATE_UN_FINISH_FILE_TABLE = "create table un_finish_file_info(_id integer primary key autoincrement ,file_id text,url text,length integer," +
            "file_name text,finished integer, is_start integer,isDownLoadFinish integer)";
    private final String DROP_TABLE = "drop table if exists thread_info";
    private static DBHelper dbHelper;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_THREAD_TABLE);
        db.execSQL(CREATE_FINISH_FILE_TABLE);
        db.execSQL(CREATE_UN_FINISH_FILE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        db.execSQL(CREATE_THREAD_TABLE);
        db.execSQL(CREATE_FINISH_FILE_TABLE);
        db.execSQL(CREATE_UN_FINISH_FILE_TABLE);
    }
}
