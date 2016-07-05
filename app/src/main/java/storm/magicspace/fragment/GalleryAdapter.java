package storm.magicspace.fragment;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import storm.commonlib.common.view.RoundedImageView;
import storm.magicspace.bean.CirclePic;
import storm.magicspace.view.MineShowView;

/**
 * Created by lixiaolu on 16/7/4.
 */
public class GalleryAdapter implements SpinnerAdapter {

    private final Context context;
    private final List<CirclePic> circlePics;

    public GalleryAdapter(Context context, List<CirclePic> circlePicList) {
        this.context = context;
        circlePics = circlePicList;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return circlePics == null ? 0 : circlePics.size();
    }

    @Override
    public Object getItem(int position) {
        return circlePics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MineShowView item = convertView == null ? new MineShowView(context) : (MineShowView)convertView;
        RoundedImageView imageView = item.getImageView();
        Picasso.with(context).load(circlePics.get(position).getUrl()).into(imageView);
        return item;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
