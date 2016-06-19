package storm.magicspace.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;

import static storm.magicspace.R.*;
import static storm.magicspace.R.layout.*;

/**
 * Created by gdq on 16/6/16.
 */
public class OnlineFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("gdq", "OnlineFragment onCreateView");
        return inflater.inflate(R.layout.fragment_online, null);
    }

    @Override
    public void onStart() {
        Log.d("gdq", "OnlineFragment onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("gdq", "OnlineFragment onResume");
        super.onResume();
    }


    @Override
    public void onPause() {
        Log.d("gdq", "OnlineFragment onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("gdq", "OnlineFragment onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("gdq", "OnlineFragment onDestroyView");
        super.onDestroyView();
    }
}
