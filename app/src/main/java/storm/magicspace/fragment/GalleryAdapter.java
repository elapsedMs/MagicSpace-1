package storm.magicspace.fragment;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import storm.commonlib.common.view.RoundedImageView;
import storm.magicspace.http.Conpon;
import storm.magicspace.view.MineShowView;

/**
 * Created by lixiaolu on 16/7/4.
 */
public class GalleryAdapter implements SpinnerAdapter {

    private final Context context;
    private final List<Conpon> circlePics;
    private int selectPosition;

    public GalleryAdapter(Context context, List<Conpon> circlePicList) {
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
        MineShowView item = convertView == null ? new MineShowView(context) : (MineShowView) convertView;
        RoundedImageView imageView = item.getImageView();
        imageView.setLocalStyle(selectPosition - position);
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


    public void setSelectedPosition(int position) {
        this.selectPosition = position;
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    public static Drawable bitmapToDrawble(Bitmap bitmap, Context mcontext) {
        Drawable drawable = new BitmapDrawable(mcontext.getResources(), bitmap);
        return drawable;
    }


    private static Bitmap changeBitmap(Bitmap bitmap, float sy) {
        Matrix matrix = new Matrix();
        matrix.postScale(sy, sy); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }
}
