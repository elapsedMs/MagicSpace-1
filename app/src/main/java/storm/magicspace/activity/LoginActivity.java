package storm.magicspace.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import storm.commonlib.common.BaseApplication;
import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.bean.SyncAccount;
import storm.magicspace.bean.httpBean.SyncAccountResponse;
import storm.magicspace.http.AccountHttpManager;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.http.reponse.LoginResponse;
import storm.magicspace.util.LocalSPUtil;
import storm.magicspace.view.AuthLoginView;

import static storm.commonlib.common.util.StringUtil.EMPTY;

public class LoginActivity extends BaseActivity {

    private EditText nameEt;
    private UMShareAPI mShareAPI = null;
    private EditText passwordEt;
    private String mLoginJson;
    private AuthLoginView authLogin;

    public LoginActivity() {
        super(R.layout.activity_login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShareAPI = UMShareAPI.get(this);

        setActivityTitle("登录");
//        setActivityTitleAndTextColor(R.color.title_color_gray, R.color.title_color);
        setRightText(R.string.register);
        setTitleBarRightTvVisibility(View.VISIBLE);

        findEventView(R.id.bt_login);
        findEventView(R.id.tv_forget_pwd);
        findEventView(R.id.share_qq);
        findEventView(R.id.share_weixin);
        findEventView(R.id.share_xinlang);

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
        SHARE_MEDIA platform = null;

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
                break;

            case R.id.tv_forget_pwd:
                goToNext(ForgetPwdActivity.class);
                break;

            case R.id.share_xinlang:
                platform = SHARE_MEDIA.SINA;
                mShareAPI.doOauthVerify(this, platform, umAuthListener);
                break;

            case R.id.share_qq:
                platform = SHARE_MEDIA.QQ;
                mShareAPI.doOauthVerify(this, platform, umAuthListener);
                break;

            case R.id.share_weixin:
                platform = SHARE_MEDIA.WEIXIN;
                mShareAPI.doOauthVerify(this, platform, umAuthListener);
                break;

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
//            Toast.makeText(LoginActivity.this, "同步成功", Toast.LENGTH_SHORT).show();
            SyncAccount data = syncAccountResponse.getData();

            LocalSPUtil.saveToken(data == null ? EMPTY : data.getToken());
            goToNext(MainActivity.class);
        }

        @Override
        public void onFailed() {
            super.onFailed();
//            Toast.makeText(LoginActivity.this, "同步失败", Toast.LENGTH_SHORT).show();
            LocalSPUtil.saveToken(EMPTY);
            goToNext(MainActivity.class);
        }
    }

    private class LoginTask extends AsyncTask<String, Void, LoginResponse> {

        @Override
        protected LoginResponse doInBackground(String... params) {
            AccountHttpManager.LoginJson loginJson = new AccountHttpManager.LoginJson();
            LoginResponse response = AccountHttpManager.doLogin(params[0], params[1], loginJson);
            mLoginJson = loginJson.getJson();
            return response;
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
            new SyncTask().execute(new String[]{loginResponse.getData().getUser_no(), mLoginJson});
            LocalSPUtil.saveLoginAccountId(loginResponse.getData().getUser_no());
            LocalSPUtil.saveAccountInfo(loginResponse.getData());
        }

    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(BaseApplication.getApplication(), "Authorize succeed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(BaseApplication.getApplication(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(BaseApplication.getApplication(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
}