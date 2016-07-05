package storm.magicspace.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.activity.EggGameInfoActivity;
import storm.magicspace.activity.GameEditDetailActivity;
import storm.magicspace.activity.album.AlbumInfoActivity;
import storm.magicspace.adapter.WorksAdapter;
import storm.magicspace.bean.Album;
import storm.magicspace.bean.httpBean.MyWorksResponse;
import storm.magicspace.http.HTTPManager;

public class MyWorksActivity extends BaseActivity {
    private ListView listView;
    private WorksAdapter adapter;
    private List<Album> list = new ArrayList<>();
    private LinearLayout nodata;
    private RelativeLayout btView;
    private TextView refresh;

    public MyWorksActivity() {
        super(R.layout.activity_my_works);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityTitle("我的作品");
        setTitleLeftBtVisibility(View.VISIBLE);
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
                goToNext(AlbumInfoActivity.class,bundle);
            }
        });
        task.execute();
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
            return HTTPManager.getMyWorks("", "");
        }

        @Override
        public void onSuccess(MyWorksResponse myWorksResponse) {
            super.onSuccess(myWorksResponse);
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
