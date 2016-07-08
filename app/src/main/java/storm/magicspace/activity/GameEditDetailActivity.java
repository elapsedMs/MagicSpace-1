package storm.magicspace.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseActivity;
import storm.commonlib.common.util.JsonUtil;
import storm.commonlib.common.util.LogUtil;
import storm.magicspace.R;
import storm.magicspace.bean.IssueUCGContent;
import storm.magicspace.bean.SubmitUGCContent;
import storm.magicspace.bean.UGCScene;
import storm.magicspace.bean.httpBean.SubmitUGCContentResponse;
import storm.magicspace.event.GameEvent;
import storm.magicspace.event.PublishEvent;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.http.URLConstant;
import storm.magicspace.view.MyEditText;

/**
 * Created by gdq on 16/7/5.
 */
public class GameEditDetailActivity extends BaseActivity {
    private static final String TAG = GameEditDetailActivity.class.getSimpleName();
    private String mContentId;
    private MyEditText myEditText;
    private EditText titleTv;
    private TextView eggCountTv;
    private TextView timeTv;
    private EditText descEt;
    private TextView giveUpTv;
    private TextView publishTv;
    private int lastCount = 5;
    private IssueUCGContent mUCGContent;
    private boolean success;

    public GameEditDetailActivity() {
        super(R.layout.activity_game_edit_detail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        super.initView();
        setActivityTitle("编辑详情");
        setTitleLeftBtVisibility(View.VISIBLE);
        setRightTvClickable(true);
        setRightText(R.string.share);
        setTitleBarRightTvVisibility(View.INVISIBLE);
        mContentId = getIntent().getStringExtra(CommonConstants.CONTENT_ID);
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
//                if (TextUtils.isEmpty(s)) return;
//                int mycount = Integer.parseInt("" + s);
//                if (mycount < 5 || mycount > 20) {
//                    Toast.makeText(GameEditDetailActivity.this, "最少5个，最多20个", Toast.LENGTH_SHORT).show();
//                    myEditText.setText(lastCount + "");
//                    myEditText.setSelection(myEditText.length());
//                } else {
//                    lastCount = mycount;
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GameEvent gameEvent) {
        mUCGContent = gameEvent.content;
        if (mUCGContent == null || mUCGContent.getScenes() == null) return;
        UGCScene ugcScene = mUCGContent.getScenes().get(0);
        if (ugcScene == null) return;
        String title = mUCGContent.getTitle();
        String itemsCount = ugcScene.getItemsCount();
        titleTv.setText(title);
        eggCountTv.setText(itemsCount);
    }

    @Override
    public void onTitleBarRightTvClicked(View view) {
        super.onTitleBarRightTvClicked(view);
        //showShare();
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId) {
            case R.id.give_up:
                finish();
                break;
            case R.id.publish:
                publish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (success) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    PublishEvent publishEvent = new PublishEvent();
                    EventBus.getDefault().post(publishEvent);
                    finish();
                }
            });
        }
    }

    private void publish() {

//        if (TextUtils.isEmpty(s)) return;
//                int mycount = Integer.parseInt("" + s);
//                if (mycount < 5 || mycount > 20) {
//                    Toast.makeText(GameEditDetailActivity.this, "最少5个，最多20个", Toast.LENGTH_SHORT).show();
//                    myEditText.setText(lastCount + "");

        String title = titleTv.getText().toString();
        if (TextUtils.isEmpty(title)) {
            mUCGContent.setTitle(title);
            Toast.makeText(GameEditDetailActivity.this, "标题不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String desc = descEt.getText().toString();
        if (TextUtils.isEmpty(desc)) {
            mUCGContent.setDescription(desc);
            Toast.makeText(GameEditDetailActivity.this, "简介不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String price = myEditText.getText().toString();
        if (TextUtils.isEmpty(price)) {
            mUCGContent.setPrice(price);
            Toast.makeText(GameEditDetailActivity.this, "请输入金币", Toast.LENGTH_SHORT).show();
            return;
        } else {
            int mycount = Integer.parseInt(price);
            if (mycount < 5 || mycount > 20) {
                Toast.makeText(GameEditDetailActivity.this, "最少5个，最多20个", Toast.LENGTH_SHORT).show();
                myEditText.setText("");
            }
        }
        new PublishTask().execute();
    }

    class PublishTask extends BaseASyncTask<Void, SubmitUGCContentResponse> {

        @Override
        public SubmitUGCContentResponse doRequest(Void param) {
            mUCGContent.setTitle(titleTv.getText().toString());
            mUCGContent.setDescription(descEt.getText().toString());
            String data = JsonUtil.convertObjectToJson(mUCGContent);
            return HTTPManager.submitUGCContent(mContentId, data);
        }

        @Override
        public void onSuccess(SubmitUGCContentResponse response) {
            super.onSuccess(response);
            Toast.makeText(GameEditDetailActivity.this, "游戏发布成功", Toast.LENGTH_SHORT).show();
//            showShare();
            setShare();
            success = true;
        }

        @Override
        public void onFailed() {
            super.onFailed();
        }

        @Override
        public void onFailed(SubmitUGCContentResponse response) {
            super.onFailed(response);
            SubmitUGCContent data = response.getData();
            if (data == null) {
                Toast.makeText(GameEditDetailActivity.this, "游戏发布失败", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            String msg = data.getMsg();
            Toast.makeText(GameEditDetailActivity.this, msg, Toast.LENGTH_SHORT).show();

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
        oks.setTitle("魔fun全景挖彩蛋");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(URLConstant.SHARED_URL + mContentId);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(getString(R.string.shared_content));
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        //        oks.setUrl("http://app.stemmind.com/vr/a/tour.html");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(URLConstant.SHARED_URL + mContentId);


        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                LogUtil.d(TAG, "onComplete");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PublishEvent publishEvent = new PublishEvent();
                        EventBus.getDefault().post(publishEvent);
                        finish();
                    }
                });
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                LogUtil.d(TAG, "onError");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                LogUtil.d(TAG, "onCancel");
            }
        });

        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                LogUtil.d(TAG, "onShare");
            }
        });

        //ShareSDK.getPlatform(Tec)
        // 启动分享GUI
        oks.show(GameEditDetailActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    public void setShare(){

        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                {
                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,SHARE_MEDIA.DOUBAN
                };
        new ShareAction(this).setDisplayList(displaylist )
                .withText( getString(R.string.shared_content) )
                .withTitle("魔fun全景挖彩蛋")
                .withTargetUrl(URLConstant.SHARED_URL + mContentId)
                .open();
    }
}
