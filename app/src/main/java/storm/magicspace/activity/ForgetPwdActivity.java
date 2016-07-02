package storm.magicspace.activity;

import android.os.Bundle;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;

public class ForgetPwdActivity extends BaseActivity {

    public ForgetPwdActivity() {
        super(R.layout.activity_forget_pwd);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityTitle("找回密码");

    }
}
