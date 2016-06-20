package storm.commonlib.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import storm.commonlib.R;

import static storm.commonlib.R.id.right_line;
import static storm.commonlib.common.BaseApplication.getApplication;


public class TitleBar extends LinearLayout {

    private Context context;
    private ImageView leftBt;
    private ImageView rightBt;
    private TextView activityTitle;
    private TextView leftTv;
    private TextView rightTv;
    private ImageView rightSecondBt;
    private RelativeLayout titleBar1;
    private RelativeLayout titleBar2;
    private TextView selectLeftTv;
    private TextView selectRightTv;
    private LinearLayout leftLineLl;
    private LinearLayout rightLineLl;
    private RelativeLayout leftRl;
    private RelativeLayout rightRl;
    private OnSelectTitleClickedListener selectTitleClickedListener;

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

    public void useSelectTitle() {
        titleBar1.setVisibility(View.GONE);
        titleBar2.setVisibility(View.VISIBLE);
    }

    public void useNormalTitle() {
        titleBar1.setVisibility(View.VISIBLE);
        titleBar2.setVisibility(View.GONE);
    }

    public void withouttitle() {
        titleBar1.setVisibility(View.GONE);
        titleBar2.setVisibility(View.GONE);
    }

    public void setSelectTitle(String left, String right) {
        selectLeftTv.setText(left);
        selectRightTv.setText(right);
    }

    private void initUI() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_navigation_bar, this);
        titleBar1 = (RelativeLayout) findViewById(R.id.title1);
        titleBar2 = (RelativeLayout) findViewById(R.id.title2);
        leftBt = (ImageView) findViewById(R.id.bt_title_bar_left);
        rightBt = (ImageView) findViewById(R.id.iv_title_bar_right);
        leftTv = (TextView) findViewById(R.id.tv_title_bar_left);
        rightTv = (TextView) findViewById(R.id.tv_title_bar_right);
        activityTitle = (TextView) findViewById(R.id.activity_title);
        rightSecondBt = (ImageView) findViewById(R.id.iv_title_bar_right_second);
        selectLeftTv = (TextView) findViewById(R.id.select_left);
        selectRightTv = (TextView) findViewById(R.id.select_right);
        leftLineLl = (LinearLayout) findViewById(R.id.left_line);
        rightLineLl = (LinearLayout) findViewById(right_line);
        leftRl = (RelativeLayout) findViewById(R.id.left_rl);
        rightRl = (RelativeLayout) findViewById(R.id.right_rl);

        leftRl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTitleClickedListener.leftClicked();
            }
        });

        rightRl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTitleClickedListener.rightClicked();
            }
        });
    }

    public void rightTitleSelected() {
        Log.d("gdq", "right");
        leftLineLl.setVisibility(View.GONE);
        rightLineLl.setVisibility(View.VISIBLE);
        selectLeftTv.setTextColor(getResources().getColor(android.R.color.black));
        selectRightTv.setTextColor(getResources().getColor(R.color.title_color));
    }

    public void leftTitleSelected() {
        Log.d("gdq", "left");
        leftLineLl.setVisibility(View.VISIBLE);
        rightLineLl.setVisibility(View.GONE);
        selectLeftTv.setTextColor(getResources().getColor(R.color.title_color));
        selectRightTv.setTextColor(getResources().getColor(android.R.color.black));
    }


    public void setOnSelectTitleClickedListener(OnSelectTitleClickedListener listener) {
        selectTitleClickedListener = listener;
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

    public interface OnSelectTitleClickedListener {
        void leftClicked();

        void rightClicked();
    }

}