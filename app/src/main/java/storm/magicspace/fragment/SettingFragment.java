package storm.magicspace.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import storm.commonlib.common.base.BaseFragment;
import storm.commonlib.common.util.ActivityCollector;
import storm.magicspace.R;
import storm.magicspace.activity.AboutUsActivity;
import storm.magicspace.activity.FeedBackActivity;
import storm.magicspace.activity.LoginActivity;

/**
 * Created by gdq on 16/6/15.
 */
public class SettingFragment extends BaseFragment {
    private ToggleButton tb_isVisible;
    private RelativeLayout rl_feedback, rl_about_us;
    private LinearLayout ll_message_notice;
    private Button bt_loginout;
    private TextView tv_message_notice;


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
                FeedBackActivity.startActivity(getActivity());
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
}
