package storm.magicspace.view;

import android.content.Context;
import android.util.AttributeSet;

import storm.commonlib.common.base.BaseView;
import storm.magicspace.R;

/**
 * Created by gdq on 16/6/19.
 */
public class AlbumCachedView extends BaseView {
    public AlbumCachedView(Context context) {
        this(context, null);
    }

    public AlbumCachedView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AlbumCachedView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle, R.layout.view_cached);
    }
}
