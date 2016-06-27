package storm.magicspace.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.util.List;

import storm.magicspace.R;
import storm.magicspace.bean.httpBean.EggImage;

/**
 * Created by xt on 16/6/26.
 */
public class EggsAdapter extends RecyclerView.Adapter<EggsAdapter.ViewHolder> {

    private EggImage mEggs;
    private Context mContext;
    private List<EggImage.ObjectsBean> mImages;

    public EggsAdapter(Context context, EggImage eggs) {
        mContext = context;
        mEggs = eggs;
        mImages = mEggs.getObjects();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_eggs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.egg.setImageResource(R.mipmap.surprise_egg_red);
        EggImage.ObjectsBean eggImage = mImages.get(position);
        final String url = eggImage.getUrl();
        RequestCreator load = Picasso.with(mContext).load(url);
        load.into(holder.egg);
        load.into(new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.egg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onClick(position, url, bitmap);
                        }
                    }
                });
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return mImages != null ? mImages.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView egg;

        public ViewHolder(View itemView) {
            super(itemView);
            egg = (ImageView) itemView.findViewById(R.id.iv_egg);
        }
    }

    private ClickInterface mListener;

    public void setOnClickListener(ClickInterface listener) {
        mListener = listener;
    }

    public interface ClickInterface {
        void onClick(int position, String url, Bitmap bitmap);
    }
}
