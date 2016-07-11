package storm.magicspace.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.PATCH;
import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;
import storm.magicspace.activity.album.AlbumInfoActivity;
import storm.magicspace.adapter.CachedAdapter;
import storm.magicspace.bean.Album;
import storm.magicspace.bean.httpBean.MyCollectionResponse;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.view.handmark.pulltorefresh.library.ILoadingLayout;
import storm.magicspace.view.handmark.pulltorefresh.library.PullToRefreshBase;
import storm.magicspace.view.handmark.pulltorefresh.library.PullToRefreshListView;

import static storm.commonlib.common.CommonConstants.FROM;

/**
 * Created by lixiaolu on 16/6/20.
 */
public class TopicCollectionFragment extends BaseFragment {
    private CachedAdapter adapter;
    private List<Album> list = new ArrayList<>();
    private int page = 1;
    private PullToRefreshListView pullToRefreshListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_collection, null);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pullto);
        adapter = new CachedAdapter(list, getActivity());
        pullToRefreshListView.setAdapter(adapter);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("album", list.get(position - 1));
                bundle.putSerializable(FROM, CommonConstants.TOPIC);
                goToNext(AlbumInfoActivity.class, bundle);
            }
        });
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                new GetMyCollectionTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetMoreMyCollectionTask().execute();
            }
        });
        init();
        new GetMyCollectionTask().execute();
    }

    private class GetMyCollectionTask extends BaseASyncTask<String, MyCollectionResponse> {
        @Override
        public MyCollectionResponse doRequest(String param) {
            return HTTPManager.getMyCollection("topic", page);
        }

        @Override
        protected void onPostExecute(MyCollectionResponse myCollectionResponse) {
            super.onPostExecute(myCollectionResponse);
            page++;
            pullToRefreshListView.onRefreshComplete();
            if (myCollectionResponse == null || myCollectionResponse.data == null) {
                Toast.makeText(getActivity(), "网络数据下载错误，请稍后再试!", Toast.LENGTH_SHORT).show();
                return;
            }
            list.clear();
            list.addAll(myCollectionResponse.data);
            adapter.notifyDataSetChanged();
        }
    }

    private class GetMoreMyCollectionTask extends BaseASyncTask<String, MyCollectionResponse> {
        @Override
        public MyCollectionResponse doRequest(String param) {
            return HTTPManager.getMyCollection("topic", page);
        }

        @Override
        protected void onPostExecute(MyCollectionResponse myCollectionResponse) {
            super.onPostExecute(myCollectionResponse);
            page++;
            pullToRefreshListView.onRefreshComplete();
            if (myCollectionResponse == null || myCollectionResponse.data == null) {
                Toast.makeText(getActivity(), "网络数据下载错误，请稍后再试!", Toast.LENGTH_SHORT).show();
                return;
            }
            list.addAll(myCollectionResponse.data);
            adapter.notifyDataSetChanged();
        }
    }

    private void init() {
        ILoadingLayout startLabels = pullToRefreshListView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = pullToRefreshListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在载入...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
    }
}
