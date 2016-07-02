package storm.magicspace.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.List;

import storm.magicspace.bean.Company;
import storm.magicspace.view.GalleryItem;

/**
 * Created by py on 15/12/15.
 */
public class GalleryAdapter extends BaseAdapter {

    private final List<Company> companies;
    private final Context context;
    private int selectedPosition;

    public GalleryAdapter(Context context, List<Company> companies) {
        this.companies = companies;
        this.context = context;
    }

    @Override
    public int getCount() {
        return companies == null ? 0 : companies.size();
    }

    @Override
    public Object getItem(int position) {
        return companies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
//        LogUtil.i("Gallery", "getView : position :" + position + "selectedPosition :" + selectedPosition);
        GalleryItem item = view == null ? new GalleryItem(context) : (GalleryItem) view;
        item.bindData(companies.get(position), position == selectedPosition);
        return item;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        this.notifyDataSetChanged();
    }
}
