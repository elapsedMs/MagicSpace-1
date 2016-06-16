package storm.magicspace.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;

/**
 * Created by gdq on 16/6/15.
 */
public class AlbumFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, null);
        return view;
    }
}
