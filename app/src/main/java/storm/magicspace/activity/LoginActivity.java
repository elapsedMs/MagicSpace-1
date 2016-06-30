package storm.magicspace.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseActivity;
import storm.commonlib.common.util.JsonUtil;
import storm.magicspace.R;
import storm.magicspace.bean.httpBean.SyncAccountResponse;
import storm.magicspace.http.AccountHttpManager;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.http.reponse.LoginResponse;
import storm.magicspace.util.LocalSPUtil;

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
        }
    }

    private void doLogin(String name, String password) {
        new LoginTask().execute(name, password);
    }

    private class SyncTask extends BaseASyncTask<String[], SyncAccountResponse> {

        @Override
        public SyncAccountResponse doRequest(String[] param) {
            return HTTPManager.syncAccount(param[0], param[1]);
        }

        @Override
        public void onSuccess(SyncAccountResponse syncAccountResponse) {
            super.onSuccess(syncAccountResponse);
            Toast.makeText(LoginActivity.this, "sync success", Toast.LENGTH_SHORT).show();
            goToNext(MainActivity.class);
        }

        @Override
        public void onFailed() {
            super.onFailed();
            Toast.makeText(LoginActivity.this, "sync failed", Toast.LENGTH_SHORT).show();
            goToNext(MainActivity.class);
        }
    }

    private class LoginTask extends AsyncTask<String, Void, LoginResponse> {

        @Override
        protected LoginResponse doInBackground(String... params) {
            return AccountHttpManager.doLogin(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(LoginResponse loginResponse) {
            super.onPostExecute(loginResponse);

            if (loginResponse == null) {
                Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                return;
            }

            if (!loginResponse.isStatus()) {
                Toast.makeText(LoginActivity.this, loginResponse.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }
            new SyncTask().execute(new String[]{loginResponse.getData().getUser_no(),
                    JsonUtil.convertObjectToJson(loginResponse)});
            LocalSPUtil.saveLoginAccountId(loginResponse.getData().getUser_no());
            LocalSPUtil.saveAccountInfo(loginResponse.getData());
        }

    }
}