package storm.magicspace.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;
import storm.magicspace.activity.GameActivity;
import storm.magicspace.adapter.EggsAdapter;
import storm.magicspace.bean.httpBean.EggImage;

/**
 * Created by xt on 16/7/2.
 */
public class EggImageFragment extends BaseFragment {

    private static final String TAG = EggImageFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private int mPosition;
    private EggsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setResource(), container, false);
    }

    private int setResource() {
        return R.layout.fragment_egg_image;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_game_egg_image);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1,
                OrientationHelper.HORIZONTAL, false));
    }

    public static EggImageFragment getInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        EggImageFragment fragment = new EggImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if (arguments != null) {
            mPosition = arguments.getInt("position", 0);
        }

        GameActivity activity = (GameActivity) getActivity();
        List<EggImage> imageList = activity.getEggImageList();
        EggImage eggImage = imageList.get(mPosition);
        mAdapter = new EggsAdapter(getActivity(), eggImage);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickListener(mEggClickListener);

    }

    private EggsAdapter.ClickInterface mEggClickListener;

    public void setOnEggClickListener(EggsAdapter.ClickInterface listener) {
        this.mEggClickListener = listener;
    }

}
