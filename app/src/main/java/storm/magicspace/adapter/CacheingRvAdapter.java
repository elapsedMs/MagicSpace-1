package storm.magicspace.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import storm.magicspace.R;
import storm.magicspace.download.FileInfo;

/**
 * Created by gdq on 16/6/21.
 */
public class CacheingRvAdapter extends RecyclerView.Adapter<CacheingRvAdapter.ViewHolder> {
    private List<FileInfo> list;
    private Context context;
    private OnClickListener onClickListener;

    public CacheingRvAdapter(List<FileInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cacheing, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //TODO赛数据
        FileInfo fileInfo = list.get(position);
        holder.progressBar.setProgress(fileInfo.finished);
        holder.name.setText(fileInfo.fileName);
    }

    @Override
    public int getItemCount() {
//        return list == null ? 0 : list.size();
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView state;
        private TextView name;
        private ProgressBar progressBar;
        private TextView downloadSize;
        private TextView total;
        private TextView downloadSpeed;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            state = (TextView) itemView.findViewById(R.id.state);
            name = (TextView) itemView.findViewById(R.id.name);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            downloadSize = (TextView) itemView.findViewById(R.id.download_size);
            total = (TextView) itemView.findViewById(R.id.total);
            downloadSpeed = (TextView) itemView.findViewById(R.id.download_speed);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.click(getPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onClickListener.longClick(getPosition());
                    return false;
                }
            });
        }
    }

    public void setOnRecyclerClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void click(int position);

        void longClick(int position);
    }
}
