package storm.magicspace.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import storm.magicspace.R;

/**
 * Created by xt on 16/6/26.
 */
public class EggsAdapter extends RecyclerView.Adapter<EggsAdapter.ViewHolder> {

    private List<EggItem> mEggs;

    public EggsAdapter(List<EggItem> eggs) {
        this.mEggs = eggs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_eggs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.egg.setImageResource(R.mipmap.surprise_egg_red);
    }

    @Override
    public int getItemCount() {
        return mEggs != null ? mEggs.size() : 10;
    }

    static class EggItem {

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView egg;

        public ViewHolder(View itemView) {
            super(itemView);
            egg = (ImageView) itemView.findViewById(R.id.iv_egg);
        }
    }

}
