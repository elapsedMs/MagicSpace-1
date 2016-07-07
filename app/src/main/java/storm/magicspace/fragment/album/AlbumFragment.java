package storm.magicspace.fragment.album;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;
import storm.magicspace.activity.MainActivity;
import storm.magicspace.adapter.HomeViewPagerAdapter;
import storm.magicspace.event.ViewPagerEvent;

/**
 * Created by gdq on 16/6/15.
 */
public class AlbumFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    public MainActivity mainActivity;
    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private MyHandler myHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("gdq", "AlbumFragment onCreateView");
        View view = inflater.inflate(R.layout.fragment_album, null);
        mainActivity = (MainActivity) getActivity();
        myHandler = new MyHandler(this);
        mainActivity.setHandler(myHandler);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ViewPagerEvent viewPagerEvent) {
        viewPager.setCurrentItem(viewPagerEvent.position);
    }

    @Override
    public void onStart() {
        Log.d("gdq", "AlbumFragment onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("gdq", "AlbumFragment onResume");
        super.onResume();
    }


    @Override
    public void onPause() {
        Log.d("gdq", "AlbumFragment onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("gdq", "AlbumFragment onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("gdq", "AlbumFragment onDestroyView");
        super.onDestroyView();
    }


    @Override
    public void initView(View view) {
        Log.d("gdq", "AlbumFragment initView");
        super.initView(view);
        viewPager = (ViewPager) view.findViewById(R.id.album_viewpager);
        initViewPager();
    }

    private void initViewPager() {
        OnlineFragment onlineFragment = new OnlineFragment();
        NativeFragment nativeFragment = new NativeFragment();
        fragmentList.add(onlineFragment);
        fragmentList.add(nativeFragment);
        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(1);
//        viewPager.setHorizontalScrollBarEnabled(false);

        mainActivity.leftTitleSelected();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mainActivity.leftTitleSelected();
                break;
            case 1:
                mainActivity.rightTitleSelected();
                break;
        }

    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDestroy() {
        fragmentList.removeAll(fragmentList);
        fragmentList = null;
        myHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    private static class MyHandler extends Handler {
        WeakReference<AlbumFragment> weakReference;

        public MyHandler(AlbumFragment albumFragment) {
            this.weakReference = new WeakReference<>(albumFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            AlbumFragment albumFragment = weakReference.get();
            switch (msg.what) {
                case LEFT:
                    albumFragment.viewPager.setCurrentItem(0);
                    break;
                case RIGHT:
                    albumFragment.viewPager.setCurrentItem(1);
                    break;
            }
        }
    }
}