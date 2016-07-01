package storm.magicspace.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.http.AccountHttpManager;
import storm.magicspace.http.reponse.GetVerifyCodeResponse;
import storm.magicspace.http.reponse.RegisterResponse;

import static storm.commonlib.common.util.StringUtil.EMPTY;

public class RegisterActivity extends BaseActivity {

    private Button getVerifyCode;
    private Handler mHandler;
    private TimerTask task;
    private Timer timer;


    public RegisterActivity() {
        super(R.layout.activity_register);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActivityTitle("注册");
        setActivityTitleAndTextColor(R.color.title_color_gray, R.color.title_color);
        setTitleLeftBtVisibility(View.VISIBLE);


        getVerifyCode = findEventView(R.id.bt_get_verify_code);
        findEventView(R.id.bt_register);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    getVerifyCode.setClickable(true);
                    getVerifyCode.setText("获取验证码");
                    return;
                }

                getVerifyCode.setClickable(false);
                getVerifyCode.setText(String.format("%d%s", msg.what, EMPTY));
            }
        };

    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
        task.cancel();
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId) {
            case R.id.bt_get_verify_code:

                task = new TimerTask() {
                    int second = 10;

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