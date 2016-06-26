package storm.magicspace.activity;

import android.os.Bundle;
import android.view.View;

import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;

public class EggGameInfoActivity extends BaseActivity {

    public EggGameInfoActivity() {
        super(R.layout.activity_egg_game_info, CommonConstants.ACTIVITY_STYLE_WITH_TITLE_BAR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findEventView(R.id.bt_egg_game_info_preview);
        findEventView(R.id.bt_egg_game_info_download);
        setActivityTitle("遊戲詳情");
        setTitleRightBtVisibility(View.GONE);
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);
        switch (resId) {
            case R.id.bt_egg_game_info_preview:
                goToNext(EggGamePreviewActivity.class);
                break;

            case R.id.bt_egg_game_info_download:
                break;

            default:
                break;
        }
    }
}