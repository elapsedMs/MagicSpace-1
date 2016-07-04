package storm.magicspace.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gdq.multhreaddownload.download.DownloadService;
import com.gdq.multhreaddownload.download.bean.FileInfo;

import java.util.List;

import retrofit.http.POST;
import storm.magicspace.R;

/**
 * Created by gdq on 16/6/21.
 */
public class CacheingRvAdapter extends RecyclerView.Adapter<CacheingRvAdapter.ViewHolder> {
    private List<FileInfo> list;
    private Context context;
    private OnClickListener onClickListener;
    private boolean goStart;

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //TODO赛数据
        final FileInfo fileInfo = list.get(position);

        holder.progressBar.setProgress(fileInfo.finished);

        holder.name.setText(fileInfo.fileName);
        holder.itemView.setTag( true);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   Log.d("gdq", "onClick d= " + goStart);
                                                   boolean goStart = (boolean) holder.itemView.getTag();
                                                   Intent intent = new Intent(context, DownloadService.class);
                                                   if (!goStart) {//暂时
                                                       holder.itemView.setTag(true);
                                                       intent.setAction(DownloadService.ACTION_STOP);
                                                       intent.putExtra("file_info", list.get(position));
                                                   } else {//开始
                                                       holder.itemView.setTag(false);
                                                       intent.setAction(DownloadService.ACTION_START);
                                                       intent.putExtra("file_info", list.get(position));
                                                   }

                                                   context.startService(intent);
                                                   holder.itemView.setClickable(false);
                                               }
                                           }

        );
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()

                                               {
                                                   @Override
                                                   public boolean onLongClick(View v) {
                                                       onClickListener.longClick(position);
                                                       return false;
                                                   }
                                               }

        );
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView state;
        private TextView name;
        private ProgressBar progressBar;
        private TextView downloadSize;
        private TextView total;
        private TextView downloadSpeed;

        public ViewHolder(final View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            state = (TextView) itemView.findViewById(R.id.state);
            name = (TextView) itemView.findViewById(R.id.name);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            downloadSize = (TextView) itemView.findViewById(R.id.download_size);
            total = (TextView) itemView.findViewById(R.id.total);
            downloadSpeed = (TextView) itemView.findViewById(R.id.download_speed);

        }
    }

    public void setOnRecyclerClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void click(int position);

        void longClick(int position);

    }

    public synchronized void updateProgress(int id, int finished) {
        list.get(id).finished = finished;
        notifyDataSetChanged();
    }

}
