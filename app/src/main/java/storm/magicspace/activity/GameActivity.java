package storm.magicspace.activity;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;

import static storm.commonlib.common.CommonConstants.ACTIVITY_STYLE_EMPTY;

/**
 * Created by gdq on 16/6/16.
 */
public class GameActivity extends BaseActivity {

    private WebView mWebView;
    private RelativeLayout mParent;

    public GameActivity() {
        super(R.layout.activity_game, ACTIVITY_STYLE_EMPTY);
    }

    @Override
    public void initView() {
        super.initView();
        mWebView = findView(R.id.webview_game);
        mParent = findView(R.id.rl_game_container);
        initWebView();
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
        mWebView.loadUrl("http://app.stemmind.com/3d/vryu/krpano.html?xml=vrxml.php&html5=only");
    }
}
