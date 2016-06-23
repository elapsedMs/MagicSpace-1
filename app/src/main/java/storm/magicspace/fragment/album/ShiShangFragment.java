package storm.magicspace.fragment.album;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;

/**
 * Created by gdq on 16/6/22.
 */
public class ShiShangFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_gao_qing, null);
        return view;
    }
}
