package storm.commonlib.common.view.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import storm.commonlib.R;


/**
 * Created by gdq on 16/1/13.
 */
public class ListDialog extends BaseDialog {
    private List<String> itemNames;

    public ListDialog(Context context, int theme, int layoutId) {
        super(context, theme, layoutId);
    }

    @Override
    public void init(Context context, int layoutId) {
        super.init(context, layoutId);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        findViewById(R.id.bt_list_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing())
                    dismiss();
            }
        });
    }

    public ListDialog setItems(OnClickListener listDialogClickListener, List<String> items) {
        this.confirmClickListener = listDialogClickListener;
        this.itemNames = items;
        initListView();
        return this;
    }

    private void initListView() {
        if (itemNames == null || itemNames.size() < 0) return;
        ListView listView = (ListView) findViewById(R.id.lv_list_dialog);
        listView.setAdapter(new ArrayAdapter<String>(mContext, R.layout.item_list_dialog, R.id.tv_list_dialog_item_name, itemNames));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (confirmClickListener != null)
                    confirmClickListener.onClick(ListDialog.this, position);
            }
        });
    }

    @Override
    public void show() {
        super.show();
        layout.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.list_dialog));
    }

    public static class Builder extends BaseDialog.Builder<ListDialog> {

        public Builder(Context context, int dialogStyle) {
            this.e = new ListDialog(context, dialogStyle, R.layout.dialog_list);
        }

        public Builder setItems(OnClickListener listDialogClickListener, String... items) {
            if (items != null)
                this.setItems(listDialogClickListener, Arrays.asList(items));
            return this;
        }

        public Builder setItems(OnClickListener listDialogClickListener, List<String> items) {
            e.setItems(listDialogClickListener, items);
            return this;
        }
    }
}
