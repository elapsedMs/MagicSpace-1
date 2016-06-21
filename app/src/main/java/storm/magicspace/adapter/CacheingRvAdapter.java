package storm.magicspace.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import storm.magicspace.R;

/**
 * Created by gdq on 16/6/21.
 */
public class CacheingRvAdapter extends RecyclerView.Adapter<CacheingRvAdapter.ViewHolder> {
    private List list;
    private Context context;

    public CacheingRvAdapter(List list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cacheing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //TODO赛数据
        holder.progressBar.setProgress(50);
    }

    @Override
    public int getItemCount() {
//        return list == null ? 0 : list.size();
    return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }
}
