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

public class ForgetPwdActivity extends BaseActivity {

    private TextView getVerifyCode;
    private EditText phoneInputEt;
    private EditText pwdEt;
    private EditText verifyCodeEt;
    private Handler mHandler;
    private TimerTask task;
    private Timer timer;

    public ForgetPwdActivity() {
        super(R.layout.activity_forget_pwd);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityTitle("找回密码");

        setTitleLeftBtVisibility(View.VISIBLE);

        getVerifyCode = findEventView(R.id.bt_get_forget_pwd_verify);
        findEventView(R.id.bt_forget_pwd_submit);
        phoneInputEt = findView(R.id.et_forget_pwd_phone_input);
        pwdEt = findView(R.id.et_forget_pwd_input);
        verifyCodeEt = findView(R.id.et_forget_pwd_verify_code);

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
            case R.id.bt_get_forget_pwd_verify:
                if (phone.equalsIgnoreCase(EMPTY)) {
                    Toast.makeText(BaseApplication.getApplication(), "手机号码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                new VerifyCodeTask().execute(EMPTY, phone);
                break;

            case R.id.bt_forget_pwd_submit:
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

                new ChangePwdSubmitTask().execute(phone, pwd, verifyCode);
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
            return AccountHttpManager.getVerifyCode(params[1], "tel_getpwd");
        }
    }

    private class ChangePwdSubmitTask extends AsyncTask<String, Void, RegisterResponse> {

        @Override
        protected RegisterResponse doInBackground(String... params) {
            return AccountHttpManager.changePwd(params[0], params[1], params[2]);
        }

        @Override
        protected void onPostExecute(RegisterResponse registerResponse) {
            super.onPostExecute(registerResponse);
            if (registerResponse == null)
                return;

            if (registerResponse.isStatus()) {
                Toast.makeText(BaseApplication.getApplication(), "修改成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BaseApplication.getApplication(), registerResponse.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
