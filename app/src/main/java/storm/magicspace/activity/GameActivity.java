package storm.magicspace.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.SeekBar;

import storm.commonlib.common.util.LogUtil;
import storm.magicspace.R;
import storm.magicspace.view.FloatView;
import storm.magicspace.view.FloatView.FloatInfo;

public class GameActivity extends Activity {

    public static final String TAG = GameActivity.class.getSimpleName();

    private WebView mWebView;
    private FloatView mFloatView;
    private Button sure;
    private FloatInfo mFloatInfo;
    private SeekBar mAlphaController;
    private float mAlphaVal = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        sure = (Button) findViewById(R.id.sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFloatInfo != null) {
                    String contentId = "1";
                    int itemId = 1;
                    String url = "http://app.stemmind.com/vr/objs/25.png";
                    float alpha = mAlphaVal;
                    float scale = mFloatInfo.getScale();
                    float rotate = mFloatInfo.getRotate();
                    // dropItem('contentId' , 'itemId', 'url' ,'alpha'  ,"scale","rotate")
                    LogUtil.d(TAG, "alpha = " + alpha + ", scale = " + scale + ", rotate = "
                            + rotate);
                    mWebView.loadUrl("javascript:dropItem('"
                            + contentId + "' ,'"
                            + itemId + "' ,'"
                            + url + "' ,'"
                            + alpha + "' ,'"
                            + scale + "' ,'"
                            + rotate + "')");
                }
            }
        });
        initView();
    }

    public void initView() {
        mWebView = (WebView) findViewById(R.id.webview_game);
        mFloatView = (FloatView) findViewById(R.id.floatview_game);
        mAlphaController = (SeekBar) findViewById(R.id.alpha);
        initFloatView();
        initWebView();
        initAlphaController();

    }

    private void initAlphaController() {
        mAlphaController.setProgress(100);
        mAlphaController.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAlphaVal = progress * 1f / 100;
                mFloatView.setAlpha(mAlphaVal);
                Log.d(TAG, "alpha = " + mAlphaVal);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
                mFloatInfo = floatInfo;
            }

        });
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
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
            LogUtil.d(TAG, "editItem used");
        }

        @JavascriptInterface
        public void dropItemCallBack(String msg) {
            LogUtil.d(TAG, "receive msg :" + msg);
            // {"x":"0","y":"0","scale":"0.5","alpha":"0.5","rotate":"0"}
        }
    }
}


//    js 调app 用editItem('contentId','sceneId','order')
//    app 调 js 用 dropItem('contentId' , 'itemId','trans'  ) 返回 array ('x', 'y', 'scale')
