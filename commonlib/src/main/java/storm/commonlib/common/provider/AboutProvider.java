package storm.commonlib.common.provider;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import storm.commonlib.common.util.BaseUtil;
import storm.commonlib.common.util.LogUtil;

import static storm.commonlib.common.util.StringUtil.EMPTY;

public class AboutProvider {
    public static final String MAIN_VERSION_ERROR = "获取主版本异常";
    private final static String TAG = AboutProvider.class.getName();
    public static final String EXCEPTION = "Exception";
    public static final String BUILD_ID = "获取build号";
    public static final String VERSION_ICON = "获取版本图标";
    public static final String ERROR_VERSION = "获取版本号异常";

    private static Context mContext;

    public static void init(Context mContext) {
        AboutProvider.mContext = mContext;
    }

    public static String getAppVersionName(Context context, String name) {
        String versionName = EMPTY;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = name + " v" + pi.versionName;
            if (versionName.length() <= 0) {
                return EMPTY;
            }
        } catch (Exception e) {
            LogUtil.e(TAG, ERROR_VERSION, e);
        }
        return versionName;
    }

    public static String getAppVersionCode(Context context) {
        String versionCode = EMPTY;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = String.valueOf(pi.versionCode);
            if (versionCode.length() <= 0) {
                return EMPTY;
            }
        } catch (Exception e) {
            LogUtil.e(BUILD_ID, EXCEPTION, e);
        }
        return versionCode;
    }

    public static Drawable getAppVersionIcon(Context context) {
        Drawable versionIcon = null;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionIcon = pi.applicationInfo.loadIcon(context.getPackageManager());
            if (versionIcon == null) {
                return null;
            }
        } catch (Exception e) {
            LogUtil.e(VERSION_ICON, EXCEPTION, e);
        }
        return versionIcon;
    }

    public static void openURL(Context context, String url) {
        Uri uri = Uri.parse(url);
        context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
//        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
//        startActivity(it);
    }

    private static String mVersionInfo = null;

    public static String getVersionInfo() {
        if (BaseUtil.isEmpty(mVersionInfo)) {
            mVersionInfo = getVersionInfo("%s %s");
        }
        return mVersionInfo;
    }

    public static String getVersionInfo(String format) {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
            return String.format(format, pi.versionName, pi.versionCode);
        } catch (Exception e) {
            LogUtil.e(TAG, ERROR_VERSION, e);
        }
        return EMPTY;
    }

    private static String mMainVer = null;

    public static String getMainVersion() {
        if (BaseUtil.isEmpty(mMainVer)) {
            try {
                PackageManager pm = mContext.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
                mMainVer = pi.versionName;
            } catch (Exception e) {
                LogUtil.e(TAG, MAIN_VERSION_ERROR, e);
            }
        }
        return mMainVer;
    }
}
