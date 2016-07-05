package storm.magicspace.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.http.URLConstant;
import storm.magicspace.view.MyEditText;

/**
 * Created by gdq on 16/7/5.
 */
public class GameEditDetailActivity extends BaseActivity {
    private String mContentId;
    private MyEditText myEditText;
    private TextView titleTv;
    private TextView eggCountTv;
    private TextView timeTv;
    private EditText descEt;
    private TextView giveUpTv;
    private TextView publishTv;
    private int lastCount = 5;

    public GameEditDetailActivity() {
        super(R.layout.activity_game_edit_detail);
    }

    @Override
    public void initView() {
        super.initView();
        setActivityTitle("编辑详情");
        setTitleLeftBtVisibility(View.VISIBLE);
        setRightTvClickable(true);
        setRightText(R.string.share);
        setTitleBarRightTvVisibility(View.VISIBLE);
        mContentId = getIntent().getStringExtra("contentId");
        publishTv = findEventView(R.id.publish);
        titleTv = findEventView(R.id.title);
        eggCountTv = findEventView(R.id.egg_count);
        timeTv = findEventView(R.id.time);
        giveUpTv = findEventView(R.id.give_up);
        descEt = findEventView(R.id.et_desx);
        myEditText = findEventView(R.id.et_coin_count);
        myEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ("".equals(s)) {
                    Toast.makeText(GameEditDetailActivity.this, "最少5个，最多20个", Toast.LENGTH_SHORT).show();
                    myEditText.setText(lastCount + "");
                    myEditText.setSelection(myEditText.length());
                    return;
                }
                int mycount = Integer.parseInt("" + s);
                if ("".equals(s) || mycount < 5 || mycount > 20) {
                    Toast.makeText(GameEditDetailActivity.this, "最少5个，最多20个", Toast.LENGTH_SHORT).show();
                    myEditText.setText(lastCount + "");
                    myEditText.setSelection(myEditText.length());
                } else {
                    lastCount = mycount;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onTitleBarRightTvClicked(View view) {
        super.onTitleBarRightTvClicked(view);
        showShare();
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId) {
            case R.id.give_up:
                finish();
                break;
            case R.id.publish:
                break;
        }
    }

    private void showShare() {
        ShareSDK.initSDK(GameEditDetailActivity.this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(URLConstant.SHARED_URL + mContentId);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        //        oks.setUrl("http://app.stemmind.com/vr/a/tour.html");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(URLConstant.SHARED_URL + mContentId);
        // 启动分享GUI
        oks.show(GameEditDetailActivity.this);
    }
}
