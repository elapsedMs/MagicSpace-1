package storm.commonlib.common.view;

import android.content.Context;
import android.util.AttributeSet;

import storm.commonlib.R;
import storm.commonlib.common.base.BaseView;


public class GrayHorizontalView extends BaseView {
    public GrayHorizontalView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle, R.layout.view_gray_horizontal);
    }

    public GrayHorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.view_gray_horizontal);
    }

    public GrayHorizontalView(Context context) {
        super(context, R.layout.view_gray_horizontal);
    }

    @Override
    public void bindData(Object object) {
        super.bindData(object);
    }

    @Override
    public void initView(Context context) {
        super.initView(context);

    }
}
