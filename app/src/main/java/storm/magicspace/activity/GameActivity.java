package storm.magicspace.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import storm.commonlib.common.util.LogUtil;
import storm.magicspace.R;
import storm.magicspace.view.FloatView;
import storm.magicspace.view.FloatView.FloatInfo;

public class GameActivity extends Activity {

    public static final String TAG = GameActivity.class.getSimpleName();

    private WebView mWebView;
    private FloatView mFloatView;
    private Button sure;
    private FloatInfo mFloatinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        sure = (Button) findViewById(R.id.sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFloatinfo != null) {
                    float x = mFloatinfo.getX();
                    float y = mFloatinfo.getY();
                    float alpha = mFloatinfo.getAlpha();
                    float scale = mFloatinfo.getScale();
                    float rotate = mFloatinfo.getRotate();
                    //mWebView.loadUrl("javascript:dropItem('1' ,'1' ,'0.5' ,'2','10')");
                    mWebView.loadUrl("javascript:dropItem('1' ,'1' ,'"+alpha+"' ,'"+scale+"'," + "'"+rotate+"')");
                }
            }
        });
        initView();
    }

    public void initView() {
        mWebView = (WebView) findViewById(R.id.webview_game);
        mFloatView = (FloatView) findViewById(R.id.floatview_game);
        initFloatView();
        initWebView();
    }

    private void initFloatView() {
        mFloatView.setImageResource(R.mipmap.surprise_egg_red);
        mFloatView.setOnFloatListener(new FloatView.FloatListener() {
            @Override
            public void clickLeftTop() {
                LogUtil.d(TAG, "left top transparent btn clicked");
            }

            @Override
            public void clickRightTop() {
                LogUtil.d(TAG, "right top rotate btn clicked");
            }

            @Override
            public void clickRightBottom() {
                LogUtil.d(TAG, "right bottom scale btn clicked");
            }

            @Override
            public void floatInfo(FloatInfo floatInfo) {
                LogUtil.d(TAG, "x = " + floatInfo.getX() +", y = " + floatInfo.getY() + ", alpha = " + floatInfo.getAlpha() + ", scale = "
                        + floatInfo.getScale() + ", rotate = " + floatInfo.getRotate());
                mFloatinfo = floatInfo;
            }

        });
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        mWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); // Android默认的处理方式
                handler.proceed();  // 接受所有网站的证书
                //handleMessage(Message msg); // 进行其他处理
            }
        });

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("gb2312");
        mWebView.loadUrl("http://app.stemmind.com/vr/a/tour.html");
        ContainerView containerView = new ContainerView();
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.addJavascriptInterface(containerView, "containerView");
    }

    private class ContainerView {

        @JavascriptInterface
        public void editItem(String contentId, String sceneId, String order) {
            Log.i("lixiaolu", "editItem used");
        }

        @JavascriptInterface
        public void dropItemCallBack(String msg) {
            Log.i("lixiaolu", "receive msg :" + msg);
        }
    }
}


//    js 调app 用editItem('contentId','sceneId','order')
//    app 调 js 用 dropItem('contentId' , 'itemId','trans'  ) 返回 array ('x', 'y', 'scale')
