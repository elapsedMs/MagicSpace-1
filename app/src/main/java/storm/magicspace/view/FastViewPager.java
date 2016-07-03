package storm.magicspace.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by xt on 16/7/2.
 */
public class FastViewPager extends ViewPager {
    public FastViewPager(Context context) {
        super(context);
    }

    public FastViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
