package storm.magicspace.activity.album;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import storm.magicspace.download.DownloadService;
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
    private CacheingRvAdapter adapter;
    private boolean a;
    private int position;
    FileInfo fileInfo;

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
        Log.d("gdq", "更新UI:" + lengthEvent.len);
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
        initRecyclerView(layoutManager);

        fileInfo = (FileInfo) getIntent().getSerializableExtra("file_info");
        if (fileInfo != null) {
            fileInfoList.add(fileInfo);
            adapter.notifyDataSetChanged();
        }
        // 注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        registerReceiver(mReceiver, filter);


    }

    private void initRecyclerView(LinearLayoutManager layoutManager) {
        adapter = new CacheingRvAdapter(fileInfoList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnRecyclerClickListener(new CacheingRvAdapter.OnClickListener() {
            @Override
            public void click(int position) {
                Log.d("gdq", "onClick d= " + a);
                CacheingActivity.this.position = position;
                Intent intent = new Intent(CacheingActivity.this, DownloadService.class);
                if (a) {//暂时
                    a = false;
                    intent.setAction(DownloadService.ACTION_STOP);
                    intent.putExtra("file_info", new FileInfo(0, fileInfo.url, 0, fileInfo.fileName, 0));
                } else {//开始
                    a = true;
                    intent.setAction(DownloadService.ACTION_START);
                    intent.putExtra("file_info", new FileInfo(0, fileInfo.url, 0, fileInfo.fileName, 0));
                }
                startService(intent);
            }

            @Override
            public void longClick(int position) {

            }
        });
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
        unregisterReceiver(mReceiver);
    }

    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("hkh", "onReceive");
            if (DownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                int finised = intent.getIntExtra("finished", 0);
                fileInfoList.get(0).finished = finised;
                adapter.notifyItemChanged(0);
            }
        }
    };


}
