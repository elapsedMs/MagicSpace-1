package storm.magicspace.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.gdq.multhreaddownload.download.bean.FileInfo;

import java.util.List;

import storm.magicspace.R;

public class FileListAdapter extends BaseAdapter {
    private Context mContext;
    private List<FileInfo> mList;

    public FileListAdapter(Context context, List<FileInfo> fileInfos) {
        this.mContext = context;
        this.mList = fileInfos;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final FileInfo fileInfo = mList.get(position);


        if (null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_cacheing, null);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.state = (TextView) convertView.findViewById(R.id.state);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.progress_bar);
            viewHolder.downloadSize = (TextView) convertView.findViewById(R.id.download_size);
            viewHolder.total = (TextView) convertView.findViewById(R.id.total);
            viewHolder.downloadSpeed = (TextView) convertView.findViewById(R.id.download_speed);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(fileInfo.fileName);
        viewHolder.progressBar.setProgress(fileInfo.finished);

        return convertView;
    }

    public synchronized void updateProgress(int id, int progress) {
        mList.get(id).finished = progress;
        notifyDataSetChanged();
    }

    public void finished(int id) {
        mList.get(id).finished = 100;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public ImageView image;
        public TextView state;
        public TextView name;
        public ProgressBar progressBar;
        public TextView downloadSize;
        public TextView total;
        public TextView downloadSpeed;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
