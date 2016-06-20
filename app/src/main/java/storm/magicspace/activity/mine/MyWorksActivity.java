package storm.magicspace.activity.mine;

import android.os.Bundle;
import android.view.View;

import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;

public class MyWorksActivity extends BaseActivity {

    public MyWorksActivity() {
        super(R.layout.activity_my_works);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityTitle("我的作品");
        setTitleLeftBtVisibility(View.VISIBLE);
    }
}
