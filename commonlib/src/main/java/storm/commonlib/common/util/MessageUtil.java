package storm.commonlib.common.util;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import storm.commonlib.R;

import static android.widget.Toast.LENGTH_SHORT;
import static storm.commonlib.common.BaseApplication.getApplication;

public class MessageUtil {
    protected static Handler mHandler;
    static long slagTime = System.currentTimeMillis();
    private final static String TAG = MessageUtil.class.getName();

    public static void showMessage(int messageResId) {
        Toast.makeText(getApplication(), getApplication().getString(messageResId), LENGTH_SHORT).show();
    }

    public static void showMessage(String message) {
        Toast.makeText(getApplication(), message, LENGTH_SHORT).show();
    }

    public static void showHttpBaseMessage(final String message) {
        if (System.currentTimeMillis() - slagTime < 3 * 1000) return;
        slagTime = System.currentTimeMillis();
        try {
            mHandler = mHandler == null ? new Handler(Looper.getMainLooper()) : mHandler;

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (getApplication() == null) return;
                    Toast.makeText(getApplication(), message, LENGTH_SHORT).show();
                }
            };
            mHandler.post(runnable);
        } catch (Exception e) {
            LogUtil.e(TAG, getApplication().getString(R.string.http_toast_error), e);
        }
    }

}
