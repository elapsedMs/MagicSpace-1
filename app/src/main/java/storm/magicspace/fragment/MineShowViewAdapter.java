package storm.magicspace.fragment;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import storm.magicspace.view.MineShowView;

/**
 * Created by lixiaolu on 16/7/4.
 */
public class MineShowViewAdapter extends PagerAdapter {

    private final List<MineShowView> mineShowViews;

    public MineShowViewAdapter(List<MineShowView> mineShowViews) {
        this.mineShowViews = mineShowViews;
    }

    @Override
    public int getCount() {
        return mineShowViews == null ? 0 : mineShowViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mineShowViews.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mineShowViews.get(position));
    }
}
