package storm.magicspace.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;

/**
 * Created by lixiaolu on 16/7/8.
 */
public class AdvertActivity extends BaseActivity {

    private WebView mWebView;

    public AdvertActivity() {
        super(R.layout.activity_advert, CommonConstants.ACTIVITY_STYLE_EMPTY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra("URL");

        mWebView = findView(R.id.wv_advert);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("gb2312");
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient());
    }

}
