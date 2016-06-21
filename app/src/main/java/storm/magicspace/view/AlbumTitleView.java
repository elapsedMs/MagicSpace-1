package storm.magicspace.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import storm.commonlib.common.base.BaseView;
import storm.magicspace.R;

/**
 * Created by gdq on 16/6/19.
 */
public class AlbumTitleView extends BaseView {

    private final int NORMAL = 1;
    private final int HAVE_COUNT = 2;

    private TextView nameTv;
    private TextView countTv;
    private ImageView redDotIv;
    private ImageView arrowIv;

    public AlbumTitleView(Context context) {
        this(context, null);
    }

    public AlbumTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AlbumTitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle, R.layout.view_album_title_view);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AlbumTitleView);
        int style = typedArray.getInt(R.styleable.AlbumTitleView_album_title_style, -1);
        String name = typedArray.getString(R.styleable.AlbumTitleView_album_title_name);
        initView();
        initData(style, name);
        typedArray.recycle();
    }

    private void initData(int style, String name) {
        nameTv.setText(name);
        switch (style) {
            case NORMAL:
                arrowIv.setVisibility(View.GONE);
                redDotIv.setVisibility(View.GONE);
                countTv.setVisibility(View.GONE);
                break;
            case HAVE_COUNT:
                arrowIv.setVisibility(View.VISIBLE);
                countTv.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initView() {
        nameTv = (TextView) findViewById(R.id.title_name);
        countTv = (TextView) findViewById(R.id.count);
        redDotIv = (ImageView) findViewById(R.id.red_dot);
        arrowIv = (ImageView) findViewById(R.id.arrow);
    }

    public void showDot() {
        redDotIv.setVisibility(View.VISIBLE);
    }

    public void hideDot() {
        redDotIv.setVisibility(View.GONE);
    }

    public void setCount(String count) {
        countTv.setText(count);
    }
}
