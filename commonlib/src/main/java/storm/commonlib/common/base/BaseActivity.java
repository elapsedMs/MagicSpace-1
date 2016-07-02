package storm.commonlib.common.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListView;

import com.umeng.analytics.MobclickAgent;

import storm.commonlib.R;
import storm.commonlib.common.util.ActivityCollector;
import storm.commonlib.common.util.ClickUtil;
import storm.commonlib.common.util.MessageUtil;
import storm.commonlib.common.util.ViewUtil;

public class BaseActivity extends CommonBaseActivity implements View.OnClickListener, BaseOnClickListener, AdapterView.OnItemClickListener {
    public final int contentViewId;
    private BaseOnClickListener listener;
    private double exitTime;

    public BaseActivity(int contentViewStubId, int style) {
        super(R.layout.activity_native, style);
        this.contentViewId = contentViewStubId;
    }

    public BaseActivity(int contentViewStubId) {
        super(R.layout.activity_native);
        this.contentViewId = contentViewStubId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().add(this);
        initContentViewStub();
        initView();
        loadCacheData();
        bindCacheData();
        initData();
        initListener();
        bindData();

        setBaseOnClickListener(this);
    }

    private void initContentViewStub() {
        ViewStub viewStub = (ViewStub) findViewById(R.id.content_view_stub);
        viewStub.setLayoutResource(contentViewId);
        viewStub.inflate();
    }

    @Override
    public void onClick(View v) {
        if (!ClickUtil.isFastClick())
            listener.onLocalClicked(v.getId());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!ClickUtil.isFastClick())
            listener.onLocalItemClicked(parent, view, position, id);
    }

    @Override
    public void onLocalClicked(int resId) {
    }

    @Override
    public void onLocalItemClicked(AdapterView<?> parent, View view, int position, long id) {
    }

    public void setBaseOnClickListener(BaseOnClickListener listener) {
        this.listener = listener;
    }

    protected <T extends View> T findView(int id) {
        return ViewUtil.findView(this, id);
    }

    protected <T extends View> T findEventView(int id) {
        T currentView = ViewUtil.findView(this, id);
        currentView.setOnClickListener(this);
        return currentView;
    }

    protected <T extends ListView> T findItemEventView(int id) {
        T currentView = ViewUtil.findView(this, id);
        currentView.setOnItemClickListener(this);
        return currentView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().remove(this);
    }

    public void tryExit() {
        if ((System.currentTimeMillis() - exitTime) <= 2000) {
            ActivityCollector.getInstance().exit();
        } else {
            MessageUtil.showMessage(R.string.login_out_tip);
            exitTime = System.currentTimeMillis();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}