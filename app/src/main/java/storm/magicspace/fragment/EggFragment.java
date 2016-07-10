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
import storm.magicspace.view.handmark.pulltorefresh.library.PullToRefreshBase;
import storm.magicspace.view.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by gdq on 16/6/15.
 */
public class EggFragment extends BaseFragment {
    private LinearLayout no_net_work_ll_egg;
    private List<EggInfo> egginfoList = new ArrayList<>();
    private PullToRefreshListView pullToRefreshListView;
    private int page = 1;
    private EggAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_egg, null);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        no_net_work_ll_egg = findView(view, R.id.no_net_work_ll_egg);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pulltorefre);
        adapter = new EggAdapter(getActivity(), egginfoList);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        ListView listView = pullToRefreshListView.getRefreshableView();
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                new getEggTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new getMoreEggTask().execute();
            }
        });
        showContent();
        new getEggTask().execute();
    }

    @Override
    public void onLocalItemClicked(AdapterView<?> parent, View view, int position, long id) {
        super.onLocalItemClicked(parent, view, position, id);
        Intent intent = new Intent(getActivity(), EggGameInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("game_info", egginfoList.get(position-1));
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    private class getEggTask extends BaseASyncTask<Void, EggHttpResponse> {
        @Override
        public EggHttpResponse doRequest(Void param) {
            return HTTPManager.getEggList(page);
        }

        @Override
        public void onSuccess(EggHttpResponse response) {
            super.onSuccess(response);
            page++;
            pullToRefreshListView.onRefreshComplete();
            showContent();
            egginfoList.clear();
            egginfoList.addAll(response.data);
            adapter.notifyDataSetInvalidated();
        }

        @Override
        public void onSuccessWithoutResult(EggHttpResponse eggHttpResponse) {
            super.onSuccessWithoutResult(eggHttpResponse);
            pullToRefreshListView.onRefreshComplete();
        }

        @Override
        public void onFailed() {
            super.onFailed();
            pullToRefreshListView.onRefreshComplete();
            showNoNet();
        }
    }


    private class getMoreEggTask extends BaseASyncTask<Void, EggHttpResponse> {
        @Override
        public EggHttpResponse doRequest(Void param) {
            return HTTPManager.getEggList(page);
        }

        @Override
        public void onSuccess(EggHttpResponse response) {
            super.onSuccess(response);
            page++;
            pullToRefreshListView.onRefreshComplete();
            showContent();
            egginfoList.addAll(response.data);
            adapter.notifyDataSetInvalidated();
        }

        @Override
        public void onSuccessWithoutResult(EggHttpResponse eggHttpResponse) {
            super.onSuccessWithoutResult(eggHttpResponse);
            pullToRefreshListView.onRefreshComplete();
        }

        @Override
        public void onFailed() {
            super.onFailed();
            pullToRefreshListView.onRefreshComplete();
        }
    }

    private void showNoNet() {
        no_net_work_ll_egg.setVisibility(View.VISIBLE);
        pullToRefreshListView.setVisibility(View.GONE);
    }

    private void showContent() {
        no_net_work_ll_egg.setVisibility(View.GONE);
        pullToRefreshListView.setVisibility(View.VISIBLE);
    }

}
