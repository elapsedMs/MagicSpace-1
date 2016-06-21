package storm.magicspace.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;
import storm.magicspace.activity.CacheingActivity;
import storm.magicspace.view.AlbumTitleView;

/**
 * Created by gdq on 16/6/16.
 */
public class NativeFragment extends BaseFragment implements View.OnClickListener {

    private AlbumTitleView albumTitleView;
    private ListView cachedListView;
    private LinearLayout noDownloadLl;
    private RelativeLayout contentRl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_native, null);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        albumTitleView = (AlbumTitleView) view.findViewById(R.id.cacheingATV);
        albumTitleView.setCount("20");
        albumTitleView.setOnClickListener(this);
        albumTitleView.showDot();
        cachedListView = (ListView) view.findViewById(R.id.cachedLv);
        noDownloadLl = (LinearLayout) view.findViewById(R.id.no_download_ll);
        contentRl = (RelativeLayout) view.findViewById(R.id.rl_content);
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId) {
            case R.id.cacheingATV:
                goToNext(CacheingActivity.class);
                break;
        }
    }

    public void showNoDownload(){
        noDownloadLl.setVisibility(View.VISIBLE);
        contentRl.setVisibility(View.GONE);
    }

    public void showContent(){
        noDownloadLl.setVisibility(View.GONE);
        contentRl.setVisibility(View.VISIBLE);
    }
}
