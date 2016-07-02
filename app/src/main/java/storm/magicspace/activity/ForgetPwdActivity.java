package storm.magicspace.activity;

import android.os.Bundle;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;

/**
 * Created by lixiaolu on 16/7/2.
 */
public class ForgetPwdActivity extends BaseActivity {

    public ForgetPwdActivity() {
        super(R.layout.activity_forget_pwd);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);

        setActivityTitle("测试");
    }
}
