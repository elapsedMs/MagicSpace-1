package storm.commonlib.common.util;

import android.content.Context;
import android.text.TextUtils;

import retrofit.RequestInterceptor;

public class UploadInterceptor implements RequestInterceptor {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CLIENT_VER = "X-CLIENT-VER";
    public static final String CLIENT_ID = "X-CLIENT-ID";
    public static final String DEVICE_ID = "X-DEVICE-ID";
    public static final String AUTH_TOKEN = "X-AUTH-TOKEN";

    private final Context mContext;
    private String mToken;
    private String mDeviceID = null;
    private String mVersionInfo = null;

    public UploadInterceptor(Context context) {
        this.mContext = context;
    }

    @Override
    public void intercept(RequestInterceptor.RequestFacade facade) {
        facade.addHeader(CONTENT_TYPE, "multipart/form-data;");
        facade.addHeader(CLIENT_ID, "android");
        facade.addHeader(CLIENT_VER, this.getClientVersion());
        facade.addHeader(DEVICE_ID, this.getDeviceId());
        facade.addHeader(AUTH_TOKEN, this.getToken());
    }

    public void token(String token) {
        this.mToken = token;
    }

    protected String getToken() {
        return this.mToken;
    }

    protected String getDeviceId() {
        if (TextUtils.isEmpty(this.mDeviceID)) {
            this.mDeviceID = AppUtil.getDeviceId(this.mContext);
        }
        return this.mDeviceID;
    }

    protected String getClientVersion() {
        if (TextUtils.isEmpty(this.mVersionInfo)) {
            this.mVersionInfo = AppUtil.getVersionInfo(this.mContext, "%s %s");
        }
        return this.mVersionInfo;
    }

}
