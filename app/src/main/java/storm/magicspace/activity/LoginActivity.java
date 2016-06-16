package storm.magicspace.activity;

import android.os.Bundle;
import android.widget.Button;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;

public class LoginActivity extends BaseActivity {
    private Button bt_login;

    public LoginActivity() {
        super(R.layout.activity_login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bt_login = findEventView(R.id.bt_login);
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId){
            case R.id.bt_login:
            goToNext(MainActivity.class);
                break;

            default:
                break;
        }
    }
}