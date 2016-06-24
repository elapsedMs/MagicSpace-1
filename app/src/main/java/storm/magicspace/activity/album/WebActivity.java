package storm.magicspace.activity.album;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.event.UrlEvent;

/**
 * Created by gdq on 16/6/25.
 */
public class WebActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        EventBus.getDefault().register(this);
    }

    private WebView mWebView;

    public WebActivity() {
        super(R.layout.activity_web, CommonConstants.ACTIVITY_STYLE_EMPTY);
    }

    @Override
    public void initView() {
        super.initView();
        mWebView = (WebView) findViewById(R.id.webview);
    }

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
        mWebView.setWebViewClient(new WebViewClient());
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onUserEvent(UrlEvent urlEvent) {
        Log.d("gdq",urlEvent.url);
        mWebView.loadUrl(urlEvent.url);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
