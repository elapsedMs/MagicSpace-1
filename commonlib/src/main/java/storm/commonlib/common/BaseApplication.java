package storm.commonlib.common;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;


public class BaseApplication extends Application {

    private static Application application;
    public static boolean isDebug = true;

    public static Application getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(this, "57760bebe0f55afeca000d30", "0001", MobclickAgent.EScenarioType. E_UM_NORMAL,true);
        MobclickAgent. startWithConfigure(config);
        MobclickAgent.setDebugMode( true );
    }
}