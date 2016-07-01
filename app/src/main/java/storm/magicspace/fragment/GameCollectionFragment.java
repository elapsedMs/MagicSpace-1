package storm.magicspace.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;
import storm.magicspace.adapter.CachedAdapter;
import storm.magicspace.adapter.WorksAdapter;
import storm.magicspace.bean.Album;
import storm.magicspace.bean.httpBean.MyCollectionResponse;
import storm.magicspace.http.HTTPManager;

/**
 * Created by lixiaolu on 16/6/20.
 */
public class GameCollectionFragment extends BaseFragment {
    private ListView listView;
    private WorksAdapter adapter;
    private List<Album> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_collection, null);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        listView = (ListView) view.findViewById(R.id.listview);
        adapter = new WorksAdapter(list, getActivity());
        listView.setAdapter(adapter);
        new GetMyCollectionTask().execute();

    }

    private class GetMyCollectionTask extends BaseASyncTask<String, MyCollectionResponse> {
        @Override
        public MyCollectionResponse doRequest(String param) {
            return HTTPManager.getMyCollection("");
        }

        @Override
        protected void onPostExecute(MyCollectionResponse myCollectionResponse) {
            super.onPostExecute(myCollectionResponse);
            list.clear();
            list.addAll(myCollectionResponse.data);
            adapter.notifyDataSetChanged();
        }
    }
}
