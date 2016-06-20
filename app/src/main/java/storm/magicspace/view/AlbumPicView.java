package storm.magicspace.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import storm.commonlib.common.base.BaseView;
import storm.magicspace.R;

/**
 * Created by gdq on 16/6/19.
 */
public class AlbumPicView extends BaseView {
    public AlbumPicView(Context context) {
        this(context, null);
    }

    public AlbumPicView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AlbumPicView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle, R.layout.view_pic);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
