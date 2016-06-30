package storm.magicspace.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import storm.magicspace.R;
import storm.magicspace.download.FileInfo;

/**
 * Created by gdq on 16/6/29.
 */
public class CachedAdapter extends BaseAdapter {
    List<FileInfo> list;
    Context context;

    public CachedAdapter(List<FileInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.view_cached,null);
            holder = new ViewHolder();

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private class ViewHolder {

    }
}
