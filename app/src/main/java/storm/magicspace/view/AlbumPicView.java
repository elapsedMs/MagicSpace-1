package storm.magicspace.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import storm.commonlib.common.base.BaseView;
import storm.magicspace.R;

/**
 * Created by gdq on 16/6/19.
 */
public class AlbumPicView extends BaseView {
    private final int PIC = 1;
    private final int TEXT = 2;

    private ImageView bgIv;
    private ImageView downloadIv;
    private TextView downloadTv;
    private TextView downSizeTv;
    private TextView downTimeTv;

    public AlbumPicView(Context context) {
        this(context, null);
    }

    public AlbumPicView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AlbumPicView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle, R.layout.view_pic);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AlbumPicView);
        int type = typedArray.getInt(R.styleable.AlbumPicView_album_pic_type, -1);
        initView();
        switchType(type);
    }

    private void switchType(int type) {
        switch (type) {
            case PIC:
                downloadIv.setVisibility(View.VISIBLE);
                downloadTv.setVisibility(View.GONE);
                break;
            case TEXT:
                downloadIv.setVisibility(View.GONE);
                downloadTv.setVisibility(View.VISIBLE);
                break;
        }
    }

    public AlbumPicView setBgRes(int res) {
        bgIv.setImageResource(res);
        return this;
    }

    public AlbumPicView setBgBitmap(Bitmap bitmap) {
        bgIv.setImageBitmap(bitmap);
        return this;
    }

    public AlbumPicView setDownloadTimes(String times) {
        downTimeTv.setText(times);
        return this;
    }

    public AlbumPicView setSize(String size) {
        downSizeTv.setText(size);
        return this;
    }

    private void initView() {
        bgIv = (ImageView) findViewById(R.id.imageView);
        downloadIv = (ImageView) findViewById(R.id.iv_download);
        downloadTv = (TextView) findViewById(R.id.tv_download);
        downSizeTv = (TextView) findViewById(R.id.tv_size);
        downTimeTv = (TextView) findViewById(R.id.tv_time);
    }
}
