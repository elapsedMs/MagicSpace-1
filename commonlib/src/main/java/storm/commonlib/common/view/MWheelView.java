package storm.commonlib.common.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import storm.commonlib.R;
import storm.commonlib.common.view.widget.WheelView;


public class MWheelView extends WheelView {
    public MWheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MWheelView(Context context) {
        super(context);
    }

    @Override
    protected Drawable getTopShadowDrawable() {
        return new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#ffffff"), Color.TRANSPARENT});
    }

    @Override
    protected Drawable getBottomShadowDrawable() {
        return new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{Color.parseColor("#ffffff"), Color.TRANSPARENT});
    }

    @Override
    protected int getWheelResource() {
        return R.drawable.wheel_val_med;
    }

    @Override
    protected int getBackgroundResource() {
        return R.drawable.wheel_bg_med;
    }
}