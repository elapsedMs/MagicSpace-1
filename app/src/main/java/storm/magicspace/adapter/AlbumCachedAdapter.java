package storm.magicspace.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.roboguice.shaded.goole.common.cache.Cache;

import java.util.List;

import storm.magicspace.R;

/**
 * Created by gdq on 16/6/19.
 */
public class AlbumCachedAdapter extends BaseAdapter {

    private List<Cache> cacheList;
    private Context context;

    public AlbumCachedAdapter(List<Cache> cacheList, Context context) {
        this.cacheList = cacheList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cacheList == null ? 0 : cacheList.size();
    }

    @Override
    public Object getItem(int i) {
        return cacheList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.item_cached, null);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        return null;
    }

    private class ViewHolder {

    }
}
