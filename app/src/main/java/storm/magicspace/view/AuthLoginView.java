package storm.magicspace.view;

import android.content.Context;
import android.util.AttributeSet;

import storm.commonlib.common.base.BaseView;
import storm.magicspace.R;

/**
 * Created by lixiaolu on 16/7/6.
 */
public class AuthLoginView extends BaseView {

    public AuthLoginView(Context context) {
        super(context, R.layout.auth_login);
    }

    public AuthLoginView(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.auth_login);
    }

    public AuthLoginView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle, R.layout.auth_login);
    }

    @Override
    public void initView(Context context) {
        super.initView(context);
    }
}
