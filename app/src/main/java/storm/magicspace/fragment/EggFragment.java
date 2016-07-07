package storm.magicspace.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;
import storm.magicspace.activity.EggGameInfoActivity;
import storm.magicspace.activity.EggGamePreviewActivity;
import storm.magicspace.adapter.EggAdapter;
import storm.magicspace.bean.EggInfo;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.http.reponse.EggHttpResponse;

/**
 * Created by gdq on 16/6/15.
 */
public class EggFragment extends BaseFragment {
    private ListView lv_egg;
    private LinearLayout no_net_work_ll_egg;
    private List<EggInfo> egginfoList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_egg, null);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshlayout);
        initRefreshView();  lv_egg = this.findItemEventView(view, R.id.lv_egg);
        lv_egg.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0)
                    swipeRefreshLayout.setEnabled(true);
            }
        });

        no_net_work_ll_egg = findView(view,R.id.no_net_work_ll_egg);

    }

    private void initRefreshView() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new getEggTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setEnabled(false);
    }


    @Override
    public void initData() {
        super.initData();
        new getEggTask().execute();
    }

    @Override
    public void onLocalItemClicked(AdapterView<?> parent, View view, int position, long id) {
        super.onLocalItemClicked(parent, view, position, id);
//        goToNext(EggGameInfoActivity.class);
        Intent intent = new Intent(getActivity(),EggGameInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("game_info", egginfoList.get(position));
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    private class getEggTask extends BaseASyncTask<Void, EggHttpResponse> {
        @Override
        public EggHttpResponse doRequest(Void param) {
            return HTTPManager.getEggList();
        }

        @Override
        public void onSuccess(EggHttpResponse response) {
            super.onSuccess(response);
            swipeRefreshLayout.setRefreshing(false);
            showContent();
            egginfoList.clear();
            egginfoList.addAll(response.data);
            EggAdapter adapter = new EggAdapter(getActivity(), egginfoList);
            lv_egg.setAdapter(adapter);
        }

        @Override
        public void onFailed() {
            super.onFailed();
            showNoNet();
        }
    }

    private void showNoNet() {
        no_net_work_ll_egg.setVisibility(View.VISIBLE);
        lv_egg.setVisibility(View.GONE);
    }
    private void showContent() {
        no_net_work_ll_egg.setVisibility(View.GONE);
        lv_egg.setVisibility(View.VISIBLE);
    }

}
