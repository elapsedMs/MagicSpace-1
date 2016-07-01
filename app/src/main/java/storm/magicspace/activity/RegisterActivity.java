package storm.magicspace.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import storm.commonlib.common.BaseApplication;
import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.http.AccountHttpManager;
import storm.magicspace.http.reponse.GetVerifyCodeResponse;
import storm.magicspace.http.reponse.RegisterResponse;

import static storm.commonlib.common.util.StringUtil.EMPTY;

public class RegisterActivity extends BaseActivity {

    private TextView getVerifyCode;
    private Handler mHandler;
    private TimerTask task;
    private Timer timer;
    private EditText phoneInputEt;
    private EditText verifyCodeEt;
    private EditText pwdEt;

    public RegisterActivity() {
        super(R.layout.activity_register);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActivityTitle("注册");
//        setActivityTitleAndTextColor(R.color.title_color_gray, R.color.title_color);
        setTitleLeftBtVisibility(View.VISIBLE);

        setRightText(R.string.login);
        setTitleBarRightTvVisibility(View.VISIBLE);

        getVerifyCode = findEventView(R.id.bt_get_verify_code);
        findEventView(R.id.bt_register);
        phoneInputEt = findView(R.id.et_phone_input);
        pwdEt = findView(R.id.et_register_pwd);
        verifyCodeEt = findView(R.id.et_register_verify_code);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    getVerifyCode.setClickable(true);
                    getVerifyCode.setText("获取验证码");
                    return;
                }

                getVerifyCode.setClickable(false);
                getVerifyCode.setText(String.format("%d%s", msg.what, "秒后再次获取"));
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null)
            timer.cancel();
        if (task != null)
            task.cancel();
    }

    @Override
    public void onTitleBarRightTvClicked(View view) {
        super.onTitleBarRightTvClicked(view);
        this.finish();
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        String phone = phoneInputEt.getText().toString();
        String pwd = pwdEt.getText().toString();
        String verifyCode = verifyCodeEt.getText().toString();

        switch (resId) {
            case R.id.bt_get_verify_code:
                if (phone.equalsIgnoreCase(EMPTY)) {
                    Toast.makeText(BaseApplication.getApplication(), "手机号码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                new VerifyCodeTask().execute(EMPTY, phone);
                break;

            case R.id.bt_register:
                if (phone.equalsIgnoreCase(EMPTY)) {
                    Toast.makeText(BaseApplication.getApplication(), "手机号码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pwd.equalsIgnoreCase(EMPTY)) {
                    Toast.makeText(BaseApplication.getApplication(), "密码不不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (verifyCode.equalsIgnoreCase(EMPTY)) {
                    Toast.makeText(BaseApplication.getApplication(), "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                new RegisterTask().execute(phone, pwd, verifyCode);
                break;
        }
    }

    private void statCountTime() {
        task = new TimerTask() {
            int second = 60;

            @Override
            public void run() {
                if (second == 0) {
                    this.cancel();
                    return;
                }

                second--;
                mHandler.sendEmptyMessage(second);
            }
        };

        timer = new Timer();
        timer.schedule(task, 1, 1000);
    }

    private class VerifyCodeTask extends AsyncTask<String, Void, GetVerifyCodeResponse> {

        @Override
        protected GetVerifyCodeResponse doInBackground(String... params) {
            statCountTime();
            return AccountHttpManager.getVerifyCode(params[0], params[1]);
        }
    }

    private class RegisterTask extends AsyncTask<String, Void, RegisterResponse> {

        @Override
        protected RegisterResponse doInBackground(String... params) {
            return AccountHttpManager.doRegist(params[0], params[1], params[2]);
        }

        @Override
        protected void onPostExecute(RegisterResponse registerResponse) {
            super.onPostExecute(registerResponse);
            if (registerResponse == null) return;

            if (registerResponse.isStatus()) {
                Toast.makeText(BaseApplication.getApplication(), "注册成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BaseApplication.getApplication(), registerResponse.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}