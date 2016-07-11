package storm.magicspace.fragment.album;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;
import storm.magicspace.activity.MainActivity;
import storm.magicspace.activity.album.AlbumInfoActivity;
import storm.magicspace.activity.album.CacheingActivity;
import storm.magicspace.adapter.CachedAdapter;
import storm.magicspace.bean.Album;
import storm.magicspace.bean.httpBean.MyCollectionResponse;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.view.handmark.pulltorefresh.library.PullToRefreshBase;
import storm.magicspace.view.handmark.pulltorefresh.library.PullToRefreshListView;

import static storm.commonlib.common.CommonConstants.FROM;

/**
 * Created by gdq on 16/6/16.
 */
public class NativeFragment extends BaseFragment implements View.OnClickListener {
    private ListView listView;
    private CachedAdapter adapter;
    private List<Album> list = new ArrayList<>();
    private PullToRefreshListView pullToRefreshListView;

    private LinearLayout noDownloadLl;
    private RelativeLayout contentRl;
    private MainActivity mainActivity;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.fragment_native, null);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_to_refreshview);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new CachedAdapter(list, getActivity());
        pullToRefreshListView.setAdapter(adapter);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                new GetMyCollectionTask().execute();
                Log.d("gdq", "onPullDownToRefresh");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.d("gdq", "onPullUpToRefresh");
                new GetMoreMyCollectionTask().execute();
            }
        });
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("album", list.get(position-1));
                bundle.putSerializable(FROM, CommonConstants.TOPIC);
                goToNext(AlbumInfoActivity.class, bundle);
            }
        });
        new GetMyCollectionTask().execute();
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId) {
            case R.id.cacheingATV:
                goToNext(CacheingActivity.class);
                break;
        }
    }

    public void showRedDot(String count) {
        mainActivity.showRedDot(count);
    }

    public void showNoDownload() {
        noDownloadLl.setVisibility(View.VISIBLE);
        contentRl.setVisibility(View.GONE);
    }

    public void showContent() {
        noDownloadLl.setVisibility(View.GONE);
        contentRl.setVisibility(View.VISIBLE);
    }

    private class GetMyCollectionTask extends BaseASyncTask<String, MyCollectionResponse> {
        @Override
        public MyCollectionResponse doRequest(String param) {
            return HTTPManager.getMyCollection("game", page);
        }

        @Override
        protected void onPostExecute(MyCollectionResponse myCollectionResponse) {
            super.onPostExecute(myCollectionResponse);
            page++;
            pullToRefreshListView.onRefreshComplete();
            if (myCollectionResponse == null || myCollectionResponse.data == null) {
//                Toast.makeText(getActivity(), "网络数据下载错误，请稍后再试!", Toast.LENGTH_SHORT).show();
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
            return HTTPManager.getMyCollection("game", page);
        }

        @Override
        protected void onPostExecute(MyCollectionResponse myCollectionResponse) {
            super.onPostExecute(myCollectionResponse);
            page++;
            pullToRefreshListView.onRefreshComplete();
            if (myCollectionResponse == null || myCollectionResponse.data == null) {
//                Toast.makeText(getActivity(), "网络数据下载错误，请稍后再试!", Toast.LENGTH_SHORT).show();
                return;
            }
            list.addAll(myCollectionResponse.data);
            adapter.notifyDataSetChanged();
        }
    }
}
