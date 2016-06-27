package storm.magicspace.activity;

import android.os.Bundle;
import android.view.View;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;

public class LoginActivity extends BaseActivity {

    public LoginActivity() {
        super(R.layout.activity_login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActivityTitle("登录");
        setActivityTitleAndTextColor(R.color.title_color_gray, R.color.title_color);
        setRightText(R.string.register);
        setTitleBarRightTvVisibility(View.VISIBLE);

        findEventView(R.id.bt_login);
    }

    @Override
    public void onTitleBarRightTvClicked(View view) {
        super.onTitleBarRightTvClicked(view);
        goToNext(RegisterActivity.class);
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId) {
            case R.id.bt_login:
                goToNext(MainActivity.class);
        }
    }
}