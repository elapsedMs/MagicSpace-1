package storm.magicspace.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import storm.magicspace.R;
import storm.magicspace.http.URLConstant;

/**
 * Created by lixiaolu on 16/7/1.
 */
public class FreshHelpActivity extends Activity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fresh_help);
//        setActivityTitle("新手攻略");
        mWebView = (WebView) findViewById(R.id.wv_fresh_help);
        initWebView();
    }

    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("gb2312");
        mWebView.loadUrl(URLConstant.MAIN_GUI);
    }
}
