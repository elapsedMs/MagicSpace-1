package storm.magicspace.fragment.album;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdq.multhreaddownload.download.bean.FileInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;
import storm.magicspace.activity.AdvertActivity;
import storm.magicspace.activity.album.CacheingActivity;
import storm.magicspace.activity.album.GuessYouLikeActivity;
import storm.magicspace.adapter.OnlineRVAdapter;
import storm.magicspace.adapter.ViewPagerAdatper;
import storm.magicspace.bean.Album;
import storm.magicspace.bean.CirclePic;
import storm.magicspace.bean.httpBean.CirclePicResponse;
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
    private View mHeaderView;
    private TextView title;
    private TextView desc;
    private List<CirclePic> circlePicList = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();
    private LinearLayout guideDotLl;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_online, null);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        initRecyclerView(view);
        viewPager = (ViewPager) mHeaderView.findViewById(id.viewpager);
        viewPager.setOnPageChangeListener(this);
        guideDotLl = (LinearLayout) mHeaderView.findViewById(R.id.ll_guide_dot);
        noNetWorkLl = (LinearLayout) view.findViewById(id.no_net_work_ll);
        contentLl = (LinearLayout) mHeaderView.findViewById(id.ll_content);

        title = (TextView) mHeaderView.findViewById(id.title);
        desc = (TextView) mHeaderView.findViewById(id.desc);
        guessULikeATV = (AlbumTitleView) mHeaderView.findViewById(id.you_like);
        guessULikeATV.setCount("更多");
        guessULikeATV.setOnClickListener(this);
        showContent();
        new TestTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new CirclePicTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(id.recycler_view);
        recyclerView.addItemDecoration(new GridItemDecoration(getActivity()));
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManagernew = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManagernew);
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.header_album, recyclerView, false);

        adapter = new OnlineRVAdapter(albumList, getActivity(), true, mHeaderView);
        recyclerView.setAdapter(adapter);

        layoutManagernew.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.isHeader(position) ? layoutManagernew.getSpanCount() : 1;
            }
        });

        adapter.setOnRecyclerViewClickListener(new OnlineRVAdapter.OnRecyclerViewClickListener() {
            @Override
            public void onItemClick(int position) {
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
        recyclerView.setVisibility(View.GONE);
    }

    public void showContent() {
        noNetWorkLl.setVisibility(View.GONE);
        contentLl.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
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
        int count = guideDotLl.getChildCount();
        for (int i = 0; i < count; i++) {
            ImageView view = (ImageView) guideDotLl.getChildAt(i);
            if (position == i) {
                view.setImageDrawable(getResources().getDrawable(R.drawable.shape_guide_dot));
            } else {
                view.setImageDrawable(getResources().getDrawable(R.drawable.shape_guide_dot_normal));
            }
        }
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
            desc.setText("");
            for (int i = 0; i < circlePicList.size(); i++) {
                final ImageView imageView = new ImageView(getActivity());
                final String hyberlink = circlePicList.get(i).getHyberlink();
                imageView.setTag(hyberlink);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AdvertActivity.class);
//                        intent.putExtra("URL", content_url);
//                        intent.setAction("android.intent.action.VIEW");
//                        intent.setData(content_url);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("URL", hyberlink);
                        goToNext(AdvertActivity.class, bundle);
                    }
                });
                Picasso.with(getActivity()).load(circlePicList.get(i).getUrl()).into(imageView);
                imageViews.add(imageView);

                ImageView dot = new ImageView(getActivity());
                if (i == 0) {
                    dot.setImageDrawable(getResources().getDrawable(R.drawable.shape_guide_dot));
                } else {
                    dot.setPadding(8, 0, 0, 0);
                    dot.setImageDrawable(getResources().getDrawable(R.drawable.shape_guide_dot_normal));
                }
                guideDotLl.addView(dot);
            }
            viewPager.setAdapter(new ViewPagerAdatper(imageViews));

        }
    }


}
