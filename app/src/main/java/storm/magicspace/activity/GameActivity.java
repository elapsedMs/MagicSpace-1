package storm.magicspace.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.util.JsonUtil;
import storm.commonlib.common.util.LogUtil;
import storm.commonlib.common.util.SharedPreferencesUtil;
import storm.magicspace.R;
import storm.magicspace.adapter.EggsAdapter;
import storm.magicspace.bean.IssueUCGContent;
import storm.magicspace.bean.IssueUCGContent.ScenesBean;
import storm.magicspace.bean.UpdateData;
import storm.magicspace.bean.httpBean.EggImage;
import storm.magicspace.bean.httpBean.EggImageListResponse;
import storm.magicspace.bean.httpBean.IssueUCGContentResponse;
import storm.magicspace.bean.httpBean.UpdateUGCContentScenesResponse;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.http.URLConstant;
import storm.magicspace.view.FloatView;
import storm.magicspace.view.FloatView.FloatInfo;

public class GameActivity extends Activity {

    public static final String TAG = GameActivity.class.getSimpleName();
    public static final String ALPHA_CONTROLLER_POSITION_PARENT_BOTTOM = "bottom";
    public static final String ALPHA_CONTROLLER_POSITION_ABOVE_EGGS = "above_eggs";
    public static final int EGG_INIT_COUNT = 5;
    public static final String DEFAULT_CONTENT_ID = "3403";

    private WebView mWebView;
    private FloatView mFloatView;
    private Button mConfirmBtn;
    private SeekBar mAlphaController;
    private RelativeLayout mEggsContainer;
    private RecyclerView mEggsLayout;
    private ImageView mGuide;
    private TextView mShowEggBtn;
    private TextView mEggsLoadingHint;
    private ImageView mSharedBtn;

    private float mAlphaVal = 1.0f;
    private int mEggsCount = 1;
    private FloatInfo mFloatInfo;
    private boolean isAlphaControllerShowing = false;
    private EggsAdapter mEggsAdapter;
    private String mUrl;
    private List<ScenesBean> mScenes;
    private String mContentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mContentId = getRandomContentId();
        initView();
        initEvent();
    }

    public void initView() {
        mWebView = (WebView) findViewById(R.id.webview_game);
        mFloatView = (FloatView) findViewById(R.id.floatview_game);
        mAlphaController = (SeekBar) findViewById(R.id.alpha);
        mConfirmBtn = (Button) findViewById(R.id.sure);
        mGuide = (ImageView) findViewById(R.id.iv_game_guide);
        mShowEggBtn = (TextView) findViewById(R.id.tv_game_egg);
        mEggsContainer = (RelativeLayout) findViewById(R.id.rl_game_eggs_container);
        mEggsLayout = (RecyclerView) findViewById(R.id.rv_game_eggs);
        mEggsLoadingHint = (TextView) findViewById(R.id.tv_game_loading);
        mSharedBtn = (ImageView) findViewById(R.id.iv_game_confirm);
        initFloatView();
        initWebView();
        initAlphaController();
        initEggs();
    }

    private void initEggs() {
        //mEggsLayout.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.HORIZONTAL,false));
        updateEggsCountHint(EGG_INIT_COUNT);
        mEggsLayout.setLayoutManager(new GridLayoutManager(this, 1, OrientationHelper.HORIZONTAL, false));
        new GetEggImageListTask().execute();
        new IssueUGCContentTask().execute();
    }

    private class IssueUGCContentTask extends BaseASyncTask<Void, IssueUCGContentResponse> {

        @Override
        public IssueUCGContentResponse doRequest(Void param) {
            return HTTPManager.issueUCCContent("", "", "", mContentId);
        }

        @Override
        public void onSuccess(IssueUCGContentResponse issueUCGContentResponse) {
            super.onSuccess(issueUCGContentResponse);
            IssueUCGContent data = issueUCGContentResponse.getData();
            List<ScenesBean> scenes = data.getScenes();
            if (scenes != null) {
                mScenes = scenes;
            }
        }

        @Override
        public void onFailed() {
            super.onFailed();
        }
    }

    private String getRandomContentId() {
        String contentJson = SharedPreferencesUtil.getJsonFromSharedPreferences(this,
                CommonConstants.CONTEND_IDS);
        if (contentJson == null) return DEFAULT_CONTENT_ID;
        ArrayList contentList = JsonUtil.fromJson(contentJson, ArrayList.class);
        if (contentList == null || contentList.size() == 0) return DEFAULT_CONTENT_ID;
        Random random = new Random();
        int id = random.nextInt(contentList.size());
        return (String) contentList.get(id);
    }

    private class UpdateUGCContentTask extends BaseASyncTask<Void, UpdateUGCContentScenesResponse> {

        @Override
        public UpdateUGCContentScenesResponse doRequest(Void param) {
            if (mScenes != null) {
                UpdateData updateData = new UpdateData();
                updateData.setBgimageUrl(mContentId);
                updateData.setItemsCount(mEggsCount);
                updateData.setOrder(mEggsCount);
                updateData.setTimeLimit(120);
                updateData.setTips("");
                UpdateData.ItemsBean items = new UpdateData.ItemsBean();
                items.setItemId("");//
                items.setX("1");
                items.setY("2");
                items.setItemMediaUrl("http://app.stemmind.com/vr/objs/08.png");
                items.setScalex("1.0");
                items.setRotatez("20");
                items.setTransparency("0.5");
                items.setEnabled("1");
                updateData.setItems(items);
                HTTPManager.updateUGCContentScenes("", mContentId, JsonUtil.toJson(updateData));
            }
            return null;
        }

        @Override
        public void onSuccess(UpdateUGCContentScenesResponse updateUGCContentScenesResponse) {
            super.onSuccess(updateUGCContentScenesResponse);
        }

        @Override
        public void onFailed() {
            super.onFailed();
        }
    }

    private class GetEggImageListTask extends BaseASyncTask<Void, EggImageListResponse> {
        @Override
        public EggImageListResponse doRequest(Void param) {
            return HTTPManager.getEggImageList();
        }

        @Override
        protected void onPostExecute(EggImageListResponse eggImageListResponse) {
            if (eggImageListResponse != null) {
                mEggsLoadingHint.setVisibility(View.INVISIBLE);
                mEggsLayout.setVisibility(View.VISIBLE);
                List<EggImage> data = eggImageListResponse.getData();
                EggImage eggImage = data.get(0);
                mEggsAdapter = new EggsAdapter(GameActivity.this, eggImage);
                mEggsLayout.setAdapter(mEggsAdapter);
                mEggsAdapter.setOnClickListener(new EggsAdapter.ClickInterface() {
                    @Override
                    public void onClick(final int position, final String url, final Bitmap bitmap) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LogUtil.d(TAG, "position = " + position + ", url = " + url);
                                mFloatView.setImageBitmap(bitmap);
                                mFloatInfo = null;
                                mUrl = url;
                                initFloatView();
                            }
                        });
                    }
                });
            } else {
                mEggsLoadingHint.setText(R.string.loading_failed);
            }
        }
    }

    private void createEgg() {
        if (mFloatInfo != null) {
            String contentId = "1";
            int itemId = mEggsCount++;
            float alpha = mAlphaVal;
            float scale = mFloatInfo.getScale();
            float rotate = -mFloatInfo.getRotate();
            // dropItem('contentId' , 'itemId', 'url' ,'alpha'  ,"scale","rotate")
            LogUtil.d(TAG, "contentId = " + contentId + ", itemId = " + itemId
                    + ", alpha = " + alpha + ", scale = " + scale
                    + ", rotate = " + rotate);
            mWebView.loadUrl("javascript:dropItem('"
                    + contentId + "' ,'"
                    + itemId + "' ,'"
                    + mUrl + "' ,'"
                    + alpha + "' ,'"
                    + scale + "' ,'"
                    + rotate + "')");
        } else {
            String contentId = "1";
            int itemId = mEggsCount++;
            float alpha = mAlphaVal;
            float scale = 1.0f;
            float rotate = 0.0f;
            mWebView.loadUrl("javascript:dropItem('"
                    + contentId + "' ,'"
                    + itemId + "' ,'"
                    + mUrl + "' ,'"
                    + alpha + "' ,'"
                    + scale + "' ,'"
                    + rotate + "')");
        }
    }

    private void initEvent() {
        mSharedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mUrl)) {
                    Toast.makeText(GameActivity.this, R.string.add_egg_hint, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEggsCount > EGG_INIT_COUNT) {
                    Toast.makeText(GameActivity.this, R.string.add_egg_over_hint, Toast.LENGTH_SHORT).show();
                    return;
                }
                updateEggsCountHint(EGG_INIT_COUNT - mEggsCount);
                createEgg();
                new UpdateUGCContentTask().execute();

            }
        });

        mShowEggBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEggsContainer.setVisibility(View.VISIBLE);
                positionAlphaController(ALPHA_CONTROLLER_POSITION_ABOVE_EGGS);
            }
        });
    }

    private void updateEggsCountHint(int count) {
        mShowEggBtn.setText(String.format(getResources().getString(R.string.eggs_count), count));
    }

    private void initAlphaController() {
        positionAlphaController(ALPHA_CONTROLLER_POSITION_PARENT_BOTTOM);
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
        // mFloatView.setImageResource(R.mipmap.surprise_egg_red);
        mFloatView.setOnFloatListener(new FloatView.FloatListener() {
            @Override
            public void clickLeftTop() {
                LogUtil.d(TAG, "left top transparent btn clicked");
                if (!isAlphaControllerShowing) {
                    mAlphaController.setVisibility(View.VISIBLE);
                } else {
                    mAlphaController.setVisibility(View.GONE);
                }
                isAlphaControllerShowing = !isAlphaControllerShowing;
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
        mFloatView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearMask();
                return false;
            }
        });
    }

    private void clearMask() {
        if (mGuide.getVisibility() == View.VISIBLE) mGuide.setVisibility(View.GONE);
        if (mEggsContainer.getVisibility() == View.VISIBLE) mEggsContainer.setVisibility(View.GONE);
        positionAlphaController(ALPHA_CONTROLLER_POSITION_PARENT_BOTTOM);
    }

    private void positionAlphaController(String type) {
        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) mAlphaController.getLayoutParams();
        if (ALPHA_CONTROLLER_POSITION_PARENT_BOTTOM.equals(type)) {
            layoutParams.removeRule(RelativeLayout.ABOVE);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        } else if (ALPHA_CONTROLLER_POSITION_ABOVE_EGGS.equals(type)) {
            layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ABOVE, R.id.rl_game_eggs_container);
        }
        mAlphaController.setLayoutParams(layoutParams);
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("gb2312");
        mWebView.loadUrl("http://app.stemmind.com/vr/a/vreditor.php?c="+mContentId);
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

    private void showShare() {
        ShareSDK.initSDK(GameActivity.this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(URLConstant.SHARED_URL);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://app.stemmind.com/vr/a/tour.html");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(URLConstant.SHARED_URL);

// 启动分享GUI
        oks.show(GameActivity.this);
    }
}


//    js 调app 用editItem('contentId','sceneId','order')
//    app 调 js 用 dropItem('contentId' , 'itemId','trans'  ) 返回 array ('x', 'y', 'scale')
