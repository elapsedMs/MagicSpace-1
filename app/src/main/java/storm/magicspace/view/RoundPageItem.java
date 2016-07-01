package storm.magicspace.view;

import android.content.Context;
import android.util.AttributeSet;

import storm.commonlib.common.base.BaseView;
import storm.magicspace.R;

/**
 * Created by lixiaolu on 16/7/1.
 */
public class RoundPageItem extends BaseView {

    public RoundPageItem(Context context, int viewId) {
        super(context, R.layout.round_item);
    }

    public RoundPageItem(Context context, AttributeSet attrs, int viewId) {
        super(context, attrs, R.layout.round_item);
    }

    public RoundPageItem(Context context, AttributeSet attrs, int defStyle, int viewId) {
        super(context, attrs, defStyle, R.layout.round_item);
    }
}
