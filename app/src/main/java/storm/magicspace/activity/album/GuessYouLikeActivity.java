package storm.magicspace.activity.album;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gdq.multhreaddownload.download.bean.FileInfo;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.adapter.OnlineRVAdapter;
import storm.magicspace.bean.Album;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.http.reponse.AlbumResponse;
import storm.magicspace.view.GridItemDecoration;

/**
 * Created by gdq on 16/6/16.
 */
public class GuessYouLikeActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private OnlineRVAdapter adapter;
    private List<Album> albumList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    public GuessYouLikeActivity() {
        super(R.layout.activity_guess_you_like);
    }

    @Override
    public void initView() {
        setActivityTitle("猜你喜欢");
        setTitleLeftBtVisibility(View.VISIBLE);
        super.initView();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshlayout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        initRecyclerView();
        initRefreshView();
        new TestTask().execute();

    }

    private void initRecyclerView() {
        adapter = new OnlineRVAdapter(albumList, this, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new GridItemDecoration(this));
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
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(-1)) {
                    swipeRefreshLayout.setEnabled(true);
                }
            }
        });
    }

    private void initRefreshView() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new TestTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setEnabled(false);
    }


    private class TestTask extends BaseASyncTask<Void, AlbumResponse> {
        @Override
        public AlbumResponse doRequest(Void param) {
            return HTTPManager.test("newest");

        }

        @Override
        public void onSuccess(AlbumResponse albumResponse) {
            super.onSuccess(albumResponse);
            swipeRefreshLayout.setRefreshing(false);
            albumList.clear();
            albumList.addAll(albumResponse.data);
            adapter.notifyDataSetChanged();
        }
    }
}
