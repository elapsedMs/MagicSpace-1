package storm.magicspace.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.base.BaseActivity;
import storm.commonlib.common.base.BaseOnClickListener;
import storm.magicspace.R;
import storm.magicspace.adapter.HomeViewPagerAdapter;
import storm.magicspace.fragment.AlbumFragment;
import storm.magicspace.fragment.EggFragment;
import storm.magicspace.fragment.MyFragment;
import storm.magicspace.fragment.SettingFragment;
import storm.magicspace.view.HomeTabView;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager viewPager;
    private HomeViewPagerAdapter homeViewPagerAdapter;
    private HomeTabView albumHtv;
    private HomeTabView eggHtv;
    private HomeTabView myHtv;
    private HomeTabView settingHtv;

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
        albumHtv = findView(R.id.album_tabview);
        eggHtv = findView(R.id.egg_tabview);
        myHtv = findView(R.id.my_tabview);
        settingHtv = findView(R.id.setting_tabview);
        viewPager = findView(R.id.viewpager);
        initAndListener();
        initViewPager();
    }

    private void initAndListener() {
        viewPager.setCurrentItem(0);
        albumHtv.setSelected(true);
        albumHtv.setOnClickListener(this);
        eggHtv.setOnClickListener(this);
        myHtv.setOnClickListener(this);
        settingHtv.setOnClickListener(this);
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
        switch (position) {
            case 0:
                selectChange(true, false, false, false);
                break;
            case 1:
                selectChange(false, false, true, false);
                break;
            case 2:
                selectChange(false, false, false, true);
                break;
            case 3:
                selectChange(false, true, false, false);
                break;
        }
    }

    private void selectChange(boolean selected, boolean selected3, boolean selected1, boolean selected2) {
        albumHtv.setSelected(selected);
        eggHtv.setSelected(selected1);
        myHtv.setSelected(selected2);
        settingHtv.setSelected(selected3);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onLocalClicked(int resId) {
        switch (resId) {
            case R.id.album_tabview:
                viewPager.setCurrentItem(0);
                selectChange(true, false, false, false);
                break;
            case R.id.egg_tabview:
                albumHtv.setSelected(false);
                eggHtv.setSelected(true);
                myHtv.setSelected(false);
                settingHtv.setSelected(false);
                viewPager.setCurrentItem(1);
                break;
            case R.id.my_tabview:
                selectChange(false, false, false, true);
                viewPager.setCurrentItem(2);
                break;
            case R.id.setting_tabview:
                selectChange(false, true, false, false);
                viewPager.setCurrentItem(3);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

