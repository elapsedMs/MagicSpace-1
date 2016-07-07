package storm.magicspace.activity.mine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.activity.album.AlbumInfoActivity;
import storm.magicspace.adapter.WorksAdapter;
import storm.magicspace.bean.Album;
import storm.magicspace.bean.httpBean.MyWorksResponse;
import storm.magicspace.http.HTTPManager;

import static storm.commonlib.common.CommonConstants.FROM;

public class MyWorksActivity extends BaseActivity {
    private ListView listView;
    private WorksAdapter adapter;
    private List<Album> list = new ArrayList<>();
    private LinearLayout nodata;
    private RelativeLayout btView;
    private TextView refresh;
    private SwipeRefreshLayout swipeRefreshLayout;

    public MyWorksActivity() {
        super(R.layout.activity_my_works);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityTitle("我的作品");
        setTitleLeftBtVisibility(View.VISIBLE);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshlayout);
        initRefreshView();
        listView = (ListView) findViewById(R.id.listview);
        adapter = new WorksAdapter(list, this);
        nodata = findView(R.id.my_works_no_net_work_ll);
        btView = findView(R.id.rl_build_works);
        refresh = findEventView(R.id.refresh);
        listView.setAdapter(adapter);
        GetMyWorksTask task = new GetMyWorksTask();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("album", list.get(position));
                bundle.putSerializable(FROM, CommonConstants.GAME);
                goToNext(AlbumInfoActivity.class, bundle);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0)
                    swipeRefreshLayout.setEnabled(true);
            }
        });

        task.execute();
    }

    private void initRefreshView() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetMyWorksTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setEnabled(false);
    }


    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId) {
            case R.id.refresh:
                refresh.setClickable(false);
                new GetMyWorksTask().execute();
                break;
        }
    }

    private class GetMyWorksTask extends BaseASyncTask<String, MyWorksResponse> {
        @Override
        public MyWorksResponse doRequest(String param) {
            return HTTPManager.getMyWorks();
        }

        @Override
        public void onSuccess(MyWorksResponse myWorksResponse) {
            super.onSuccess(myWorksResponse);
            swipeRefreshLayout.setRefreshing(false);
            resetView(View.GONE);
            refresh.setClickable(true);
            list.clear();
            list.addAll(myWorksResponse.data);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onSuccessWithoutResult(MyWorksResponse myWorksResponse) {
            super.onSuccessWithoutResult(myWorksResponse);
            resetView(View.GONE);
            refresh.setClickable(true);
            list.clear();
            list.addAll(myWorksResponse.data);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailed() {
            super.onFailed();
            refresh.setClickable(true);
            resetView(View.VISIBLE);
        }
    }

    private void resetView(int visibility) {
        nodata.setVisibility(visibility);
//        btView.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
}
