package storm.magicspace.fragment.album;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;
import storm.magicspace.activity.album.ClassifyRecommendActivity;
import storm.magicspace.adapter.OnlineRVAdapter;
import storm.magicspace.view.AlbumTitleView;
import storm.magicspace.view.GridItemDecoration;

import static storm.magicspace.R.*;

/**
 * Created by gdq on 16/6/16.
 */
public class OnlineFragment extends BaseFragment {

    private LinearLayout noNetWorkLl;
    private LinearLayout contentLl;
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private AlbumTitleView guessULikeATV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_online, null);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        noNetWorkLl = (LinearLayout) view.findViewById(id.no_net_work_ll);
        contentLl = (LinearLayout) view.findViewById(id.ll_content);
        viewPager = (ViewPager) view.findViewById(id.viewpager);
        recyclerView = (RecyclerView) view.findViewById(id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(new OnlineRVAdapter(new ArrayList(), getActivity()));
        recyclerView.addItemDecoration(new GridItemDecoration(getActivity()));
        guessULikeATV = (AlbumTitleView) view.findViewById(id.you_like);
        guessULikeATV.setCount("更多");
        guessULikeATV.setOnClickListener(this);
        showContent();
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId) {
            case id.you_like:
                goToNext(ClassifyRecommendActivity.class);
                break;
        }
    }

    public void showNoNetWork() {
        noNetWorkLl.setVisibility(View.VISIBLE);
        contentLl.setVisibility(View.GONE);
    }

    public void showContent() {
        noNetWorkLl.setVisibility(View.GONE);
        contentLl.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
