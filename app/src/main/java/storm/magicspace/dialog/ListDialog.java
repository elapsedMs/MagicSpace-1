package storm.magicspace.dialog;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import storm.magicspace.R;

/**
 * Created by gdq on 16/7/4.
 */
public class ListDialog extends Dialog implements View.OnClickListener {

    private ListView listView;
    private TextView cancelBtn;
    private List<String> list = new ArrayList<>();
    private ListAdapter adapter;

    public ListDialog(Context context, int theme) {
        super(context, theme);
        this.getWindow().setGravity(Gravity.BOTTOM);
        this.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.my_dialog_list, null);
        listView = (ListView) view.findViewById(R.id.lv_list_dialog);
        adapter = new ListAdapter(context, list);
        listView.setAdapter(adapter);
        cancelBtn = (TextView) view.findViewById(R.id.cancel_1);
        cancelBtn.setOnClickListener(this);
        setContentView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_1:
                dismiss();
                break;
        }
    }

    public void setItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        listView.setOnItemClickListener(onItemClickListener);
    }

    public void setData(List<String> list) {
        this.list.clear();
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public static class Buider {
        private ListDialog listDialog;
        private Context context;

        public Buider(Context context) {
            this.context = context;
            listDialog = new ListDialog(context, R.style.ListDialogStyle);
        }

        public Buider setItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
            listDialog.setItemClickListener(onItemClickListener);
            return this;
        }

        public Buider setData(List<String> list) {
            listDialog.setData(list);
            return this;
        }

        public ListDialog create() {
            return listDialog;
        }
    }

    private class ListAdapter extends BaseAdapter {
        private Context context;
        private List<String> list;

        public ListAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(context, R.layout.item_list_dialog, null);
                holder.itemName = (TextView) convertView.findViewById(R.id.tv_list_dialog_item_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.itemName.setText(list.get(position));
            return convertView;
        }

        private class ViewHolder {
            TextView itemName;
        }
    }

    @Override
    public void show() {
        super.show();
        ObjectAnimator.ofFloat(this, "translateY", 0, 100).setDuration(1000).start();
    }
}
