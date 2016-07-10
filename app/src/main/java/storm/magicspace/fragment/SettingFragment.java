package storm.magicspace.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.umeng.common.message.UmengMessageDeviceConfig;
import com.umeng.fb.FeedbackAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.IUmengUnregisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;

import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.base.BaseFragment;
import storm.commonlib.common.util.ActivityCollector;
import storm.commonlib.common.util.LogUtil;
import storm.magicspace.R;
import storm.magicspace.activity.AboutUsActivity;
import storm.magicspace.activity.LoginActivity;

import static storm.commonlib.common.BaseApplication.getApplication;

/**
 * Created by gdq on 16/6/15.
 */
public class SettingFragment extends BaseFragment {
    private ToggleButton tb_isVisible;
    private RelativeLayout rl_feedback, rl_about_us;
    private LinearLayout ll_message_notice;
    private Button bt_loginout;
    private TextView tv_message_notice;
    private PushAgent mPushAgent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, null);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        tb_isVisible = this.findEventView(view, R.id.tb_isVisible);
        rl_feedback = this.findEventView(view, R.id.rl_feedback);
        rl_about_us = this.findEventView(view, R.id.rl_about_us);
        ll_message_notice = this.findEventView(view, R.id.ll_message_notice);
        bt_loginout = this.findEventView(view, R.id.bt_loginout);
        tv_message_notice = this.findView(view, R.id.tv_message_notice);
        PushAgent.getInstance(getApplication()).onAppStart();

        mPushAgent = PushAgent.getInstance(getApplication());
//        mPushAgent.enable();

        tb_isVisible.setChecked(mPushAgent.isEnabled());

        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);

        tb_isVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tb_isVisible.isChecked()) {
                    mPushAgent.enable(mRegisterCallback);
                } else {
                    mPushAgent.disable(mUnregisterCallback);

                }
            }
        });
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId) {
            case R.id.tb_isVisible:
                break;

            case R.id.ll_message_notice:
                break;

            case R.id.rl_feedback:
                FeedbackAgent agent = new FeedbackAgent(getActivity());
                agent.startFeedbackActivity();
                break;

            case R.id.rl_about_us:
                AboutUsActivity.startActivity(getActivity());
                break;

            case R.id.bt_loginout:
                ActivityCollector.getInstance().exit();
                goToNext(LoginActivity.class);
                break;

            default:
                break;
        }
    }


    private void updateStatus() {
        String pkgName = getApplication().getPackageName();
        String info = String.format("enabled:%s\nisRegistered:%s\nDeviceToken:%s\n" +
                        "SdkVersion:%s\nAppVersionCode:%s\nAppVersionName:%s",
                mPushAgent.isEnabled(), mPushAgent.isRegistered(),
                mPushAgent.getRegistrationId(), MsgConstant.SDK_VERSION,
                UmengMessageDeviceConfig.getAppVersionCode(getApplication()), UmengMessageDeviceConfig.getAppVersionName(getApplication()));

        LogUtil.i(CommonConstants.TAG, "updateStatus:" + String.format("enabled:%s  isRegistered:%s",
                mPushAgent.isEnabled(), mPushAgent.isRegistered()));
        Log.i(CommonConstants.TAG, "=============================");
    }

    public Handler handler = new Handler();

    public IUmengUnregisterCallback mUnregisterCallback = new IUmengUnregisterCallback() {

        @Override
        public void onUnregistered(String registrationId) {
            // TODO Auto-generated method stub
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    updateStatus();
                }
            }, 2000);
        }
    };

    public IUmengRegisterCallback mRegisterCallback = new IUmengRegisterCallback() {

        @Override
        public void onRegistered(String registrationId) {
            // TODO Auto-generated method stub
            handler.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    updateStatus();
                }
            });
        }
    };

}
