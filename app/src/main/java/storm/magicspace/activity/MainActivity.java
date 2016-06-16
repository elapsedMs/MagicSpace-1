package storm.magicspace.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.adapter.HomeViewPagerAdapter;
import storm.magicspace.fragment.AlbumFragment;
import storm.magicspace.fragment.EggFragment;
import storm.magicspace.fragment.MyFragment;
import storm.magicspace.fragment.SettingFragment;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager viewPager;
    private HomeViewPagerAdapter homeViewPagerAdapter;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();
        viewPager = findView(R.id.viewpager);
        initViewPager();
    }

    private void initViewPager() {
        AlbumFragment albumFragment = new AlbumFragment();
        EggFragment eggFragment = new EggFragment();
        MyFragment myFragment = new MyFragment();
        SettingFragment settingFragment = new SettingFragment();
        fragmentList.add(albumFragment);
        fragmentList.add(eggFragment);
        fragmentList.add(myFragment);
        fragmentList.add(settingFragment);
        homeViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(homeViewPagerAdapter);
        viewPager.setOnPageChangeListener(this);
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

