package storm.magicspace.activity.album;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.activity.EggGamePreviewActivity;
import storm.magicspace.activity.GameActivity;
import storm.magicspace.bean.Album;
import storm.magicspace.bean.EggInfo;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.http.reponse.AddCollectResponse;

public class AlbumInfoActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private Album info;
    private TextView tv_egg_game_zan, tv_egg_game_des, tv_egg_game_title;
    private WebView wv_egg_info;
    private TextView collectTv;

    public AlbumInfoActivity() {
        super(R.layout.album_info, CommonConstants.ACTIVITY_STYLE_WITH_TITLE_BAR);
    }

    @Override
    public void initView() {
        super.initView();
        findEventView(R.id.bt_egg_game_info_preview);
        tv_egg_game_zan = findView(R.id.tv_egg_game_zan);
        tv_egg_game_des = findView(R.id.tv_egg_game_des);
        wv_egg_info = findView(R.id.wv_egg_info);
        collectTv = findEventView(R.id.tv_collect);
        findEventView(R.id.bt_egg_game_info_download);
        tv_egg_game_title = findView(R.id.tv_egg_game_title);
        setActivityTitle(getString(R.string.game_info));
        setTitleRightBtVisibility(View.GONE);
        setTitleLeftBtVisibility(View.VISIBLE);
        Intent intent = this.getIntent();
        info = (Album) intent.getSerializableExtra("album");
        if (info != null) {
            String appreciateCount = info.getAppreciateCount() == null ? "" : info.getAppreciateCount();
            tv_egg_game_title.setText(info.getTitle());
            tv_egg_game_zan.setText(appreciateCount);
            tv_egg_game_des.setText(info.getDescription() == null ? "" : info.getDescription());
            initWebView(info.getContentId() == null ? "" : info.getContentId());
        }

    }

    private void initWebView(String mContentId) {
        wv_egg_info.getSettings().setJavaScriptEnabled(true);
        wv_egg_info.getSettings().setDefaultTextEncodingName("gb2312");
        wv_egg_info.loadUrl("http://app.stemmind.com/vr/a/preview.php?c=" + mContentId);
        wv_egg_info.setWebViewClient(new WebViewClient());
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId) {
            case R.id.bt_egg_game_info_preview:
                Intent intent = new Intent(AlbumInfoActivity.this, EggGamePreviewActivity.class);
                EggInfo eggInfo = new EggInfo();
                eggInfo.contentId = info.getContentId();
                intent.putExtra("game_info", eggInfo);
                this.startActivity(intent);
                break;

            case R.id.bt_egg_game_info_download:
                Intent gameIntent = new Intent(AlbumInfoActivity.this, GameActivity.class);
                gameIntent.putExtra("contentId", info.getContentId());
                this.startActivity(gameIntent);
                break;
            case R.id.tv_collect:
                new AddCollectTask().execute(info.getContentId());
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class AddCollectTask extends BaseASyncTask<String, AddCollectResponse> {
        @Override
        public AddCollectResponse doRequest(String param) {
            return HTTPManager.addCollect(param);
        }

        @Override
        public void onSuccess(AddCollectResponse addCollectResponse) {
            super.onSuccess(addCollectResponse);
            Toast.makeText(AlbumInfoActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccessWithoutResult(AddCollectResponse addCollectResponse) {
            super.onSuccessWithoutResult(addCollectResponse);
            Toast.makeText(AlbumInfoActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
        }
    }
}