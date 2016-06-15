package storm.magicspace.activity;

import android.os.Bundle;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;

public class SplashActivity extends BaseActivity {

    public SplashActivity() {
        super(R.layout.activity_splash);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goToNext(LoginActivity.class);

    }
}