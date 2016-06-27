package storm.magicspace.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import storm.magicspace.R;
import storm.magicspace.bean.Album;
import storm.magicspace.view.AlbumPicView;

/**
 * Created by gdq on 16/6/22.
 */
public class OnlineRVAdapter extends RecyclerView.Adapter<OnlineRVAdapter.ViewHolder> {

    private List<Album> list;
    private Context context;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;
    private boolean isLimit = false;

    public OnlineRVAdapter(List list, Context context, boolean isLimit) {
        this.list = list;
        this.context = context;
        this.isLimit = isLimit;
    }

    @Override
    public OnlineRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("gdq","onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.view_album_online, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d("gdq","onBindViewHolder");
        final Album item = list.get(position);
        Picasso.with(context).load(item.getThumbImageUrl()).into(holder.albumPicView.getBgIv());
        holder.descTv.setText(list.get(position).getDescription());
        holder.nameTv.setText("没字段");
        holder.albumPicView.setSupportTimes(item.getAppreciateCount());
        holder.albumPicView.setCollectTimes(item.getCommentCount());
        holder.father.setOnClickListener(n ew View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("gdq","father onClick ");
                onRecyclerViewClickListener.onItemClick(position);
            }
        });

        holder.downloadIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("gdq","downloadIv onClick ");

                onRecyclerViewClickListener.onBtnClick(position);
            }
        });  }


    @Override
    public int getItemCount() {
        Log.d("gdq","getItemCount");
        return list == null ? 0 : isLimit ? list.size() <= 6 ? list.size() : 6 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AlbumPicView albumPicView;
        private TextView nameTv;
        private TextView descTv;
        private ImageView downloadIv;
        private ImageView imageView;
        private LinearLayout father;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.d("gdq", "ViewHolder");
            albumPicView = (AlbumPicView) itemView.findViewById(R.id.picview);
            nameTv = (TextView) itemView.findViewById(R.id.name);
            descTv = (TextView) itemView.findViewById(R.id.desc);
            downloadIv = (ImageView) itemView.findViewById(R.id.iv_down_load);
            father = (LinearLayout) itemView.findViewById(R.id.father);
        }
    }

    public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener) {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    public interface OnRecyclerViewClickListener {
        void onItemClick(int position);

        void onBtnClick(int position);
    }
}

