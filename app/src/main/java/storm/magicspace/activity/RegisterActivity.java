package storm.magicspace.activity;

import android.os.Bundle;
import android.view.View;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;

/**
 * Created by lixiaolu on 16/6/27.
 */
public class RegisterActivity extends BaseActivity {

    public RegisterActivity() {
        super(R.layout.activity_register);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActivityTitle("注册");
        setActivityTitleAndTextColor(R.color.title_color_gray, R.color.title_color);
        setTitleLeftBtVisibility(View.VISIBLE);
    }
}
