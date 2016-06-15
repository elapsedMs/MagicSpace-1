package storm.commonlib.common.util;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit.RetrofitError;


/**
 * Created by gdq on 16/1/18.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static CrashHandler crashHandler = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    public static CrashHandler getInstance() {
        return crashHandler;
    }

    public void init() {
        mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        LogUtil.d("gdq", ex.toString());

//        if (mDefaultUncaughtExceptionHandler != null) {
//            LogUtil.d("gdq","if");
//            mDefaultUncaughtExceptionHandler.uncaughtException(thread,ex);
//            return;
//        }else{
        ActivityCollector.getInstance().exit();

//        }
    }


    private boolean handleMedException(Throwable throwable) {
        if (throwable == null) {
            return false;
        }
        if (throwable instanceof RetrofitError) {
            return false;
        } else if (throwable instanceof ConnectException) {
            return false;
        } else if (throwable instanceof UnknownHostException) {
            return false;
        } else if (throwable instanceof SocketTimeoutException) {
            return false;
        }
        return true;
    }
}
