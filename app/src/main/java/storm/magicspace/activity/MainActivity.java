package storm.magicspace.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import storm.commonlib.common.base.BaseActivity;
import storm.commonlib.common.view.TitleBar;
import storm.magicspace.R;
import storm.magicspace.activity.album.GuessYouLikeActivity;
import storm.magicspace.activity.mine.MyCollectionActivity;
import storm.magicspace.adapter.HomeViewPagerAdapter;
import storm.magicspace.dialog.ListDialog;
import storm.magicspace.event.PublishEvent;
import storm.magicspace.fragment.EggFragment;
import storm.magicspace.fragment.MyFragment;
import storm.magicspace.fragment.SettingFragment;
import storm.magicspace.fragment.album.AlbumFragment;
import storm.magicspace.view.HomeTabView;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private final String[] data = new String[]{"从图库选择", "从收藏选择"};

    private static final int ALBUM = 1;
    private static final int EGG = 2;
    private static final int MY = 3;
    private static final int SETTING = 4;
    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager viewPager;
    private HomeViewPagerAdapter homeViewPagerAdapter;
    private HomeTabView albumHtv;
    private HomeTabView eggHtv;
    private HomeTabView myHtv;
    private HomeTabView settingHtv;
    private ImageView addIv;
    private Handler handler;
    private ListDialog listDialog;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        PushAgent.getInstance(this).onAppStart();

        PushAgent mPushAgent = PushAgent.getInstance(getApplication());
        mPushAgent.enable();
    }

    @Override
    public void initView() {
        setTitleLeftBtVisibility(View.GONE);
        setTitleRightBtVisibility(View.GONE);
        super.initView();
        addIv = findView(R.id.add_Btn);
        albumHtv = findView(R.id.album_tabview);
        eggHtv = findView(R.id.egg_tabview);
        myHtv = findView(R.id.my_tabview);
        settingHtv = findView(R.id.setting_tabview);
        viewPager = findView(R.id.viewpager);
        initAndListener();
        initViewPager();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void initAndListener() {
        selectedTab(ALBUM);
        setOnSelectTitleClickedListener(new TitleBar.OnSelectTitleClickedListener() {
            @Override
            public void leftClicked() {
                handler.sendEmptyMessage(AlbumFragment.LEFT);
            }

            @Override
            public void rightClicked() {
                handler.sendEmptyMessage(AlbumFragment.RIGHT);
            }
        });
        albumHtv.setOnClickListener(this);
        eggHtv.setOnClickListener(this);
        myHtv.setOnClickListener(this);
        settingHtv.setOnClickListener(this);
        addIv.setOnClickListener(this);
    }

    private void initViewPager() {
        AlbumFragment albumFragment = new AlbumFragment();
        EventBus.getDefault().register(albumFragment);
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
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                selectedTab(ALBUM);
                break;
            case 1:
                selectedTab(EGG);
                break;
            case 2:
                selectedTab(MY);
                break;
            case 3:
                selectedTab(SETTING);
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
                break;
            case R.id.egg_tabview:
                viewPager.setCurrentItem(1);
                break;
            case R.id.my_tabview:
                viewPager.setCurrentItem(2);
                break;
            case R.id.setting_tabview:
                viewPager.setCurrentItem(3);
                break;
            case R.id.add_Btn:
                ListDialog.Buider buider = new ListDialog.Buider(MainActivity.this);
                listDialog = buider.setData(Arrays.asList(data)).setItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            //在线
                            case 0:
//                                ViewPagerEvent viewPagerEvent = new ViewPagerEvent();
//                                viewPagerEvent.position = 0;
//                                EventBus.getDefault().post(viewPagerEvent);
//                                viewPager.setCurrentItem(0);
                                startActivity(new Intent(MainActivity.this, GuessYouLikeActivity.class));
                                break;
                            //收藏
                            case 1:
//                                ViewPagerEvent viewPagerEvent1 = new ViewPagerEvent();
//                                viewPagerEvent1.position = 1;
//                                EventBus.getDefault().post(viewPagerEvent1);
//                                viewPager.setCurrentItem(0);
                                startActivity(new Intent(MainActivity.this, MyCollectionActivity.class));
                                break;
                            //随机
//                            case 2:
//                                goToNext(GameActivity.class);
//                                break;
                        }
                        listDialog.dismiss();
                    }
                }).create();
                listDialog.show();
                break;
        }
    }

    private void selectedTab(int type) {
        Log.d("gdq", "1");
        switch (type) {
            case ALBUM:
                useSelectTitle("在线", "收藏");
                selectChange(true, false, false, false);
                break;
            case EGG:
                useNormalTitle();
                setActivityTitle("作品区");
                selectChange(false, false, true, false);
                break;
            case MY:
                withoutTitle();
                selectChange(false, false, false, true);
                break;
            case SETTING:
                useNormalTitle();
                setActivityTitle("设置");
                selectChange(false, true, false, false);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PublishEvent event) {
        selectedTab(EGG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    // 改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            tryExit();
        }
        return false;
    }
}

