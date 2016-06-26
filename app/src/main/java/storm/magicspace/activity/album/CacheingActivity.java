package storm.magicspace.activity.album;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.adapter.CacheingRvAdapter;
import storm.magicspace.download.FileInfo;
import storm.magicspace.event.LengthEvent;

/**
 * Created by gdq on 16/6/16.
 */
public class CacheingActivity extends BaseActivity {
    private LinearLayout noDownloadLl;
    private RelativeLayout contentRl;
    private RecyclerView recyclerView;
    private List<FileInfo> fileInfoList = new ArrayList<>();

    public CacheingActivity() {
        super(R.layout.activity_cacheing);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LengthEvent lengthEvent) {
        Log.d("gdq", "更新UI");
    }

    @Override
    public void initView() {
        super.initView();
        setActivityTitle("正在缓存");
        setTitleLeftBtVisibility(View.VISIBLE);
        noDownloadLl = (LinearLayout) findViewById(R.id.no_download_ll);
        contentRl = (RelativeLayout) findViewById(R.id.rl_content);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CacheingRvAdapter(fileInfoList, this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void showNoDownload() {
        noDownloadLl.setVisibility(View.VISIBLE);
        contentRl.setVisibility(View.GONE);
    }

    public void showContent() {
        noDownloadLl.setVisibility(View.GONE);
        contentRl.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
