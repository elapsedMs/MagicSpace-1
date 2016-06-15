package storm.commonlib.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import static storm.commonlib.common.util.StringUtil.EMPTY;


public class AppUtil {

    public static final String APP_UTILS = "AppUtils";
    public static final String ERROR_VERSION = "获取主版本版本异常";
    public static final String ANDROID = "android";
    public static final String NO_DEVICE_ID = "no device ID";
    public static final String GET_DEVICE_ID_ERROR = "getDeviceID error";

    public static String getDeviceId(Context context) {
        TelephonyManager tm = null;
        try {
            tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        } catch (Exception ex) {
            LogUtil.e(APP_UTILS, GET_DEVICE_ID_ERROR, ex);
        }
        if (tm == null)
            return NO_DEVICE_ID;
        else
            return tm.getDeviceId();
    }

    public static String getVersionInfo(Context context, String format) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return String.format(format, pi.versionName, pi.versionCode);
        } catch (Exception e) {
            LogUtil.e(APP_UTILS, ERROR_VERSION, e);
        }
        return EMPTY;
    }

    public static int getWindowWidth(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    public static void setSoftInputVisibility(Activity activity, boolean visibility) {
        int mode = visibility ? WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE : WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
        activity.getWindow().setSoftInputMode(mode);
    }

    public static String getMainVersion(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (Exception e) {
            LogUtil.e(APP_UTILS, ERROR_VERSION, e);
        }
        return EMPTY;
    }

    public static String getClientType() {
        return ANDROID;
    }
}