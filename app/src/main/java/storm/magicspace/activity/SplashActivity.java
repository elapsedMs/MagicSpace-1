package storm.magicspace.activity;

import android.os.Bundle;

import storm.commonlib.common.base.BaseActivity;
import storm.commonlib.common.util.JsonUtil;
import storm.commonlib.common.util.LogUtil;
import storm.magicspace.R;

import static storm.commonlib.common.CommonConstants.ACTIVITY_STYLE_EMPTY;

public class SplashActivity extends BaseActivity {

    public SplashActivity() {
        super(R.layout.activity_splash, ACTIVITY_STYLE_EMPTY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goToNext(LoginActivity.class);

        String[] text = new String[0];
        String s = JsonUtil.convertObjectToJson(text);
        LogUtil.i("Test", s);
    }
}