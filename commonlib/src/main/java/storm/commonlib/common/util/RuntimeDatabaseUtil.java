package storm.commonlib.common.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static storm.commonlib.common.BaseApplication.getApplication;
import static storm.commonlib.common.util.SharedPreferencesUtil.getLoginAccountId;


public class RuntimeDatabaseUtil extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DB_PREFIX_NAME = "MedTree_";
    public static final String DB_SUFFIX_NAME = ".db";
    public static final int NEW_MESSAGE = 0;

    private static RuntimeDatabaseUtil instance;
    private static DatabaseMonitor monitor;

    public static RuntimeDatabaseUtil init() {
        return getDatabaseHelperInstance(getApplication(), getLoginAccountId());
    }

    public static RuntimeDatabaseUtil getInstance() {
        return init();
    }

    public static RuntimeDatabaseUtil getInstance(DatabaseMonitor monitor) {
        RuntimeDatabaseUtil.monitor = monitor;
        return init();
    }

    public static synchronized RuntimeDatabaseUtil getDatabaseHelperInstance(Context applicationContext, String account) {
        return instance == null ? (instance = new RuntimeDatabaseUtil(applicationContext, account)) : instance;
    }

    protected RuntimeDatabaseUtil(Context context, String accountId) {
        super(context, DB_PREFIX_NAME + accountId + DB_SUFFIX_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    }

    public interface DatabaseMonitor {
        void onDatabaseChanged(int type);
    }
}