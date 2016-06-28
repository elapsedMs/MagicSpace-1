package storm.magicspace.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.adapter.ViewPagerAdatper;
import storm.magicspace.bean.CirclePic;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.bean.httpBean.CirclePicResponse;

public class EggGameInfoActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private List<CirclePic> circlePicList = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();
    public EggGameInfoActivity() {
        super(R.layout.activity_egg_game_info, CommonConstants.ACTIVITY_STYLE_WITH_TITLE_BAR);
    }

    @Override
    public void initView() {
        super.initView();
        viewPager = (ViewPager)findViewById(R.id.viewpager_egg);
        viewPager.setOnPageChangeListener(EggGameInfoActivity.this);
        findEventView(R.id.bt_egg_game_info_preview);
        findEventView(R.id.bt_egg_game_info_download);
        setActivityTitle(getString(R.string.game_info));
        setTitleRightBtVisibility(View.GONE);
        setTitleLeftBtVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
        new CirclePicTask().execute();
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId) {
            case R.id.bt_egg_game_info_preview:
                goToNext(EggGamePreviewActivity.class);
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

    private class CirclePicTask extends BaseASyncTask<Void,CirclePicResponse> {
        @Override
        public CirclePicResponse doRequest(Void param) {
            return HTTPManager.getAlbumCirclePic();
        }

        @Override
        public void onSuccess(CirclePicResponse albumResponse) {
            super.onSuccess(albumResponse);
            circlePicList = albumResponse.data;
            imageViews.clear();
//            title.setText(circlePicList.get(0).getTitle());
//            desc.setText("没字段");
            for (int i = 0; i < circlePicList.size(); i++) {
                ImageView imageView = new ImageView(EggGameInfoActivity.this);
                Picasso.with(EggGameInfoActivity.this).load(circlePicList.get(i).getUrl()).into(imageView);
                imageViews.add(imageView);
            }
            viewPager.setAdapter(new ViewPagerAdatper(imageViews));

        }
    }
}