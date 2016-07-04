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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.util.BaseUtil;
import storm.commonlib.common.util.JsonUtil;
import storm.commonlib.common.util.LogUtil;
import storm.commonlib.common.util.SharedPreferencesUtil;
import storm.magicspace.R;
import storm.magicspace.adapter.EggsAdapter;
import storm.magicspace.bean.IssueUCGContent;
import storm.magicspace.bean.UGCItem;
import storm.magicspace.bean.UGCScene;
import storm.magicspace.bean.UGCUpdateContent;
import storm.magicspace.bean.httpBean.EggImage;
import storm.magicspace.bean.httpBean.EggImageListResponse;
import storm.magicspace.bean.httpBean.IssueUCGContentResponse;
import storm.magicspace.bean.httpBean.UpdateUGCContentScenesResponse;
import storm.magicspace.fragment.EggImageFragment;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.http.URLConstant;
import storm.magicspace.view.FloatView;
import storm.magicspace.view.FloatView.FloatInfo;

import static storm.magicspace.bean.UGCUpdateContent.EggInfo;

public class GameActivity extends FragmentActivity {

    public static final String TAG = GameActivity.class.getSimpleName();
    public static final String ALPHA_CONTROLLER_POSITION_PARENT_BOTTOM = "bottom";
    public static final String ALPHA_CONTROLLER_POSITION_ABOVE_EGGS = "above_eggs";
    public static final int EGG_INIT_COUNT = 0;
    public static final int EGG_MAX_COUNT = 5;
    public static final String DEFAULT_CONTENT_ID = "3403";

    private WebView mWebView;
    private FloatView mFloatView;

    private ImageView mBackBtn;
    private ImageView mConfirmBtn;
    private ImageView mDeleteBtn;
    private ImageView mSharedBtn;
    private TextView mShowEggBtn;

    private RelativeLayout mEggsContainer;
    private TextView mLoadingHint;
    private ViewPager mEggPager;
    private TabLayout mEggTab;

    private SeekBar mAlphaController;
    private ImageView mGuide;

    private float mAlphaVal = 1.0f;
    private int mEggsCount = 1;
    private FloatInfo mFloatInfo;
    private boolean isAlphaControllerShowing = false;
    private String mUrl;
    private String mContentId;
    private List<EggImage> mEggImageList;
    private Map<String, UGCItem> mEggInfos;
    // Get from issue, use for update
    private UGCScene mUCGScene;
    private List<UGCItem> mUGCItems;
    private UGCItem mCurrentItem;
    private int mCurrentState = 1;
    private boolean mWebViewInit = false;
    private String mEggKey;

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
        mEggInfos = new HashMap();
    }

    public void initView() {
        findView();
        initFloatView();
        initWebView();
        syncFloatView(false);
        initAlphaController();
        initEggs();
    }

    private void findView() {
        mWebView = (WebView) findViewById(R.id.webview_game);
        mFloatView = (FloatView) findViewById(R.id.floatview_game);
        mAlphaController = (SeekBar) findViewById(R.id.alpha);

        mBackBtn = (ImageView) findViewById(R.id.iv_game_back);
        mConfirmBtn = (ImageView) findViewById(R.id.iv_game_confirm);
        mDeleteBtn = (ImageView) findViewById(R.id.iv_game_delete);
        mSharedBtn = (ImageView) findViewById(R.id.iv_game_shared);
        mShowEggBtn = (TextView) findViewById(R.id.tv_game_egg);

        mGuide = (ImageView) findViewById(R.id.iv_game_guide);
        mEggsContainer = (RelativeLayout) findViewById(R.id.rl_game_eggs_container);
        mLoadingHint = (TextView) findViewById(R.id.tv_game_loading);
        mEggPager = (ViewPager) findViewById(R.id.vp_game_eggs);
        mEggTab = (TabLayout) findViewById(R.id.tab_layout_game);
    }

    /**
     * sync button status when float view status change
     */
    private void syncFloatView(boolean floatViewShowing) {
        if (floatViewShowing) {
            mCurrentState = 0;
            mConfirmBtn.setVisibility(View.VISIBLE);
            mDeleteBtn.setVisibility(View.VISIBLE);
            mSharedBtn.setVisibility(View.GONE);
        } else {
            mCurrentState = 1;
            mConfirmBtn.setVisibility(View.INVISIBLE);
            mDeleteBtn.setVisibility(View.INVISIBLE);
            mSharedBtn.setVisibility(View.VISIBLE);
        }
        reportEditorState();
    }

    private void reportEditorState() {
        if (mWebViewInit) {
            mWebView.loadUrl("javascript:setEditorState('" + mCurrentState + "')");
        }
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
            List<UGCScene> scenes = content.getScenes();
            if (scenes == null) return;
            mUCGScene = scenes.get(0);
            mUGCItems = mUCGScene.getItems();
            for (UGCItem ugcItem : mUGCItems) {
                mEggInfos.put(ugcItem.getItemId(), ugcItem);
            }
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
            if (mUCGScene == null) return null;
            //UGCUpdateContent content = createUpdateBean();
            //return HTTPManager.updateUGCContentScenes("", mContentId, JsonUtil.toJson(content));
            mEggKey = BaseUtil.MD5(System.currentTimeMillis() + "");
            mCurrentItem.setItemId(mEggKey);
            mUGCItems.add(mCurrentItem);
            mUCGScene.setItems(mUGCItems);
            mUCGScene.setItemsCount(mEggsCount + "");
            mEggInfos.put(mEggKey, mCurrentItem);
            return HTTPManager.updateUGCContentScenes("", mContentId, JsonUtil.toJson(mUCGScene));
        }

        @Override
        public void onSuccess(UpdateUGCContentScenesResponse response) {
            super.onSuccess(response);
            Toast.makeText(GameActivity.this, R.string.update_egg_success, Toast.LENGTH_SHORT).show();
            createEgg();
            resetFloatView();
            resetAlphaController();
            mEggsCount = mEggsCount + 1;
            updateEggsCountHint(mEggsCount);
        }

        @Override
        public void onFailed() {
            super.onFailed();
            Toast.makeText(GameActivity.this, R.string.update_egg_failed, Toast.LENGTH_SHORT).show();
            mUGCItems.remove(mCurrentItem);
            mEggInfos.remove(mEggKey);
        }
    }

    @NonNull
    private UGCUpdateContent createUpdateBean() {

        UGCUpdateContent content = new UGCUpdateContent();

        content.setBgimageUrl(mContentId);
        content.setItemsCount(mEggsCount);
        content.setOrder(mEggsCount);
        content.setSceneId(mUCGScene == null ? "" : mUCGScene.getSceneId());
        content.setTimeLimit(120);
        content.setTips("2");

        EggInfo eggInfo = new EggInfo();

        eggInfo.setX("1");
        eggInfo.setY("2");
        eggInfo.setScalex("1.0");
        eggInfo.setRotatez("20");
        eggInfo.setTransparency("0.5");

        eggInfo.setItemId("1");//
        eggInfo.setItemMediaUrl("http://app.stemmind.com/vr/objs/08.png");
        eggInfo.setEnabled("1");

        content.setItems(eggInfo);
        return content;
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
            mLoadingHint.setVisibility(View.INVISIBLE);
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
                        // init viewpager first
                        fillTabLayout(i);
                    }
                }
            }).start();
        }

        @Override
        public void onFailed() {
            super.onFailed();
            mLoadingHint.setText(R.string.loading_failed);
        }
    }

    private void fillTabLayout(int pos) {
        try {
            BitmapDrawable bitmapDrawable = getDrawableWithBitmap(pos);
            TabLayout.Tab tab = mEggTab.getTabAt(pos);
            if (tab != null) {
                tab.setIcon(bitmapDrawable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private BitmapDrawable getDrawableWithBitmap(int pos) throws IOException {
        String url = mEggImageList.get(pos).getImgurl();
        Bitmap bitmap = createBitmapWithUrl(url);
        return new BitmapDrawable(getResources(), bitmap);
    }

    private Bitmap createBitmapWithUrl(String url) throws IOException {
        RequestCreator load = Picasso.with(GameActivity.this).load(url);
        return load.get();
    }

    private void initViewPager(final ArrayList<EggImageFragment> fragments) {
        mEggPager.setOffscreenPageLimit(fragments.size());
        mEggPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

        });
        mEggTab.setupWithViewPager(mEggPager);
    }

    private void setEggImageListener(EggImageFragment fragment) {
        fragment.setOnEggClickListener(new EggsAdapter.ClickInterface() {
            @Override
            public void onClick(int position, String url, Bitmap bitmap) {
                LogUtil.d(TAG, "position = " + position + ", url = " + url);
                mFloatView.setImageBitmap(bitmap);

                mCurrentItem = new UGCItem();

                mCurrentItem.setX("0");
                mCurrentItem.setY("0");
                mCurrentItem.setScalex(1.0f + "");
                mCurrentItem.setRotatez(0.0f + "");
                mCurrentItem.setTransparency(1.0f + "");

                mCurrentItem.setItemMediaUrl(url);
                mCurrentItem.setEnabled("1");
                //mCurrentItem.setItemId(mEggsCount + "");

                mFloatInfo = null;
                mUrl = url;
                initFloatView();
                syncFloatView(true);
            }
        });
    }

    public List<EggImage> getEggImageList() {
        return mEggImageList;
    }

    private void createEgg() {
        int itemId = mEggsCount;
        float alpha = mAlphaVal;
        float scale = mFloatInfo != null ? mFloatInfo.getScale() : 1.0f;
        float rotate = mFloatInfo != null ? -mFloatInfo.getRotate() : 0.0f;
        LogUtil.d(TAG, "contentId = " + mContentId
                + ", itemId = " + itemId
                + ", url = " + mUrl
                + ", alpha = " + alpha
                + ", scale = " + scale
                + ", rotate = " + rotate);
        // dropItem('contentId', 'itemId', 'url', 'alpha', 'scale', "rotate")
        mWebView.loadUrl("javascript:dropItem('"
                + mContentId + "' ,'"
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

        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFloatView();
            }
        });
    }

    private void resetFloatView() {
        mFloatView.setImageBitmap(null);
        syncFloatView(false);
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
                Log.d(TAG, "alpha = " + mAlphaVal);
                mFloatView.setAlpha(mAlphaVal);
                mCurrentItem.setTransparency(mAlphaVal + "");
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
                if (mCurrentItem == null) {
                    mCurrentItem = new UGCItem();
                }
                mCurrentItem.setX("0");
                mCurrentItem.setY("0");
                mCurrentItem.setScalex(floatInfo.getScale() + "");
                mCurrentItem.setRotatez(floatInfo.getRotate() + "");
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
        mWebView.loadUrl("http://app.stemmind.com/vr/a/vreditor.php?ua=app&c="+mContentId);
        ContainerView containerView = new ContainerView();
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.addJavascriptInterface(containerView, "containerView");
        mWebViewInit = true;
    }

    private class ContainerView {

        @JavascriptInterface
        public void editItem(String contentId, String sceneId, String order) {
            LogUtil.d(TAG, "edit call back, contentId = " + contentId + ", sceneId = " + sceneId +
                    ", order = " + order);
//            mFloatView.setVisibility(View.VISIBLE);
            mEggsCount = mEggsCount - 1;
            mEggsCount = mEggsCount >= 0 ? mEggsCount : 0;
            UGCItem ugcItem = mEggInfos.get(order);
            if (ugcItem == null) return;
            String url = ugcItem.getItemMediaUrl();
            float rotate = Float.parseFloat(ugcItem.getRotatez());
            float scale = Float.parseFloat(ugcItem.getScalex());
            float alpha = Float.parseFloat(ugcItem.getTransparency());
            createAndShowBitmap(url, scale, rotate, alpha);
            updateCountHint();
        }

//        @JavascriptInterface
//        public int currentState() {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(GameActivity.this, "current state = " + mCurrentState, Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            return mCurrentState;
//        }

        @JavascriptInterface
        public void dropItemCallBack(String msg) {
            LogUtil.d(TAG, "drop call back, msg = " + msg);
        }
    }

    private void updateCountHint() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateEggsCountHint(mEggsCount);
            }
        });
    }

    private void createAndShowBitmap(final String url, final float scale, final float rotate,
                                     final float alpha) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = createBitmapWithUrl(url);
                    showBitmap(bitmap, scale, rotate, alpha);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showBitmap(final Bitmap bitmap, final float scale, final float rotate,
                            float alpha) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mFloatView.setImageBitmap(null);
                mFloatView.setFloatView(bitmap, scale, rotate);
            }
        });
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
