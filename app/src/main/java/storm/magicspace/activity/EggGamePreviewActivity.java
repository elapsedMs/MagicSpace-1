package storm.magicspace.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.base.BaseActivity;
import storm.commonlib.common.util.LogUtil;
import storm.magicspace.R;
import storm.magicspace.bean.EggInfo;

public class EggGamePreviewActivity extends BaseActivity {
    private WebView wv_egg_game_preview;
    private EggInfo info;

    public EggGamePreviewActivity() {
        super(R.layout.activity_egg_preview, CommonConstants.ACTIVITY_STYLE_WITH_LOADING);
    }


    @Override
    public void initView() {
        super.initView();
        wv_egg_game_preview = (WebView) findViewById(R.id.wv_egg_game_preview);
        Intent intent = this.getIntent();
        info = (EggInfo) intent.getSerializableExtra("game_info");
        initWebView();
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        wv_egg_game_preview.getSettings().setJavaScriptEnabled(true);
        wv_egg_game_preview.getSettings().setDefaultTextEncodingName("gb2312");
        wv_egg_game_preview.loadUrl("http://app.stemmind.com/vr/a/preview.php?c=" + info.contentId);
        ContainerView containerView = new ContainerView();
        wv_egg_game_preview.setWebViewClient(new WebViewClient());
        wv_egg_game_preview.addJavascriptInterface(containerView, "containerView");
    }

    private class ContainerView {

        @JavascriptInterface
        public void editItem(String contentId, String sceneId, String order) {
            LogUtil.d("py", "editItem used");
        }

        @JavascriptInterface
        public void dropItemCallBack(String msg) {
            LogUtil.d("py", "receive msg :" + msg);
            // {"x":"0","y":"0","scale":"0.5","alpha":"0.5","rotate":"0"}
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
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl(URLConstant.SHARED_URL);
        oks.setTitleUrl("http://app.stemmind.com/vr/html/gamedetail.php?c=" + info.contentId);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://app.stemmind.com/vr/a/tour.html");
        oks.setUrl("http://app.stemmind.com/vr/html/gamedetail.php?c=" + info.contentId);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl(URLConstant.SHARED_URL);
        oks.setSiteUrl("http://app.stemmind.com/vr/html/gamedetail.php?c=" + info.contentId);

// 启动分享GUI
        oks.show(EggGamePreviewActivity.this);
    }
}


//    js 调app 用editItem('contentId','sceneId','order')
//    app 调 js 用 dropItem('contentId' , 'itemId','trans'  ) 返回 array ('x', 'y', 'scale')
