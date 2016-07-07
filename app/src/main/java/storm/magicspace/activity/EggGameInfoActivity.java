package storm.magicspace.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.bean.EggInfo;

public class EggGameInfoActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private EggInfo info;
    private TextView tv_egg_game_zan, tv_egg_game_des, tv_egg_game_person_count, tv_egg_game_time, tv_my_best_time, tv_egg_game_title, tv_egg_count;
    private WebView wv_egg_info;

    public EggGameInfoActivity() {
        super(R.layout.activity_egg_game_info, CommonConstants.ACTIVITY_STYLE_WITH_TITLE_BAR);
    }

    @Override
    public void initView() {
        super.initView();
        findEventView(R.id.bt_egg_game_info_preview);
        tv_egg_game_zan = findView(R.id.tv_egg_game_zan);
        tv_egg_game_des = findView(R.id.tv_egg_game_des);
        tv_egg_game_person_count = findView(R.id.tv_egg_game_person_count);
        tv_egg_game_time = findView(R.id.tv_egg_game_time);
        tv_my_best_time = findView(R.id.tv_my_best_time);
        wv_egg_info = findView(R.id.wv_egg_info);
        tv_egg_game_title = findView(R.id.tv_egg_game_title);
        tv_egg_count = findView(R.id.tv_egg_count);
        setActivityTitle(getString(R.string.game_info));
        setTitleRightBtVisibility(View.GONE);
        setTitleLeftBtVisibility(View.VISIBLE);
        Intent intent = this.getIntent();
        info = (EggInfo) intent.getSerializableExtra("game_info");
        if (info != null) {
            String appreciateCount = info.appreciateCount == null ? "" : info.appreciateCount;
            tv_egg_game_zan.setText(appreciateCount);
            tv_egg_game_des.setText(info.description == null ? "" : info.description);
            String playCount = info.playCount == null ? "" : info.playCount;
            tv_egg_game_person_count.setText(playCount + getString(R.string.play_count));
            String avgTime = info.avgtime == null ? "" : info.avgtime;
            tv_egg_game_time.setText(getString(R.string.avgtime) + avgTime);
            tv_my_best_time.setText(info.myBestTime == null ? "" : info.myBestTime);
            //ToDo add my best time
            tv_egg_game_title.setText(info.title);
            initWebView(info.contentId == null ? "" : info.contentId);
            String egg_count = info.itemCount == null ? "" : info.itemCount;
            tv_egg_count.setText(getResources().getString(R.string.egg_count) + egg_count);
        }

    }

    private void initWebView(String mContentId) {
        wv_egg_info.getSettings().setJavaScriptEnabled(true);
        wv_egg_info.getSettings().setDefaultTextEncodingName("gb2312");
        wv_egg_info.loadUrl("http://app.stemmind.com/vr/a/preview.php?ua=app&s=ugc&c=" + mContentId);
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
                Intent intent = new Intent(EggGameInfoActivity.this, EggGamePreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("game_info", info);
                intent.putExtras(bundle);
                this.startActivity(intent);
                break;

            case R.id.bt_egg_game_info_download:
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

}