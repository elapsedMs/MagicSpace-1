package storm.commonlib.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import storm.commonlib.R;

import static storm.commonlib.common.BaseApplication.getApplication;


public class TitleBar extends LinearLayout {

    private Context context;
    private ImageView leftBt;
    private ImageView rightBt;
    private TextView activityTitle;
    private TextView leftTv;
    private TextView rightTv;
    private ImageView rightSecondBt;

    public TitleBar(Context context) {
        super(context);
        this.context = context;
        initUI();
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initUI();
    }

    private void initUI() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_navigation_bar, this);

        leftBt = (ImageView) findViewById(R.id.bt_title_bar_left);
        rightBt = (ImageView) findViewById(R.id.iv_title_bar_right);
        leftTv = (TextView) findViewById(R.id.tv_title_bar_left);
        rightTv = (TextView) findViewById(R.id.tv_title_bar_right);
        activityTitle = (TextView) findViewById(R.id.activity_title);
        rightSecondBt = (ImageView) findViewById(R.id.iv_title_bar_right_second);
    }

    public void setOnLeftBtClickedListener(OnClickListener listener) {
        leftBt.setOnClickListener(listener);
    }

    public void setOnRightBtClickedListener(OnClickListener listener) {
        rightBt.setOnClickListener(listener);
    }

    public void setOnRightTvClickedListener(OnClickListener listener) {
        rightTv.setOnClickListener(listener);
    }

    public void setOnLeftTvClickedListener(OnClickListener listener) {
        leftTv.setOnClickListener(listener);
    }

    public void setRightSecondButtonClickListener(OnClickListener listener) {
        rightSecondBt.setOnClickListener(listener);
    }

    public void setTitle(CharSequence text) {
        activityTitle.setText(text);
    }

    public void setTitle(int resId) {
        activityTitle.setText(resId);
    }

    public void setRightDrawable(int rightDrawable) {
        rightTv.setVisibility(View.GONE);
        rightBt.setVisibility(View.VISIBLE);
        rightBt.setImageResource(rightDrawable);
    }

    public void setRightTvText(int textResId) {
        rightBt.setVisibility(GONE);
        rightTv.setVisibility(View.VISIBLE);
        rightTv.setText(getApplication().getString(textResId));
    }

    public void setRightTvVisibility(int visibility) {
        rightTv.setVisibility(visibility);
    }

    public void setLeftTvText(int textResId) {
        leftBt.setVisibility(GONE);
        leftTv.setText(getApplication().getString(textResId));
    }

    public void setLeftTvText(String text) {
        leftBt.setVisibility(GONE);
        leftTv.setVisibility(VISIBLE);
        leftTv.setText(text);
    }

    public void setRightTvClickable(boolean clickable) {
        rightTv.setClickable(clickable);
    }

    public void setRightButtonVisibility(int visibility) {
        rightBt.setVisibility(visibility);
    }

    public void setLeftButtonVisibility(int visibility) {
        leftBt.setVisibility(visibility);
    }

    public void setRightSecondButtonVisibility(int visibility) {
        rightSecondBt.setVisibility(visibility);
    }
}