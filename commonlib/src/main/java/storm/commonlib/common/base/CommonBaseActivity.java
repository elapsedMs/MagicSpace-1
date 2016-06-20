package storm.commonlib.common.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import storm.commonlib.R;
import storm.commonlib.common.CommonConstants;
import storm.commonlib.common.view.TitleBar;
import storm.commonlib.common.view.dialog.MedtreeDialog;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static storm.commonlib.common.util.StringUtil.EMPTY;
import static storm.commonlib.common.view.dialog.MedtreeDialog.DisplayStyle.LOADING;

public class CommonBaseActivity extends FragmentActivity {

    private final int contentViewId;
    private int style = CommonConstants.ACTIVITY_STYLE_WITH_TITLE_BAR;
    private TitleBar titleBar;
    private MedtreeDialog medtreeDialog;

    public CommonBaseActivity(int contentViewId, int style) {
        this.style = style;
        this.contentViewId = contentViewId;
    }

    public CommonBaseActivity(int contentViewId) {
        this.contentViewId = contentViewId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentViewId);
        initTitleBar();
        switchActivityType();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dismissBaseDialog();
    }

    public void rightTitleSelected() {
        titleBar.rightTitleSelected();
    }

    public void leftTitleSelected() {
        titleBar.leftTitleSelected();
    }

    public void useSelectTitle(String left, String right) {
        titleBar.useSelectTitle();
        titleBar.setSelectTitle(left, right);
    }

    public void useNormalTitle() {
        titleBar.useNormalTitle();
    }

    public void setOnSelectTitleClickedListener(TitleBar.OnSelectTitleClickedListener listener) {
        titleBar.setOnSelectTitleClickedListener(listener);
    }

    public void setSelectTitle(String left, String right) {
        titleBar.setSelectTitle(left, right);
    }

    private void initTitleBar() {
        titleBar = (TitleBar) findViewById(R.id.navigation_bar);
        titleBar.setVisibility(View.GONE);

        titleBar.setOnLeftBtClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTitleBarBackButtonClicked();
            }
        });

        titleBar.setOnRightBtClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTitleBarRightButtonClicked(view);
            }
        });

        titleBar.setOnRightTvClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTitleBarRightTvClicked(view);
            }
        });

        titleBar.setRightSecondButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTitleBarRightSecondBtClicked(view);
            }
        });
        titleBar.setOnLeftTvClickedListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onTitleBarLeftTextClicked(view);
            }
        });
    }

    public void onTitleBarRightSecondBtClicked(View view) {
    }

    public void setStyle(int style) {
        this.style = style;
        switchActivityType();
    }

    private void switchActivityType() {
        switch (style) {
            case CommonConstants.ACTIVITY_STYLE_EMPTY:
                setTitleBarVisibility(GONE);
                break;

            case CommonConstants.ACTIVITY_STYLE_WITH_LOADING:

                showBaseDialog(LOADING, getString(R.string.data_loading), EMPTY, true, true);
                break;

            case CommonConstants.ACTIVITY_STYLE_WITH_TITLE_BAR:
                titleBar.setVisibility(VISIBLE);
                break;

            case CommonConstants.ACTIVITY_STYLE_WITH_TITLE_BAR_AND_LOADING:
                showBaseDialog(LOADING, getString(R.string.data_loading), EMPTY, true, true);
                titleBar.setVisibility(VISIBLE);
                break;

            case CommonConstants.ACTIVITY_STYLE_CONSULTATION:
                titleBar.setVisibility(VISIBLE);
                titleBar.setRightButtonVisibility(VISIBLE);
                titleBar.setRightSecondButtonVisibility(VISIBLE);
                titleBar.setRightDrawable(R.drawable.btn_service_order);
                break;
        }
    }

    public void initView() {
    }

    public void bindCacheData() {
    }

    public void loadCacheData() {
    }

    public void initListener() {
    }

    public void initData() {
    }

    public void bindData() {
    }

    public void onTitleBarBackButtonClicked() {
        this.finish();
    }

    public void onTitleBarLeftTextClicked(View view) {
    }

    public void onTitleBarSearchButtonClicked(View view) {
    }

    public void onTitleBarRightButtonClicked(View view) {
    }

    public void onTitleBarRightTvClicked(View view) {
    }

    public void setRightTvClickable(boolean clickable) {
        titleBar.setRightTvClickable(clickable);
    }

    public void setActivityTitle(int resId) {
        titleBar.setTitle(resId);
    }

    public void setLeftText(int resId) {
        titleBar.setLeftTvText(resId);
    }


    public void setLeftText(String text) {
        titleBar.setLeftTvText(text);
    }

    public void setRightText(int resId) {
        titleBar.setRightTvText(resId);
    }

    public void setActivityTitle(String title) {
        titleBar.setTitle(title);
    }

    public void setTitleBarRightDrawable(int resId) {
        titleBar.setRightDrawable(resId);
    }

    public void setTitleBarVisibility(int visibility) {
        titleBar.setVisibility(visibility);
    }

    public void setTitleRightBtVisibility(int visibility) {
        titleBar.setRightButtonVisibility(visibility);
    }

    public void setTitleLeftBtVisibility(int visibility) {
        titleBar.setLeftButtonVisibility(visibility);
    }

    public void goToNext(Class<? extends Activity> clz) {
        Intent intent = new Intent(getApplication(), clz);
        startActivity(intent);
    }

    public void goToNext(Class<? extends Activity> clz, Bundle bundle) {
        Intent intent = new Intent(getApplication(), clz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public <E extends Activity> void goToNextForResult(Class<E> clz, int requestCode) {
        Intent intent = new Intent(getApplicationContext(), clz);
        startActivityForResult(intent, requestCode);

    }

    public <E extends Activity> void goToNextForResult(Class<E> clz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getApplicationContext(), clz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);

    }

    public void showBaseDialog(MedtreeDialog.DisplayStyle style, String title, String message, boolean shouldBottomHide, boolean hasAnimation) {
        medtreeDialog = medtreeDialog == null ? new MedtreeDialog(this) : medtreeDialog;
        medtreeDialog.displayWithStyle(style);
        medtreeDialog.setMessage(message);
        medtreeDialog.setTitle(title);

        if (shouldBottomHide)
            medtreeDialog.hideBottom();

        if (hasAnimation)
            medtreeDialog.startAnimation();

        medtreeDialog.show();
    }

    public void dismissBaseDialog() {
        if (medtreeDialog != null)
            medtreeDialog.dismiss();
    }

    public void setTitleBarVisible(int visible) {
        titleBar.setVisibility(visible);
    }

    public void setTitleBarRightTvVisibility(int visibility) {
        titleBar.setRightTvVisibility(visibility);
    }
}