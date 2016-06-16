package storm.magicspace.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;

/**
 * Created by gdq on 16/6/16.
 */
public class OnlineFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_online, container);
        return view;
    }
}
