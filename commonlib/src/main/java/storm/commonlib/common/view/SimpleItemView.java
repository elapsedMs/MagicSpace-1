package storm.commonlib.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import storm.commonlib.R;


/**
 * Created by gdq on 16/3/22.
 */
public class SimpleItemView extends RelativeLayout {

    private final int STYLE_NORMAL = 1;
    private final int STYLE_NORMALA_BLOD = 4;
    private final int STYLE_WITHOUT_ARROW = 2;
    private final int STYLE_HAVE_ARROW = 3;

    private TextView nameTv;
    private TextView countTv;
    private ImageView arrowIv;

    public SimpleItemView(Context context) {
        this(context, null);
    }

    public SimpleItemView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SimpleItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SimpleItemView);
        int style = array.getInt(R.styleable.SimpleItemView_sv_style, -1);
        String name = array.getString(R.styleable.SimpleItemView_sv_name);
        initView(context, style, name);
    }

    private void initView(Context context, int style, String name) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_simple_item, this);
        nameTv = (TextView) view.findViewById(R.id.name);
        countTv = (TextView) view.findViewById(R.id.count);
        arrowIv = (ImageView) view.findViewById(R.id.arrow);
        nameTv.setText(name);
        switchStyle(style);
    }

    private void switchStyle(int style) {
        switch (style) {
            case STYLE_NORMAL:
                break;
            case STYLE_HAVE_ARROW:
                nameTv.getPaint().setFakeBoldText(true);
                arrowIv.setVisibility(View.VISIBLE);
                countTv.setTextColor(getResources().getColor(R.color.color99252525));
                break;
            case STYLE_WITHOUT_ARROW:
                nameTv.getPaint().setFakeBoldText(true);
                countTv.setTextColor(getResources().getColor(R.color.color99252525));
                break;
            case STYLE_NORMALA_BLOD:
                nameTv.getPaint().setFakeBoldText(true);
                break;
        }
    }

    public void setCount(String count) {
        countTv.setText(count);
    }

    public void setStyle(int style) {
        switch (style) {
            case STYLE_NORMAL:
                break;
            case STYLE_HAVE_ARROW:
                nameTv.getPaint().setFakeBoldText(true);
                arrowIv.setVisibility(View.VISIBLE);
                countTv.setTextColor(getResources().getColor(R.color.color99252525));
                break;
            case STYLE_WITHOUT_ARROW:
                nameTv.getPaint().setFakeBoldText(true);
                countTv.setTextColor(getResources().getColor(R.color.color99252525));
                break;
            case STYLE_NORMALA_BLOD:
                nameTv.getPaint().setFakeBoldText(true);
                break;
        }
    }

    public String getCount() {
        return countTv.getText().toString();
    }
}
