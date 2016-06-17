package storm.magicspace.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import storm.magicspace.view.EggItem;


public class EggAdapter extends BaseAdapter {
	public Context context;

	public EggAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int getCount() {
		return 5;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		EggItem item = arg1 == null ? new EggItem(context) : (EggItem) arg1;
		item.setClickable(false);
		return item;
	}

}
