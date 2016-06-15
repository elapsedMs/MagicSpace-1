package storm.commonlib.common.util;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.google.inject.Inject;

public class DeviceInfoUtil {
    private final static String TAG = DeviceInfoUtil.class.getName();

    @Inject
    private static Context context;

    public static void init(Context mContext) {
        DeviceInfoUtil.context = mContext;
    }

    private static String deviceID = null;

    public static String getClientType() {
        return "android";
    }

    /**
     * 获取唯一的设备ID
     *
     * @return
     */
    public static String getDeviceID() {
        return getDeviceID(context);
    }

    /**
     * 获取唯一的设备ID
     *
     * @return
     */
    public static String getDeviceID(Context context) {
        if (BaseUtil.isEmpty(deviceID)) {
            TelephonyManager tm = null;
            try {
                tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
            } catch (Exception ex) {
                LogUtil.e(TAG, "getDeviceID error", ex);
            }
            if (tm == null)
                deviceID = "no device ID";
            else
                deviceID = tm.getDeviceId();
        }
        return deviceID;
    }

    public static boolean registerDeviceInfo() {
        return true;

//        Build bd = new Build();
//        String model = bd.MODEL;
//
//
//        String sdk=android.os.Build.VERSION.SDK;    // SDK号
//        String release=android.os.Build.VERSION.RELEASE;  // android系统版本号
//
//        System.out.println("model="+model);
//        System.out.println("android.os.Build.MODEL="+android.os.Build.MODEL);
//        System.out.println("sdk="+sdk);
//        System.out.println("osversion="+android.os.Build.VERSION.RELEASE);
//
//        // 在manifest.xml文件中要添加 <uses-permission android:name="android.permission.READ_PHONE_STATE" />
//        TelephonyManager tm = (TelephonyManager) mCountext.getSystemService(Activity.TELEPHONY_SERVICE);
//        /*
//        * 唯一的设备ID：
//        * GSM手机的 IMEI 和 CDMA手机的 MEID.
//        * Return null if device ID is not available.
//        */
//        tm.getDeviceId();//String
//
//        /*
//        * 设备的软件版本号：
//        * 例如：the IMEI/SV(software version) for GSM phones.
//        * Return null if the software version is not available.
//        */
//        tm.getDeviceSoftwareVersion();//String
//        /*
//        * 手机号：
//        * GSM手机的 MSISDN.
//        * Return null if it is unavailable.
//        */
//        tm.getLine1Number();//String
//        /*
//        * 当前使用的网络类型：
//        * 例如： NETWORK_TYPE_UNKNOWN  网络类型未知  0
//          NETWORK_TYPE_GPRS     GPRS网络  1
//          NETWORK_TYPE_EDGE     EDGE网络  2
//          NETWORK_TYPE_UMTS     UMTS网络  3
//          NETWORK_TYPE_HSDPA    HSDPA网络  8
//          NETWORK_TYPE_HSUPA    HSUPA网络  9
//          NETWORK_TYPE_HSPA     HSPA网络  10
//          NETWORK_TYPE_CDMA     CDMA网络,IS95A 或 IS95B.  4
//          NETWORK_TYPE_EVDO_0   EVDO网络, revision 0.  5
//          NETWORK_TYPE_EVDO_A   EVDO网络, revision A.  6
//          NETWORK_TYPE_1xRTT    1xRTT网络  7
//        */
//        tm.getNetworkType();//int
//        /*
//        * 手机类型：
//        * 例如： PHONE_TYPE_NONE  无信号
//          PHONE_TYPE_GSM   GSM信号
//          PHONE_TYPE_CDMA  CDMA信号
//        */
//        tm.getPhoneType();//int
//        /*
//        * 服务商名称：
//        * 例如：中国移动、联通
//        * SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
//        */
//        tm.getSimOperatorName();//String
//
//
//
//
//        // 获取分辨率
////        DisplayMetrics dm = new DisplayMetrics();
////        ((Activity)mCountext).getWindowManager().getDefaultDisplay().getMetrics(dm);
////        System.out.println(dm.heightPixels);
////        System.out.println(dm.widthPixels);
//
//        StringBuilder deviceDetail = new StringBuilder();
//        deviceDetail.append("{");
//        deviceDetail.append("model:").append(android.os.Build.MODEL);
//        deviceDetail.append("}");
//
//        return RemotingDeviceServiceRPC.registerDeviceInfo(Membership.getUserID(), RemotingServiceProvider.getDeviceType(), getDeviceToken(), deviceDetail.toString());
    }

    public static boolean logoutDevice() {
        return true;
//        return RemotingDeviceServiceRPC.logoutDevice(Membership.getUserID(), RemotingServiceProvider.getDeviceType(), getDeviceToken());
    }

//    private static String getDeviceToken() {
//        GCMRegistrar.checkDevice(mCountext);
//        GCMRegistrar.checkManifest(mCountext);
//        final String regId = GCMRegistrar.getRegistrationId(mCountext);
//        if (regId.equals("")) {
//            GCMRegistrar.register(mCountext, Constants.SENDER_ID);
//        } else {
//        }
//        return regId;
//    }
}
