package storm.commonlib.common.base;

import android.view.View;
import android.widget.AdapterView;

public interface BaseOnClickListener {
    void onLocalClicked(int resId);

    void onLocalItemClicked(AdapterView<?> parent, View view, int position, long id);
}