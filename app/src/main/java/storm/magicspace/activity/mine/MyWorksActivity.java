package storm.magicspace.activity.mine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
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
import storm.magicspace.view.handmark.pulltorefresh.library.PullToRefreshBase;
import storm.magicspace.view.handmark.pulltorefresh.library.PullToRefreshListView;

import static storm.commonlib.common.CommonConstants.FROM;

public class MyWorksActivity extends BaseActivity {
    private WorksAdapter adapter;
    private List<Album> list = new ArrayList<>();
    private FrameLayout nodata;
    private RelativeLayout btView;
    private TextView refresh;
    private PullToRefreshListView pullToRefreshListView;
    private int page = 1;

    public MyWorksActivity() {
        super(R.layout.activity_my_works);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityTitle("我的作品");
        setTitleLeftBtVisibility(View.VISIBLE);
        nodata = findView(R.id.my_works_no_net_work_ll);
        btView = findView(R.id.rl_build_works);
        refresh = findEventView(R.id.refresh);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pulltor);
        adapter = new WorksAdapter(list, this);
        pullToRefreshListView.setAdapter(adapter);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("album", list.get(position-1));
                bundle.putSerializable(FROM, CommonConstants.GAME);
                bundle.putSerializable(CommonConstants.COME_FROM, CommonConstants.MY_WORKS);
                goToNext(AlbumInfoActivity.class, bundle);
            }
        });
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                new GetMyWorksTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetMoreMyWorksTask().execute();

            }
        });

        new GetMyWorksTask().execute();
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId) {
            case R.id.refresh:
                refresh.setClickable(false);
                page = 1;
                new GetMyWorksTask().execute();
                break;
        }
    }

    private class GetMyWorksTask extends BaseASyncTask<String, MyWorksResponse> {
        @Override
        public MyWorksResponse doRequest(String param) {
            return HTTPManager.getMyWorks(page);
        }

        @Override
        public void onSuccess(MyWorksResponse myWorksResponse) {
            super.onSuccess(myWorksResponse);
            page++;
            pullToRefreshListView.onRefreshComplete();
            resetView(View.GONE);
            refresh.setClickable(true);
            list.clear();
            list.addAll(myWorksResponse.data);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onSuccessWithoutResult(MyWorksResponse myWorksResponse) {
            super.onSuccessWithoutResult(myWorksResponse);
            page++;
            pullToRefreshListView.onRefreshComplete();
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

    private class GetMoreMyWorksTask extends BaseASyncTask<String, MyWorksResponse> {
        @Override
        public MyWorksResponse doRequest(String param) {
            return HTTPManager.getMyWorks(page);
        }

        @Override
        public void onSuccess(MyWorksResponse myWorksResponse) {
            super.onSuccess(myWorksResponse);
            page++;
            pullToRefreshListView.onRefreshComplete();
            resetView(View.GONE);
            refresh.setClickable(true);
            list.addAll(myWorksResponse.data);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onSuccessWithoutResult(MyWorksResponse myWorksResponse) {
            super.onSuccessWithoutResult(myWorksResponse);
            page++;
            pullToRefreshListView.onRefreshComplete();
            resetView(View.GONE);
            refresh.setClickable(true);
            list.addAll(myWorksResponse.data);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailed() {
            super.onFailed();
            pullToRefreshListView.onRefreshComplete();
        }
    }

    private void resetView(int visibility) {
        nodata.setVisibility(visibility);
        pullToRefreshListView.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
}
