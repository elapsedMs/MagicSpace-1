package storm.magicspace.activity.album;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.adapter.FileListAdapter;
import storm.magicspace.bean.Album;
import storm.magicspace.download.DownloadService;
import storm.magicspace.download.FileInfo;
import storm.magicspace.download.db.ThreadDAO;
import storm.magicspace.download.db.ThreadDaoImpl;
import storm.magicspace.event.LengthEvent;
import storm.magicspace.util.LocalSPUtil;

/**
 * Created by gdq on 16/6/16.
 */
public class CacheingActivity extends BaseActivity {
    private LinearLayout noDownloadLl;
    private RelativeLayout contentRl;
    private ListView listView;
    private List<FileInfo> fileInfoList = new ArrayList<>();
    private FileListAdapter adapter;
    private ThreadDAO threadDAO;

    public CacheingActivity() {
        super(R.layout.activity_cacheing);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        threadDAO = new ThreadDaoImpl(this);
        super.onCreate(savedInstanceState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LengthEvent lengthEvent) {
        Log.d("zzz", "更新UI:" + lengthEvent.len);
    }

    @Override
    public void initView() {
        super.initView();
        setActivityTitle("正在缓存");
        setTitleLeftBtVisibility(View.VISIBLE);
        noDownloadLl = (LinearLayout) findViewById(R.id.no_download_ll);
        contentRl = (RelativeLayout) findViewById(R.id.rl_content);
        listView = (ListView) findViewById(R.id.listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        initListView(layoutManager);
        initMyData();
        // 注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        filter.addAction(DownloadService.ACTION_FINISH);
        registerReceiver(mReceiver, filter);
    }

    private void initMyData() {
        if (getIntent().getSerializableExtra("file_info") != null) {
            FileInfo fileInfo = (FileInfo) getIntent().getSerializableExtra("file_info");
            fileInfoList.add(fileInfo);
//            Intent intent = new Intent(CacheingActivity.this, DownloadService.class);
//            intent.setAction(DownloadService.ACTION_START);
//            fileInfo.position = fileInfoList.size() - 1;
//            intent.putExtra("file_info", fileInfo);
//            startService(intent);
            if (getIntent().getSerializableExtra("album") != null) {
                LocalSPUtil.saveUnFinishAlbum(getApplicationContext(), (Album) getIntent().getSerializableExtra("album"));
            }
        }
        if (threadDAO.getAllUnFinishFile() != null) {
            if (threadDAO.getAllUnFinishFile().size() != 0)
                fileInfoList.addAll(threadDAO.getAllUnFinishFile());
        }
        adapter.notifyDataSetChanged();
    }

    private void initListView(LinearLayoutManager layoutManager) {
        adapter = new FileListAdapter(this, fileInfoList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isStart = fileInfoList.get(position).isStart;
                Intent intent = new Intent(CacheingActivity.this, DownloadService.class);
                if (isStart) {//暂时
                    Log.d("gdq", "暂停");
                    fileInfoList.get(position).isStart = false;
                    intent.setAction(DownloadService.ACTION_STOP);
                    intent.putExtra("file_info", fileInfoList.get(position));
                } else {//开始
                    Log.d("gdq", "开始");
                    fileInfoList.get(position).isStart = true;
                    threadDAO.insertUnFinishFileifNotExists(fileInfoList.get(position));
                    intent.setAction(DownloadService.ACTION_START);
                    FileInfo fileInfo = fileInfoList.get(position);
                    fileInfo.position = position;
                    intent.putExtra("file_info", fileInfo);
                }
                startService(intent);
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
                Log.d("hkh", "onReceive ACTION_UPDATE");
                int finised = intent.getIntExtra("finished", 0);
                int id = intent.getIntExtra("id", 0);
                adapter.updateProgress(id, finised);
            } else if (DownloadService.ACTION_FINISH.equals(intent.getAction())) {
                Log.d("hkh", "onReceive ACTION_FINISH");
                FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                Toast.makeText(CacheingActivity.this, "下载完成" + fileInfo.fileName, Toast.LENGTH_LONG).show();
                adapter.finished(fileInfo.position);
                LocalSPUtil.saveFinishAlbum(getApplicationContext(), LocalSPUtil.getUnFinishAlbum(getApplicationContext(), fileInfo.id));
                LocalSPUtil.deleteUnFinishAlbum(getApplicationContext(), fileInfo.id);
            }
        }
    };
}
