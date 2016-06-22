package storm.magicspace.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import storm.magicspace.R;
import storm.magicspace.view.AlbumPicView;

/**
 * Created by gdq on 16/6/22.
 */
public class OnlineRVAdapter extends RecyclerView.Adapter<OnlineRVAdapter.ViewHolder> {

    private List list;
    private Context context;

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
    public void onBindViewHolder(ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 7;
//        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AlbumPicView albumPicView;
        private TextView nameTv;
        private TextView descTv;
        private ImageView downloadIv;

        public ViewHolder(View itemView) {
            super(itemView);
            albumPicView = (AlbumPicView) itemView.findViewById(R.id.picview);
            nameTv = (TextView) itemView.findViewById(R.id.name);
            descTv = (TextView) itemView.findViewById(R.id.desc);
            downloadIv = (ImageView) itemView.findViewById(R.id.iv_download);
        }
    }
}

