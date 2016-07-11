package storm.magicspace.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseActivity;
import storm.commonlib.common.util.LogUtil;
import storm.commonlib.common.view.dialog.MedtreeDialog;
import storm.magicspace.R;
import storm.magicspace.bean.EggInfo;
import storm.magicspace.bean.httpBean.gameEnd;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.http.URLConstant;
import storm.magicspace.http.reponse.ShareUrlResponse;
import storm.magicspace.util.LocalSPUtil;

import static storm.commonlib.common.view.dialog.MedtreeDialog.DisplayStyle.LOADING;

public class EggGamePreviewActivity extends BaseActivity {
    private WebView wv_egg_game_preview;
    private EggInfo info;
    private Handler mHandler;

    private TimeCount time;
    private MedtreeDialog medtreeDialog;
    private String mFrom;
    private String mContentId;
    private int duration ;
    private gameEnd mgameEnd;
    public EggGamePreviewActivity() {
        super(R.layout.activity_egg_preview, CommonConstants.ACTIVITY_STYLE_EMPTY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private String configFrom() {
        Intent intent = getIntent();
        if (intent == null) return CommonConstants.GAME;
        mContentId = intent.getStringExtra(CommonConstants.CONTENT_ID);
        return intent.getStringExtra(CommonConstants.FROM);
    }

    @Override
    public void initView() {
        super.initView();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        finish();
                        break;

                    case 1:
//                        showShare();
                        setShare();
                        break;

                    case 2:
                        Toast.makeText(EggGamePreviewActivity.this, "过关成功", Toast.LENGTH_SHORT).show();
                        new GameEnd().execute(mgameEnd);
                        break;

                    case 3:
                        Toast.makeText(EggGamePreviewActivity.this, "过关失败", Toast.LENGTH_SHORT).show();
                        new GameEnd().execute(mgameEnd);
                        break;

                    case 4:
                        dismissLoadingDialog();
//                        new reqInfoCallback().execute();
                        wv_egg_game_preview.loadUrl("javascript:reqInfoCallback('"+ LocalSPUtil.getAccountInfo().getUser_no()+"'");
                        break;
                }
            }
        };
        wv_egg_game_preview = (WebView) findViewById(R.id.wv_egg_game_preview);
        mFrom = configFrom();
        Intent intent = this.getIntent();
        info = (EggInfo) intent.getSerializableExtra("game_info");
        initWebView();
        showloadingeDialog(LOADING, "数据加载中…", "", true, true);
        time = new TimeCount(30 * 1000, 1000);
        time.start();
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        wv_egg_game_preview.getSettings().setJavaScriptEnabled(true);
        wv_egg_game_preview.getSettings().setDefaultTextEncodingName("gb2312");
        String webUrl = getUrl();
        wv_egg_game_preview.loadUrl(webUrl);
        ContainerView containerView = new ContainerView();
        wv_egg_game_preview.setWebViewClient(new WebViewClient());
        wv_egg_game_preview.addJavascriptInterface(containerView, "containerView");
    }

    private String getUrl() {
        //        if (equals) {
//            return URLConstants.URL_4 + mContentId;
//        } else if (mFrom.equals(CommonConstants.TOPIC)) {
//            return URLConstant.URL_WEBVIEW_PREVIEW_TOPIC + mContentId;
//        }
//        else {
//            return URLConstant.URL_WEBVIEW_PREVIEW_GAME + mContentId;
//        }
        if (mFrom.equals(CommonConstants.GAME)) return URLConstant.URL_4 + mContentId;
        else return URLConstant.URL_WEBVIEW_PREVIEW_TOPIC + mContentId;
    }

    private class ContainerView {

        @JavascriptInterface
        public void goBack() {
            Log.i("lixiaolu", "go back");
            mHandler.sendEmptyMessage(0);
        }

        @JavascriptInterface
        public void shareGame() {
            Log.i("lixiaolu", "game share");
            mHandler.sendEmptyMessage(1);
        }

        @JavascriptInterface
        public void endGame(boolean bool,int duration) {
            Log.i("lixiaolu", "game end");
            mgameEnd = new gameEnd(info.contentId,duration,bool);
            if (bool) {
                mHandler.sendEmptyMessage(2);

            } else {
                mHandler.sendEmptyMessage(3);

            }

        }


        @JavascriptInterface
        public void onLoadComplete() {
            Log.i("lixiaolu", "onLoadComplete");
            mHandler.sendEmptyMessage(4);
        }
    }

    private void showShare() {
        ShareSDK.initSDK(EggGamePreviewActivity.this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("魔fun全景挖彩蛋");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl(URLConstant.SHARED_URL);
        oks.setTitleUrl(URLConstant.OUT_SHAER_1 + info.contentId);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(getString(R.string.shared_content));
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://app.stemmind.com/vr/a/tour.html");
        oks.setUrl(URLConstant.EGG_GAME_PRE_SHARE_URL + info.contentId);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl(URLConstant.SHARED_URL);
        oks.setSiteUrl(URLConstant.SHARE_OUT_URL + info.contentId);


// 启动分享GUI
        oks.show(EggGamePreviewActivity.this);
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕
            dismissLoadingDialog();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程

        }
    }

    public void showloadingeDialog(MedtreeDialog.DisplayStyle style, String title, String message, boolean shouldBottomHide, boolean hasAnimation) {
        medtreeDialog = medtreeDialog == null ? new MedtreeDialog(this) : medtreeDialog;
        medtreeDialog.displayWithStyle(style);
        medtreeDialog.setMessage(message);
        medtreeDialog.setTitle(title);

        if (shouldBottomHide)
            medtreeDialog.hideBottom();

        if (hasAnimation)
            medtreeDialog.startAnimation();
        medtreeDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mHandler.sendEmptyMessage(4);
                LogUtil.d("py", "dismiss");
            }
        });
        medtreeDialog.show();
    }

    public void dismissLoadingDialog() {
        if (medtreeDialog != null)
            medtreeDialog.dismiss();
    }

    public void setShare(){

        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                {
                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
                };
        new ShareAction(this).setDisplayList(displaylist )
                .withText(getString(R.string.shared_content))
                .withTitle("魔fun全景挖彩蛋")
                .withTargetUrl(URLConstant.URL_111 + info.contentId)
                .open();
    }

    private class GameEnd extends BaseASyncTask<gameEnd, ShareUrlResponse> {
        @Override
        public ShareUrlResponse doRequest(gameEnd param) {
            return HTTPManager.gameEnd(param);
        }

        @Override
        public void onSuccess(ShareUrlResponse response) {
            super.onSuccess(response);

        }
    }
    private class reqInfoCallback extends BaseASyncTask<Void, ShareUrlResponse> {
        @Override
        public ShareUrlResponse doRequest(Void param) {
            return HTTPManager.reqInfoCallback();
        }

        @Override
        public void onSuccess(ShareUrlResponse response) {
            super.onSuccess(response);

        }
    }
}


//    js 调app 用editItem('contentId','sceneId','order')
//    app 调 js 用 dropItem('contentId' , 'itemId','trans'  ) 返回 array ('x', 'y', 'scale')
