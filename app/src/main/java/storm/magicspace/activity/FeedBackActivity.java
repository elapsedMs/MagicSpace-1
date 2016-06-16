package storm.magicspace.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;

/**
 * Created by py on 2016/6/16.
 */
public class FeedBackActivity extends BaseActivity {
    private TextView tv_client_version;
    private EditText et_feedback, et_contact;

    public FeedBackActivity() {
        super(R.layout.activity_feed_back, CommonConstants.ACTIVITY_STYLE_WITH_TITLE_BAR);
    }

    @Override
    public void initView() {
        super.initView();
        tv_client_version = findView(R.id.tv_client_version);
        et_feedback = findView(R.id.et_feedback);
        et_contact = findView(R.id.et_contact);
        setActivityTitle(getResources().getString(R.string.feedback));
        setRightText(R.string.finish);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FeedBackActivity.class);
        context.startActivity(intent);
    }


}
