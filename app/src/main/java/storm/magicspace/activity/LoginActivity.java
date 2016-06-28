package storm.magicspace.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;

import static storm.commonlib.common.util.StringUtil.EMPTY;

public class LoginActivity extends BaseActivity {

    private EditText nameEt;
    private EditText passwordEt;

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
        nameEt = findView(R.id.et_login_name);
        passwordEt = findView(R.id.et_login_password);
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
                String name = nameEt.getText().toString();
                if (name.equalsIgnoreCase(EMPTY)) {
                    Toast.makeText(LoginActivity.this, "账户不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }

                String password = passwordEt.getText().toString();
                if (password.equalsIgnoreCase(EMPTY)) {
                    Toast.makeText(LoginActivity.this, "账户不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }

                doLogin(name, password);
                goToNext(MainActivity.class);
        }
    }

    private void doLogin(String name, String password) {

    }
}