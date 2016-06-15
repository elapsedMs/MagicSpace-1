package storm.commonlib.common.database.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.database.model.ModelBuilder;
import storm.commonlib.common.database.model.ModelFactory;
import storm.commonlib.common.database.model.ModelPatcher;


public class DatabaseActions {

    public static <T> int insertOne(SQLiteDatabase database, String tableName, ModelFactory<T> modelFactory, T tInstance) {
        try {
            database.insert(tableName, null, modelFactory.extractFromModel(tInstance));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return 0;
        }
        return 1;
    }

    public static <T> int insert(SQLiteDatabase database, String tableName, ModelFactory<T> modelFactory, List<T> list) {
        database.beginTransaction();

        try {
            for (T tInstance : list) {
                database.insert(tableName, null, modelFactory.extractFromModel(tInstance));
            }
            database.setTransactionSuccessful();

            return list.size();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return 0;
        } finally {
            database.endTransaction();
        }
    }

    public static <T> List<T> loadList(ModelBuilder<T> modelBuilder, Cursor cursor) {
        ArrayList<T> result = new ArrayList<T>();

        try {
            while (cursor.moveToNext()) {
                T object = modelBuilder.buildModel(cursor);
                result.add(object);
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } finally {
            cursor.close();
        }
        return result;
    }

    public static <T> T loadOne(ModelBuilder<T> modelBuilder, Cursor cursor) {
        return loadOne(modelBuilder, cursor, null);
    }

    public static <T> T loadOne(ModelBuilder<T> modelBuilder, Cursor cursor, T defaultValue) {
        T result = defaultValue;

        try {
            if (cursor.moveToFirst()) {
                result = modelBuilder.buildModel(cursor);
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } finally {
            cursor.close();
        }

        return result;
    }

    public static int loadIntScalar(Cursor cursor) {
        int result = 0;

        try {
            cursor.moveToFirst();
            result = cursor.getInt(0);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } finally {
            cursor.close();
        }

        return result;
    }

    public static String loadString(Cursor cursor) {
        String result = "";

        try {
            cursor.moveToFirst();
            result = cursor.getString(0);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } finally {
            cursor.close();
        }

        return result;
    }

    public static int loadCount(Cursor cursor) {
        int count = 0;

        try {
            count = cursor.getCount();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } finally {
            cursor.close();
        }

        return count;
    }

    public static long loadLongScalar(Cursor cursor) {
        long result = 0;

        try {
            cursor.moveToFirst();
            result = cursor.getLong(0);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } finally {
            cursor.close();
        }

        return result;
    }

    public static int delete(SQLiteDatabase database, String table, String whereClause, String... args) {
        return database.delete(table, whereClause, args);
    }

    public static <T> void patchModel(Cursor cursor, T model, ModelPatcher<T> patcher) {
        try {
            if (cursor.moveToFirst()) {
                patcher.patchObject(cursor, model);
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } finally {
            cursor.close();
        }
    }

    public static String readCursorString(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }

    public static DateTime readCursorDateTime(Cursor cursor, String column) {
        return new DateTime(readCursorLong(cursor, column));
    }

    public static String readCursorString(Cursor cursor, String column, String defaultValue) {
        String value;
        int cityCodeIndex = cursor.getColumnIndex(column);
        if (cityCodeIndex != -1) {
            value = cursor.getString(cityCodeIndex);
        } else {
            value = defaultValue;
        }
        return value;
    }

    public static int readCursorInt(Cursor cursor, String column) {
        return cursor.getInt(cursor.getColumnIndex(column));
    }

    public static long readCursorLong(Cursor cursor, String column) {
        return cursor.getLong(cursor.getColumnIndex(column));
    }

    public static float readCursorFloat(Cursor cursor, String column) {
        return cursor.getFloat(cursor.getColumnIndex(column));
    }

    public static <T extends Enum<T>> T readCursorEnum(Cursor cursor, Class<T> enumClass, String column) {
        return enumClass.getEnumConstants()[cursor.getInt(cursor.getColumnIndex(column))];
    }

    public static boolean readCursorBoolean(Cursor cursor, String column) {
        return cursor.getInt(cursor.getColumnIndex(column)) == 1;
    }

    public static boolean update(SQLiteDatabase database, String tableName, ContentValues values, String whereClause, String... whereArgs) {
        return database.update(tableName, values, whereClause, whereArgs) > 0;
    }
}