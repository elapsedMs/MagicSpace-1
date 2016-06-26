package storm.magicspace.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import storm.magicspace.bean.EggInfo;
import storm.magicspace.view.EggItem;


public class EggAdapter extends BaseAdapter {
    public Context context;
    private List<EggInfo> eggInfoList = new ArrayList<>();

    public EggAdapter(Context context, List<EggInfo> eggInfoList) {
        super();
        this.context = context;
        this.eggInfoList = eggInfoList;
    }

    @Override
    public int getCount() {
        return eggInfoList == null ? 0 : eggInfoList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        EggItem item = arg1 == null ? new EggItem(context) : (EggItem) arg1;
        item.setClickable(false);
        if (eggInfoList.get(arg0) != null) {
            item.bindData(eggInfoList.get(arg0));
        }
        return item;
    }

}
