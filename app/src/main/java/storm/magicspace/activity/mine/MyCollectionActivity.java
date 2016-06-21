package storm.magicspace.activity.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.base.BaseActivity;
import storm.commonlib.common.view.TitleBar;
import storm.magicspace.R;
import storm.magicspace.adapter.HomeViewPagerAdapter;
import storm.magicspace.fragment.GameCollectionFragment;
import storm.magicspace.fragment.TopicCollectionFragment;

public class MyCollectionActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager viewPager;

    public MyCollectionActivity() {
        super(R.layout.activity_my_collection);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityTitle("我的收藏");
        setTitleLeftBtVisibility(View.VISIBLE);
        initViewPager();
        setOnSelectTitleClickedListener(new TitleBar.OnSelectTitleClickedListener() {
            @Override
            public void leftClicked() {
            }

            @Override
            public void rightClicked() {
            }
        });
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.my_vp_my_collection);

        GameCollectionFragment gameCollectionFragment = new GameCollectionFragment();
        TopicCollectionFragment topicCollectionFragment = new TopicCollectionFragment();
        fragmentList.add(gameCollectionFragment);
        fragmentList.add(topicCollectionFragment);
        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(this.getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(1);
        useBothTitle();
        setSecondTitleBackground(android.R.color.white);
        setSelectTitle("游戏", "主题");


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                leftTitleSelected();
                break;
            case 1:
                rightTitleSelected();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}