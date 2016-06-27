package storm.magicspace.base;

import com.squareup.leakcanary.LeakCanary;

import storm.commonlib.common.BaseApplication;
import storm.commonlib.common.http.HttpUtils;

/**
 * Created by gdq on 16/6/23.
 */
public class MagicApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install(this);

        HttpUtils.setAppModel(true);
    }
}
