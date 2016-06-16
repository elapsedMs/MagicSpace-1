package storm.magicspace.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;

/**
 * Created by py on 2016/6/16.
 */
public class AboutUsActivity extends BaseActivity {
    private ImageView iv_Avatar;
    private RelativeLayout rl_share, rl_assessment;
    private TextView tv_version;

    public AboutUsActivity() {
        super(R.layout.activity_about_us, CommonConstants.ACTIVITY_STYLE_WITH_TITLE_BAR);
    }

    @Override
    public void initView() {
        super.initView();
        iv_Avatar = findView(R.id.iv_Avatar);
        tv_version = findView(R.id.tv_version);
        rl_assessment = findEventView(R.id.rl_assessment);
        rl_share = findEventView(R.id.rl_share);
        setActivityTitle(getResources().getString(R.string.about_us));
        setTitleRightBtVisibility(View.GONE);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId) {
            case R.id.rl_share:

                break;

            case R.id.rl_assessment:

                break;

            default:
                break;
        }
    }
}
