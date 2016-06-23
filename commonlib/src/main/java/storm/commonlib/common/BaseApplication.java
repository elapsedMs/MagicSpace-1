package storm.commonlib.common;

import android.app.Application;


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
    }
}