package storm.magicspace.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.adapter.CacheingRvAdapter;

/**
 * Created by gdq on 16/6/16.
 */
public class CacheingActivity extends BaseActivity {
    private LinearLayout noDownloadLl;
    private RelativeLayout contentRl;
    private RecyclerView recyclerView;

    public CacheingActivity() {
        super(R.layout.activity_cacheing);
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
        recyclerView.setAdapter(new CacheingRvAdapter(new ArrayList<>(), this));
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
}
