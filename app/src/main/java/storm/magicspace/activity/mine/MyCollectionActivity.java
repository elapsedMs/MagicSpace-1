package storm.magicspace.activity.mine;

import android.os.Bundle;
import android.view.View;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;

public class MyCollectionActivity extends BaseActivity {

    public MyCollectionActivity() {
        super(R.layout.activity_my_collection);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityTitle("我的收藏");
        setTitleLeftBtVisibility(View.VISIBLE);
    }

}