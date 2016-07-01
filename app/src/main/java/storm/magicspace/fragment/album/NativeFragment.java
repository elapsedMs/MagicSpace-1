package storm.magicspace.fragment.album;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;
import storm.magicspace.activity.album.CacheingActivity;
import storm.magicspace.activity.MainActivity;
import storm.magicspace.adapter.CachedAdapter;
import storm.magicspace.base.MagicApplication;
import storm.magicspace.bean.Album;
import storm.magicspace.download.FileInfo;
import storm.magicspace.download.db.ThreadDAO;
import storm.magicspace.download.db.ThreadDaoImpl;
import storm.magicspace.util.LocalSPUtil;
import storm.magicspace.view.AlbumTitleView;

/**
 * Created by gdq on 16/6/16.
 */
public class NativeFragment extends BaseFragment implements View.OnClickListener {

    private AlbumTitleView albumTitleView;
    private AlbumTitleView cachedATV;
    private ListView cachedListView;
    private LinearLayout noDownloadLl;
    private RelativeLayout contentRl;
    private MainActivity mainActivity;
    private CachedAdapter adapter;
    private ThreadDAO threadDAO;
    private List<Album> albumList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        threadDAO = new ThreadDaoImpl(getActivity());

        return inflater.inflate(R.layout.fragment_native, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("zzz", "1");
        if (threadDAO.getAllFinishFile() != null) {
            Log.d("zzz", "2");
            if (threadDAO.getAllFinishFile().size() > 0) {
                Log.d("zzz", "3");
                albumList.clear();
                albumList.addAll(LocalSPUtil.getFinishAlbum(MagicApplication.getApplication()));
                Log.d("zzz", "list vie size" + albumList.size());
                adapter.notifyDataSetChanged();
            } else {
                Log.d("zzz", "4");
                haveNotAnyContent();
            }
        } else {
            haveNotAnyContent();
        }

        if (threadDAO.getAllUnFinishFile() != null) {
            if (threadDAO.getAllUnFinishFile().size() > 0) {
                albumTitleView.setCount(threadDAO.getAllUnFinishFile().size() + "");
            } else
                albumTitleView.setCount(0 + "");
        } else
            albumTitleView.setCount(0 + "");
    }

    private void haveNotAnyContent() {
        noCached();
        if (threadDAO.getAllUnFinishFile() != null) {
            if (threadDAO.getAllUnFinishFile().size() > 0) {
                showNoDownload();
            }
        }
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        cachedATV = (AlbumTitleView) view.findViewById(R.id.cachedATV);
        albumTitleView = (AlbumTitleView) view.findViewById(R.id.cacheingATV);
        albumTitleView.setOnClickListener(this);
//        albumTitleView.showDot();
        cachedListView = (ListView) view.findViewById(R.id.cachedLv);
//        adapter = new CachedAdapter(albumList, getActivity());
        adapter = new CachedAdapter(albumList, getActivity());
        cachedListView.setAdapter(adapter);
        noDownloadLl = (LinearLayout) view.findViewById(R.id.no_download_ll);
        contentRl = (RelativeLayout) view.findViewById(R.id.rl_content);
    }

    @Override
    public void initData() {
        super.initData();
//        showRedDot("20");
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

    public void showRedDot(String count) {
        mainActivity.showRedDot(count);
    }

    public void noCached() {
        cachedATV.setVisibility(View.GONE);
        cachedListView.setVisibility(View.GONE);
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
