package storm.commonlib.common.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Objects;

import storm.commonlib.common.util.ClickUtil;
import storm.commonlib.common.util.ViewUtil;

public class BaseView extends LinearLayout implements View.OnClickListener, BaseOnClickListener {

    private int viewId;
    private BaseOnClickListener listener;

    public BaseView(Context context, int viewId) {
        super(context);
        this.viewId = viewId;
        initView(context);
//        initListener();
    }

    public BaseView(Context context, AttributeSet attrs, int viewId) {
        super(context, attrs);
        this.viewId = viewId;
        initView(context);
//        initListener();
    }

    public BaseView(Context context, AttributeSet attrs, int defStyle, int viewId) {
        super(context, attrs, defStyle);
        this.viewId = viewId;
        initView(context);
//        initListener();
    }

    public void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(viewId, this, true);
    }

    public void initListener() {
        this.setBaseOnClickListener(this);
//        this.setOnClickListener(this);
    }

    private void setBaseOnClickListener(BaseOnClickListener BaseOnClickListener) {
        listener = BaseOnClickListener;
    }

    public void bindData(Object object) {
    }

    public void bindData(Object object, int position) {
    }

    public <T> void bindData(ArrayList<T> data) {
    }

    public void bindData(Objects... objects) {
    }

    @Override
    public void onClick(View v) {
        if (!ClickUtil.isFastClick())
            listener.onLocalClicked(v.getId());
    }

    @Override
    public void onLocalClicked(int view) {
    }

    @Override
    public void onLocalItemClicked(AdapterView<?> parent, View view, int position, long id) {
    }

    protected <T extends View> T findEventView(int id) {
        T view = ViewUtil.findView(this, id);
        view.setOnClickListener(this);
        return view;
    }

    protected <T extends View> T findView(int id) {
        return ViewUtil.findView(this, id);
    }

    protected <T extends View> T findEventView(View view, int id) {
        T currentView = ViewUtil.findView(view, id);
        currentView.setOnClickListener(this);
        return currentView;
    }
}
