package storm.commonlib.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import storm.commonlib.R;


public class TabView extends LinearLayout {

    private Context context;
    private ImageView icIV;
    private TextView nameTv;

    public TabView(Context context) {
        super(context);
        this.context = context;
        initUI();
    }

    public TabView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }


    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabView);
        int icon = array.getResourceId(R.styleable.TabView_icon2, R.drawable.selector_consult);
        String text = array.getString(R.styleable.TabView_text);
        initUI();
        setRes(icon, text);
    }

    private void setRes(int icon, String text) {
        icIV.setImageResource(icon);
        nameTv.setText(text);
    }

    private void initUI() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_tab, this);

        icIV = (ImageView) findViewById(R.id.iv_tab_title);
        nameTv = (TextView) findViewById(R.id.tv_tab_title);
    }

    public void setTitle(int resId) {

    }

    public void setText(String text) {
        nameTv.setText(text);
    }

    public void setDrawble(int textResId) {
        icIV.setImageResource(textResId);
    }


    public void setTabColor(int blue) {
        nameTv.setTextColor(blue);
    }

}