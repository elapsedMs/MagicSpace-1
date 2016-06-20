package storm.magicspace.activity;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import storm.commonlib.common.util.LogUtil;
import storm.magicspace.R;
import storm.magicspace.view.FloatView;

public class GameActivity extends Activity {

    public static final String TAG = GameActivity.class.getSimpleName();

    private WebView mWebView;
    private FloatView mFloatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
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
        });
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
