package storm.magicspace.activity.album;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

    public GuessYouLikeActivity() {
        super(R.layout.activity_guess_you_like);
    }

    @Override
    public void initView() {
        setActivityTitle("猜你喜欢");
        setTitleLeftBtVisibility(View.VISIBLE);
        super.initView();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        initRecyclerView();
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
                Bundle bundle = new Bundle();
                bundle.putString("url", albumList.get(position).getUrl());
                goToNext(WebActivity.class, bundle);
            }

            @Override
            public void onBtnClick(int position) {
                goToNext(CacheingActivity.class);
            }
        });
    }

    private class TestTask extends BaseASyncTask<Void, AlbumResponse> {
        @Override
        public AlbumResponse doRequest(Void param) {
            return HTTPManager.test("newest");

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
