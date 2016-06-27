package storm.magicspace.activity.album;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.adapter.HomeViewPagerAdapter;
import storm.magicspace.adapter.OnlineRVAdapter;
import storm.magicspace.bean.Album;
import storm.magicspace.fragment.album.FengJingFragment;
import storm.magicspace.fragment.album.GaoQingFragment;
import storm.magicspace.fragment.album.ShiShangFragment;
import storm.magicspace.fragment.album.XuanKuFragment;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.http.reponse.AlbumResponse;
import storm.magicspace.view.GridItemDecoration;

/**
 * Created by gdq on 16/6/16.
 */
public class ClassifyRecommendActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private RelativeLayout oneRl;
    private RelativeLayout twoRl;
    private RelativeLayout threeRl;
    private RelativeLayout fourRl;
    private TextView oneTv;
    private TextView twoTv;
    private TextView threeTv;
    private TextView fourTv;
    private LinearLayout lineOneLl;
    private LinearLayout lineTwoLl;
    private LinearLayout lineThreeLl;
    private LinearLayout lineFourLl;
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private OnlineRVAdapter adapter;
    private List<Album> albumList = new ArrayList<>();

    public ClassifyRecommendActivity() {
        super(R.layout.activity_classify_recommend);
    }

    @Override
    public void initView() {
        super.initView();
        setActivityTitle("推荐");
        setTitleLeftBtVisibility(View.VISIBLE);
        oneRl = (RelativeLayout) findViewById(R.id.rl_one);
        twoRl = (RelativeLayout) findViewById(R.id.rl_two);
        threeRl = (RelativeLayout) findViewById(R.id.rl_three);
        fourRl = (RelativeLayout) findViewById(R.id.rl_four);

        oneTv = (TextView) findViewById(R.id.tv_one);
        twoTv = (TextView) findViewById(R.id.tv_two);
        threeTv = (TextView) findViewById(R.id.tv_three);
        fourTv = (TextView) findViewById(R.id.tv_four);

        lineOneLl = (LinearLayout) findViewById(R.id.line_one);
        lineTwoLl = (LinearLayout) findViewById(R.id.line_two);
        lineThreeLl = (LinearLayout) findViewById(R.id.line_three);
        lineFourLl = (LinearLayout) findViewById(R.id.line_four);
        initViewPager();
        showOne();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        initRecyclerView();
        new TestTask().execute();

    }

    private void initRecyclerView() {
        adapter = new OnlineRVAdapter(albumList, this, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new GridItemDecoration(this));
        adapter.setOnRecyclerViewClickListener(new OnlineRVAdapter.OnRecyclerViewClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url", albumList.get(position).getUrl());
                goToNext(WebActivity.class, bundle);
            }

            @Override
            public void onBtnClick(int position) {
                goToNext(CacheingActivity.class);
            }
        });
    }


    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new GaoQingFragment());
        fragmentList.add(new FengJingFragment());
        fragmentList.add(new XuanKuFragment());
        fragmentList.add(new ShiShangFragment());
    }

    @Override
    public void initListener() {
        super.initListener();
        oneRl.setOnClickListener(this);
        twoRl.setOnClickListener(this);
        threeRl.setOnClickListener(this);
        fourRl.setOnClickListener(this);
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId) {
            case R.id.rl_one:
                viewPager.setCurrentItem(0);
                break;
            case R.id.rl_two:
                viewPager.setCurrentItem(1);
                break;
            case R.id.rl_three:
                viewPager.setCurrentItem(2);
                break;
            case R.id.rl_four:
                viewPager.setCurrentItem(3);
                break;
        }
    }

    public void showOne() {
        twoTv.setTextColor(getResources().getColor(android.R.color.black));
        threeTv.setTextColor(getResources().getColor(android.R.color.black));
        fourTv.setTextColor(getResources().getColor(android.R.color.black));
        oneTv.setTextColor(getResources().getColor(storm.commonlib.R.color.title_color));
        lineOneLl.setVisibility(View.VISIBLE);
        lineTwoLl.setVisibility(View.GONE);
        lineThreeLl.setVisibility(View.GONE);
        lineFourLl.setVisibility(View.GONE);
    }

    public void showTwo() {
        twoTv.setTextColor(getResources().getColor(storm.commonlib.R.color.title_color));
        threeTv.setTextColor(getResources().getColor(android.R.color.black));
        fourTv.setTextColor(getResources().getColor(android.R.color.black));
        oneTv.setTextColor(getResources().getColor(android.R.color.black));
        lineOneLl.setVisibility(View.GONE);
        lineTwoLl.setVisibility(View.VISIBLE);
        lineThreeLl.setVisibility(View.GONE);
        lineFourLl.setVisibility(View.GONE);
    }

    public void showThree() {
        twoTv.setTextColor(getResources().getColor(android.R.color.black));
        threeTv.setTextColor(getResources().getColor(storm.commonlib.R.color.title_color));
        fourTv.setTextColor(getResources().getColor(android.R.color.black));
        oneTv.setTextColor(getResources().getColor(android.R.color.black));
        lineOneLl.setVisibility(View.GONE);
        lineTwoLl.setVisibility(View.GONE);
        lineThreeLl.setVisibility(View.VISIBLE);
        lineFourLl.setVisibility(View.GONE);
    }

    public void showFour() {
        twoTv.setTextColor(getResources().getColor(android.R.color.black));
        threeTv.setTextColor(getResources().getColor(android.R.color.black));
        fourTv.setTextColor(getResources().getColor(storm.commonlib.R.color.title_color));
        oneTv.setTextColor(getResources().getColor(android.R.color.black));
        lineOneLl.setVisibility(View.GONE);
        lineTwoLl.setVisibility(View.GONE);
        lineThreeLl.setVisibility(View.GONE);
        lineFourLl.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                showOne();
                break;
            case 1:
                showTwo();
                break;
            case 2:
                showThree();
                break;
            case 3:
                showFour();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class TestTask extends BaseASyncTask<Void, AlbumResponse> {
        @Override
        public AlbumResponse doRequest(Void param) {
            return HTTPManager.test("recommanded");

        }

        @Override
        public void onSuccess(AlbumResponse albumResponse) {
            super.onSuccess(albumResponse);
            albumList.clear();
            albumList.addAll(albumResponse.data);
            adapter.notifyDataSetChanged();}
    }
}
