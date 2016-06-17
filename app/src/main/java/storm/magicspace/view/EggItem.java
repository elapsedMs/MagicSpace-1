package storm.magicspace.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import storm.commonlib.common.base.BaseView;
import storm.magicspace.R;


public class EggItem extends BaseView {

	public EggItem(Context context, AttributeSet attrs,
				   int defStyle) {
		super(context, attrs, defStyle, R.layout.view_egg_item);
	}

	public EggItem(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.view_egg_item);
	}

	public EggItem(Context context) {
		super(context, R.layout.view_egg_item);
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
