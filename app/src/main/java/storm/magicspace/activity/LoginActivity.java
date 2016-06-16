package storm.magicspace.activity;

import android.os.Bundle;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;

import static storm.commonlib.common.Constants.ACTIVITY_STYLE_EMPTY;

public class LoginActivity extends BaseActivity {

    public LoginActivity() {
        super(R.layout.activity_login, ACTIVITY_STYLE_EMPTY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findEventView(R.id.bt_login);
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