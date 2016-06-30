package storm.magicspace.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import storm.magicspace.R;
import storm.magicspace.download.FileInfo;
import storm.magicspace.view.AlbumPicView;

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
            convertView = View.inflate(context, R.layout.view_cached, null);
            holder = new ViewHolder();
            holder.albumPicView = (AlbumPicView) convertView.findViewById(R.id.left);
            holder.nameTv = (TextView) convertView.findViewById(R.id.name);
            holder.descTv = (TextView) convertView.findViewById(R.id.desc);
            holder.countTv = (TextView) convertView.findViewById(R.id.download_count);
            holder.btnTv = (TextView) convertView.findViewById(R.id.btn);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private class ViewHolder {
        AlbumPicView albumPicView;
        TextView nameTv;
        TextView descTv;
        TextView countTv;
        TextView btnTv;
    }
}
