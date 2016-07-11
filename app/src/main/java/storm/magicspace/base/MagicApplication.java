package storm.magicspace.base;

import storm.commonlib.common.BaseApplication;

/**
 * Created by gdq on 16/6/23.
 */
public class MagicApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install(this);

//        HttpUtils.setHostUri(URLConstant.API_HOST);
    }
}
