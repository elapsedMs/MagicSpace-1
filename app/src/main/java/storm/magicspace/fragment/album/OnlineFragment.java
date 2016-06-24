package storm.magicspace.fragment.album;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;
import storm.magicspace.activity.album.ClassifyRecommendActivity;
import storm.magicspace.adapter.HomeViewPagerAdapter;
import storm.magicspace.adapter.OnlineRVAdapter;
import storm.magicspace.adapter.ViewPagerAdatper;
import storm.magicspace.bean.Album;
import storm.magicspace.http.reponse.AlbumResponse;
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
    private List<Album> albumList = new ArrayList<>();
    private OnlineRVAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_online, null);
    }

    @Override
    public void initView(View view) {
        new TestTask().execute();
        super.initView(view);
        noNetWorkLl = (LinearLayout) view.findViewById(id.no_net_work_ll);
        contentLl = (LinearLayout) view.findViewById(id.ll_content);
        viewPager = (ViewPager) view.findViewById(id.viewpager);
        recyclerView = (RecyclerView) view.findViewById(id.recycler_view);
        guessULikeATV = (AlbumTitleView) view.findViewById(id.you_like);
        guessULikeATV.setCount("更多");
        guessULikeATV.setOnClickListener(this);
        initViewPager();
        initRecyclerView();
        showContent();
    }

    private void initRecyclerView() {
        adapter = new OnlineRVAdapter(albumList, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new GridItemDecoration(getActivity()));
    }

    private void initViewPager() {
        List<ImageView> imageViews = new ArrayList<>();
        ImageView imageView = new ImageView(getActivity());
        ImageView imageView2 = new ImageView(getActivity());
        ImageView imageView3 = new ImageView(getActivity());
        ImageView imageView4 = new ImageView(getActivity());
        ImageView imageView5 = new ImageView(getActivity());
        imageView.setImageResource(R.mipmap.arrow_left);
        imageView2.setImageResource(R.mipmap.arrow_left);
        imageView3.setImageResource(R.mipmap.arrow_left);
        imageView4.setImageResource(R.mipmap.arrow_left);
        imageView5.setImageResource(R.mipmap.arrow_left);
        imageViews.add(imageView);
        imageViews.add(imageView2);
        imageViews.add(imageView3);
        imageViews.add(imageView4);
        imageViews.add(imageView5);
        viewPager.setAdapter(new ViewPagerAdatper(imageViews));
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

    private class TestTask extends BaseASyncTask<Void, AlbumResponse> {
        @Override
        public AlbumResponse doRequest(Void param) {
            return super.doRequest(param);
        }

        @Override
        public void onSuccess(AlbumResponse albumResponse) {
            super.onSuccess(albumResponse);
            albumList.clear();
            albumList.addAll(albumResponse.data);
            adapter.notifyDataSetChanged();
        }
    }

}
