package storm.magicspace.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.IOException;
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
import storm.magicspace.fragment.EggImageFragment;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.http.URLConstant;
import storm.magicspace.view.FloatView;
import storm.magicspace.view.FloatView.FloatInfo;

public class GameActivity extends FragmentActivity {

    public static final String TAG = GameActivity.class.getSimpleName();
    public static final String ALPHA_CONTROLLER_POSITION_PARENT_BOTTOM = "bottom";
    public static final String ALPHA_CONTROLLER_POSITION_ABOVE_EGGS = "above_eggs";
    public static final int EGG_INIT_COUNT = 0;
    public static final int EGG_MAX_COUNT = 5;
    public static final String DEFAULT_CONTENT_ID = "3403";

    private WebView mWebView;
    private FloatView mFloatView;
    private ImageView mConfirmBtn;
    private SeekBar mAlphaController;
    private RelativeLayout mEggsContainer;
    private ImageView mGuide;
    private TextView mShowEggBtn;
    private TextView mEggsLoadingHint;
    private ImageView mSharedBtn;
    private ImageView mBackBtn;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private float mAlphaVal = 1.0f;
    private int mEggsCount = 1;
    private FloatInfo mFloatInfo;
    private boolean isAlphaControllerShowing = false;
    private String mUrl;
    private List<ScenesBean> mScenes;
    private String mContentId;
    private List<EggImage> mEggImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initData();
        initView();
        initEvent();
    }

    private void initData() {
        mContentId = getRandomContentId();
    }

    public void initView() {
        findView();
        initFloatView();
        initWebView();
        initAlphaController();
        initEggs();
    }

    private void findView() {
        mWebView = (WebView) findViewById(R.id.webview_game);
        mFloatView = (FloatView) findViewById(R.id.floatview_game);
        mAlphaController = (SeekBar) findViewById(R.id.alpha);
        mConfirmBtn = (ImageView) findViewById(R.id.sure);
        mGuide = (ImageView) findViewById(R.id.iv_game_guide);
        mShowEggBtn = (TextView) findViewById(R.id.tv_game_egg);
        mEggsContainer = (RelativeLayout) findViewById(R.id.rl_game_eggs_container);
        mEggsLoadingHint = (TextView) findViewById(R.id.tv_game_loading);
        mSharedBtn = (ImageView) findViewById(R.id.iv_game_confirm);
        mBackBtn = (ImageView) findViewById(R.id.iv_game_back);
        mViewPager = (ViewPager) findViewById(R.id.vp_game_eggs);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout_game);
    }

    private void initEggs() {
        updateEggsCountHint(mEggsCount = EGG_INIT_COUNT);
        new GetEggImageListTask().execute();
        new IssueUGCContentTask().execute();
    }

    private class IssueUGCContentTask extends BaseASyncTask<Void, IssueUCGContentResponse> {

        @Override
        public IssueUCGContentResponse doRequest(Void param) {
            return HTTPManager.issueUCCContent("", "", "", mContentId);
        }

        @Override
        public void onSuccess(IssueUCGContentResponse response) {
            super.onSuccess(response);
            IssueUCGContent content = response.getData();
            if (content == null) return;
            List<ScenesBean> scenes = content.getScenes();
            if (scenes == null) return;
            mScenes = scenes;
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

        public UpdateUGCContentTask(Context context, boolean hasLoadingDialog) {
            super(context, hasLoadingDialog);
        }

        @Override
        public UpdateUGCContentScenesResponse doRequest(Void param) {
            super.doRequest(param);
            if (mScenes == null) return null;
            UpdateData updateData = createUpdateBean();
            return HTTPManager.updateUGCContentScenes("", mContentId, JsonUtil.toJson(updateData));
        }

        @Override
        public void onSuccess(UpdateUGCContentScenesResponse response) {
            super.onSuccess(response);
            Toast.makeText(GameActivity.this, R.string.update_egg_success, Toast.LENGTH_SHORT).show();
            createEgg();
            updateEggsCountHint(mEggsCount);
            resetFloatView();
            resetAlphaController();
        }

        @Override
        public void onFailed() {
            super.onFailed();
            Toast.makeText(GameActivity.this, R.string.update_egg_failed, Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    private UpdateData createUpdateBean() {
        UpdateData updateData = new UpdateData();
        updateData.setBgimageUrl(mContentId);
        updateData.setItemsCount(mEggsCount);
        updateData.setOrder(mEggsCount);
        updateData.setSceneId(mScenes.get(0) == null ? "" : mScenes.get(0).getSceneId());
        updateData.setTimeLimit(120);
        updateData.setTips("2");
        UpdateData.ItemsBean items = new UpdateData.ItemsBean();
        items.setItemId("1");//
        items.setX("1");
        items.setY("2");
        items.setItemMediaUrl("http://app.stemmind.com/vr/objs/08.png");
        items.setScalex("1.0");
        items.setRotatez("20");
        items.setTransparency("0.5");
        items.setEnabled("1");
        updateData.setItems(items);
        return updateData;
    }

    private void resetAlphaController() {
        mAlphaController.setProgress(100);
        mAlphaController.setVisibility(View.INVISIBLE);
        mAlphaVal = 1.0f;
    }

    private class GetEggImageListTask extends BaseASyncTask<Void, EggImageListResponse> {

        @Override
        public EggImageListResponse doRequest(Void param) {

            return HTTPManager.getEggImageList();
        }

        @Override
        public void onSuccess(EggImageListResponse response) {
            super.onSuccess(response);
            mEggsLoadingHint.setVisibility(View.INVISIBLE);
            mEggImageList = response.getData();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<EggImageFragment> fragments = new ArrayList<>();
                    int size = mEggImageList.size();
                    for (int i = 0; i < size; i++) {
                        EggImageFragment fragment = EggImageFragment.getInstance(i);
                        setEggImageListener(fragment);
                        fragments.add(fragment);
                    }
                    initViewPager(fragments);
                    for (int i = 0; i < size; i++) {
                        fillTabLayout(i);
                    }
                }
            }).start();
        }

        @Override
        public void onFailed() {
            super.onFailed();
            mEggsLoadingHint.setText(R.string.loading_failed);
        }
    }

    private void fillTabLayout(int i) {
        try {
            BitmapDrawable bitmapDrawable = getDrawableWithBitmap(i);
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setIcon(bitmapDrawable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private BitmapDrawable getDrawableWithBitmap(int i) throws IOException {
        Bitmap bitmap = createBitmapWithUrl(i);
        return new BitmapDrawable(getResources(), bitmap);
    }

    private Bitmap createBitmapWithUrl(int i) throws IOException {
        String imgurl = mEggImageList.get(i).getImgurl();
        final RequestCreator load = Picasso.with(GameActivity.this).load(imgurl);
        return load.get();
    }

    private void initViewPager(final ArrayList<EggImageFragment> fragments) {
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

        });
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setEggImageListener(EggImageFragment fragment) {
        fragment.setOnEggClickListener(new EggsAdapter.ClickInterface() {
            @Override
            public void onClick(int position, String url, Bitmap bitmap) {
                LogUtil.d(TAG, "position = " + position + ", url = " + url);
                mFloatView.setImageBitmap(bitmap);
                mFloatInfo = null;
                mUrl = url;
                initFloatView();
            }
        });
    }

    public List<EggImage> getEggImageList() {
        return mEggImageList;
    }

    private void createEgg() {
        String contentId = "1";
        int itemId = mEggsCount++;
        float alpha = mAlphaVal;
        float scale = mFloatInfo != null ? mFloatInfo.getScale() : 1.0f;
        float rotate = mFloatInfo != null ? -mFloatInfo.getRotate() : 0.0f;
        LogUtil.d(TAG, "contentId = " + contentId
                + ", itemId = " + itemId
                + ", url = " + mUrl
                + ", alpha = " + alpha
                + ", scale = " + scale
                + ", rotate = " + rotate);
        // dropItem('contentId', 'itemId', 'url', 'alpha', 'scale', "rotate")
        mWebView.loadUrl("javascript:dropItem('"
                + contentId + "' ,'"
                + itemId + "' ,'"
                + mUrl + "' ,'"
                + alpha + "' ,'"
                + scale + "' ,'"
                + rotate + "')");
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
                if (mEggsCount >= EGG_MAX_COUNT) {
                    Toast.makeText(GameActivity.this, R.string.add_egg_over_hint, Toast.LENGTH_SHORT).show();
                    return;
                }
                new UpdateUGCContentTask(GameActivity.this, true).execute();
            }
        });

        mShowEggBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEggsContainer.setVisibility(View.VISIBLE);
                positionAlphaController(ALPHA_CONTROLLER_POSITION_ABOVE_EGGS);
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.this.finish();
            }
        });
    }

    private void resetFloatView() {
        mFloatView.setVisibility(View.INVISIBLE);
        mUrl = null;
    }

    private void updateEggsCountHint(int count) {
        mShowEggBtn.setText(String.format(getResources().getString(R.string.eggs_count), count));
    }

    private void initAlphaController() {
        positionAlphaController(ALPHA_CONTROLLER_POSITION_ABOVE_EGGS);
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
        mFloatView.setVisibility(View.VISIBLE);
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
        if (mEggsContainer.getVisibility() == View.VISIBLE)
            mEggsContainer.setVisibility(View.INVISIBLE);
        positionAlphaController(ALPHA_CONTROLLER_POSITION_ABOVE_EGGS);
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
            LogUtil.d(TAG, "edit call back, contentId = " + contentId + ", sceneId = " + sceneId +
                    ", order = " + order);
            mFloatView.setVisibility(View.VISIBLE);
            updateEggsCountHint(mEggsCount-- == 0 ? 0 : mEggsCount--);
        }

        @JavascriptInterface
        public void dropItemCallBack(String msg) {
            LogUtil.d(TAG, "drop call back, msg = " + msg);
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
        oks.setTitleUrl(URLConstant.SHARED_URL + mContentId);
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
        oks.setSiteUrl(URLConstant.SHARED_URL + mContentId);

// 启动分享GUI
        oks.show(GameActivity.this);
    }
}


//    js 调app 用editItem('contentId','sceneId','order')
//    app 调 js 用 dropItem('contentId' , 'itemId','trans'  ) 返回 array ('x', 'y', 'scale')
