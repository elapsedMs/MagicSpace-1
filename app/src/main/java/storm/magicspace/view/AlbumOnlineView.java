package storm.magicspace.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import storm.commonlib.common.base.BaseView;
import storm.magicspace.R;

/**
 * Created by gdq on 16/6/19.
 */
public class AlbumOnlineView extends BaseView {
    public AlbumOnlineView(Context context) {
        this(context, null);
    }

    public AlbumOnlineView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AlbumOnlineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle, R.layout.view_album_online);
    }
}
