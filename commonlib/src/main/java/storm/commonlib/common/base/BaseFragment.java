package storm.commonlib.common.base;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.umeng.analytics.MobclickAgent;

import storm.commonlib.common.util.ClickUtil;
import storm.commonlib.common.util.ViewUtil;

import static storm.commonlib.common.BaseApplication.getApplication;


public class BaseFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, BaseOnClickListener {

    private BaseOnClickListener baseOnClickListener;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("gdq","onViewCreated");
        initView(view);
        loadCacheData();
        bindCacheData();
        initData();
        initListener();
        bindData();

        setBaseOnClickListener(this);
    }

    private void setBaseOnClickListener(BaseOnClickListener baseOnClickListener) {
        this.baseOnClickListener = baseOnClickListener;
    }

    public void initView(View view) {

    }

    public void loadCacheData() {

    }

    public void bindCacheData() {

    }

    public void initData() {

    }

    private void initListener() {

    }

    public void bindData() {

    }

    protected <T extends View> T findView(View view, int id) {
        return ViewUtil.findView(view, id);
    }

    protected <T extends View> T findEventView(View view, int id) {
        T currentView = ViewUtil.findView(view, id);
        currentView.setOnClickListener(this);
        return currentView;
    }

    protected <T extends ListView> T findItemEventView(View view, int id) {
        T currentView = ViewUtil.findView(view, id);
        currentView.setOnItemClickListener(this);
        return currentView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (ClickUtil.isFastClick()) return;
        baseOnClickListener.onLocalItemClicked(parent, view, position, id);
    }

    @Override
    public void onClick(View v) {
        if (ClickUtil.isFastClick()) return;
        baseOnClickListener.onLocalClicked(v.getId());
    }

    @Override
    public void onLocalClicked(int resId) {
    }

    @Override
    public void onLocalItemClicked(AdapterView<?> parent, View view, int position, long id) {
    }

    public void goToNext(Class<? extends Activity> clz) {
        Intent intent = new Intent(getApplication(), clz);
        startActivity(intent);
    }

    public void goToNext(Class<? extends Activity> clz, Bundle bundle) {
        Intent intent = new Intent(getApplication(), clz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public <E extends Activity> void goToNextForResult(Class<E> clz, int requestCode) {
        Intent intent = new Intent(getApplication(), clz);
        startActivityForResult(intent, requestCode);

    }

    public <E extends Activity> void goToNextForResult(Class<E> clz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getApplication(), clz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getApplication());
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getApplication());       //统计时长
    }
}