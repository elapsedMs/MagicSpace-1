package storm.magicspace.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;
import storm.magicspace.adapter.EggAdapter;

/**
 * Created by gdq on 16/6/15.
 */
public class EggFragment extends BaseFragment {
    private ListView lv_egg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_egg, null);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        lv_egg = this.findItemEventView(view, R.id.lv_egg);
        EggAdapter adapter = new EggAdapter(getActivity());
        lv_egg.setAdapter(adapter);
    }

    @Override
    public void onLocalItemClicked(AdapterView<?> parent, View view, int position, long id) {
        super.onLocalItemClicked(parent, view, position, id);
    }
}
