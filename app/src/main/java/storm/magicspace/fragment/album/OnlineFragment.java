package storm.magicspace.fragment.album;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;
import storm.magicspace.activity.album.CacheingActivity;
import storm.magicspace.activity.album.GuessYouLikeActivity;
import storm.magicspace.activity.album.WebActivity;
import storm.magicspace.adapter.OnlineRVAdapter;
import storm.magicspace.adapter.ViewPagerAdatper;
import storm.magicspace.bean.Album;
import storm.magicspace.bean.CirclePic;
import storm.magicspace.bean.httpBean.CirclePicResponse;
import storm.magicspace.download.FileInfo;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.http.reponse.AlbumResponse;
import storm.magicspace.util.LocalSPUtil;
import storm.magicspace.view.AlbumTitleView;
import storm.magicspace.view.GridItemDecoration;

import static storm.magicspace.R.id;

/**
 * Created by gdq on 16/6/16.
 */
public class OnlineFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private LinearLayout noNetWorkLl;
    private LinearLayout contentLl;
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private AlbumTitleView guessULikeATV;
    private List<Album> albumList = new ArrayList<>();
    private OnlineRVAdapter adapter;
    private TextView title;
    private TextView desc;
    private List<CirclePic> circlePicList = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_online, null);
    }

    @Override
    public void initView(View view) {
        new TestTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new CirclePicTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        super.initView(view);
        noNetWorkLl = (LinearLayout) view.findViewById(id.no_net_work_ll);
        contentLl = (LinearLayout) view.findViewById(id.ll_content);
        viewPager = (ViewPager) view.findViewById(id.viewpager);
        viewPager.setOnPageChangeListener(this);
        recyclerView = (RecyclerView) view.findViewById(id.recycler_view);
        title = (TextView) view.findViewById(id.title);
        desc = (TextView) view.findViewById(id.desc);
        guessULikeATV = (AlbumTitleView) view.findViewById(id.you_like);
        guessULikeATV.setCount("更多");
        guessULikeATV.setOnClickListener(this);
        initRecyclerView();
        showContent();
    }

    private void initRecyclerView() {
        adapter = new OnlineRVAdapter(albumList, getActivity(), true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new GridItemDecoration(getActivity()));
        adapter.setOnRecyclerViewClickListener(new OnlineRVAdapter.OnRecyclerViewClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url", "http://app.stemmind.com/vr/a/preview.php?c=" + albumList.get(position).getContentId());
                goToNext(WebActivity.class, bundle);
            }

            @Override
            public void onBtnClick(int position) {
                FileInfo fileInfo = new FileInfo(albumList.get(position).getContentId(), "http://www.imooc.com/mobile/imooc.apk", 0, albumList.get(position).getNickName() + ".apk", 0, false, false);
                Bundle bundle = new Bundle();
                bundle.putSerializable("file_info", fileInfo);
                bundle.putSerializable("album", albumList.get(position));
                goToNext(CacheingActivity.class, bundle);
            }
        });
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId) {
            case id.you_like:
                goToNext(GuessYouLikeActivity.class);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        title.setText(circlePicList.get(position).getTitle());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class TestTask extends BaseASyncTask<Void, AlbumResponse> {
        @Override
        public AlbumResponse doRequest(Void param) {
            return HTTPManager.test("");

        }

        @Override
        public void onSuccess(AlbumResponse albumResponse) {
            super.onSuccess(albumResponse);
            LocalSPUtil.saveAlbum(albumResponse.data);
            albumList.clear();
            albumList.addAll(albumResponse.data);
            adapter.update(albumResponse.data);
        }
    }

    private class CirclePicTask extends BaseASyncTask<Void, CirclePicResponse> {
        @Override
        public CirclePicResponse doRequest(Void param) {
            return HTTPManager.getAlbumCirclePic();
        }

        @Override
        public void onSuccess(CirclePicResponse albumResponse) {
            super.onSuccess(albumResponse);
            circlePicList = albumResponse.data;
            imageViews.clear();
            title.setText(circlePicList.get(0).getTitle());
            desc.setText("没字段");
            for (int i = 0; i < circlePicList.size(); i++) {
                ImageView imageView = new ImageView(getActivity());
                Picasso.with(getActivity()).load(circlePicList.get(i).getUrl()).into(imageView);
                imageViews.add(imageView);
            }
            viewPager.setAdapter(new ViewPagerAdatper(imageViews));

        }
    }

}
