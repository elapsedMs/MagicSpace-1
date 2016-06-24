package storm.magicspace.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

    public OnlineRVAdapter(List list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public OnlineRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_album_online, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Album item = list.get(position);
        Picasso.with(context).load(item.getThumbImageUrl()).into(holder.albumPicView.getBgIv());
        holder.father.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewClickListener.onItemClick(position,item);
            }
        });

        holder.downloadIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewClickListener.onBtnClick(position,item);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
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
            albumPicView = (AlbumPicView) itemView.findViewById(R.id.picview);
            nameTv = (TextView) itemView.findViewById(R.id.name);
            descTv = (TextView) itemView.findViewById(R.id.desc);
            downloadIv = (ImageView) itemView.findViewById(R.id.iv_download);
            father = (LinearLayout) itemView.findViewById(R.id.father);
        }
    }

    public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener) {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    public interface OnRecyclerViewClickListener {
        void onItemClick(int position, Album item);

        void onBtnClick(int position, Album item);
    }
}

