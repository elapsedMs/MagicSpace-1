package storm.magicspace.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;
import storm.magicspace.view.AlbumPicView;

import static storm.magicspace.R.*;
import static storm.magicspace.R.layout.*;

/**
 * Created by gdq on 16/6/16.
 */
public class OnlineFragment extends BaseFragment {

    private LinearLayout noNetWorkLl;
    private AlbumPicView albumPicView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("gdq", "OnlineFragment onCreateView");
        return inflater.inflate(R.layout.fragment_online, null);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        noNetWorkLl = (LinearLayout) view.findViewById(id.no_net_work_ll);
    }


    public void showNoNetWork() {
        noNetWorkLl.setVisibility(View.VISIBLE);
    }

    public void showContent() {
        noNetWorkLl.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        Log.d("gdq", "OnlineFragment onDestroyView");
        super.onDestroyView();
    }
}
