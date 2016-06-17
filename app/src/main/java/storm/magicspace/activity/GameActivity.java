package storm.magicspace.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.view.FloatView;

import static storm.commonlib.common.CommonConstants.ACTIVITY_STYLE_EMPTY;

public class GameActivity extends BaseActivity {

    private WebView mWebView;
    private FloatView mFloatView;

    public GameActivity() {
        super(R.layout.activity_game, ACTIVITY_STYLE_EMPTY);
    }

    @Override
    public void initView() {
        super.initView();

        mWebView = findView(R.id.webview_game);
        mFloatView = findView(R.id.floatview_game);

        initWebView();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.surprise_egg_red);
        mFloatView.load(bitmap);
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
