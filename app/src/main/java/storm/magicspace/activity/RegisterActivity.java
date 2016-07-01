package storm.magicspace.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.http.AccountHttpManager;
import storm.magicspace.http.reponse.GetVerifyCodeResponse;
import storm.magicspace.http.reponse.RegisterResponse;

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


        findEventView(R.id.bt_get_verify_code);
        findEventView(R.id.bt_register);

    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId) {
            case R.id.bt_get_verify_code:
                new VerifyCodeTask().execute("", "18612965139");
                break;

            case R.id.bt_register:
                new RegisterTask().execute("18612965139", "123456a", "866282");
                break;
        }
    }

    private class VerifyCodeTask extends AsyncTask<String, Void, GetVerifyCodeResponse> {

        @Override
        protected GetVerifyCodeResponse doInBackground(String... params) {
            return AccountHttpManager.getVerifyCode(params[0], params[1]);
        }
    }

    private class RegisterTask extends AsyncTask<String, Void, RegisterResponse> {

        @Override
        protected RegisterResponse doInBackground(String... params) {
            return AccountHttpManager.doRegist(params[0], params[1], params[2]);
        }
    }
}