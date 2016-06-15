package storm.commonlib.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import storm.commonlib.R;
import storm.commonlib.common.base.BaseView;


public class TitleView extends BaseView {
    private TextView titleTv;

    public TitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle, R.layout.view_title);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.view_title);
    }

    public TitleView(Context context) {
        super(context, R.layout.view_title);
    }

    @Override
    public void bindData(Object object) {
        super.bindData(object);
        String string = (String) object;
        titleTv.setText(string);
    }

    @Override
    public void initView(Context context) {
        super.initView(context);
        titleTv = findView(R.id.tv_title);
    }
}
