package storm.magicspace.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import storm.commonlib.common.base.BaseView;
import storm.magicspace.R;

/**
 * Created by gdq on 16/6/16.
 */
public class HomeTabView extends BaseView implements View.OnClickListener {
    private final int ALBUM = 1;
    private final int EGG = 2;
    private final int MY = 3;
    private final int SETTING = 4;
    private ImageView picIv;
    private TextView nameTv;
    private boolean hasThisView = false;
    private static int lastClickId;
    private static List<HomeTabView> list = new ArrayList<>();

    public HomeTabView(Context context) {
        this(context, null);
    }

    public HomeTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle, R.layout.view_home_tab);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HomeTabView);
        int style = typedArray.getInteger(R.styleable.HomeTabView_home_tab_style, -1);
        init(style);
    }

    private void init(int style) {
        setClickable(true);
        setOnClickListener(this);
        picIv = this.findView(R.id.tab_img);
        nameTv = this.findView(R.id.tab_name);
        switch (style) {
            case ALBUM:
                break;
            case EGG:
                break;
            case MY:
                break;
            case SETTING:
                break;
            default:
                break;
        }
        picIv.setImageResource(R.mipmap.ic_launcher);
        nameTv.setText("gdq");
    }

    @Override
    public void setSelected(boolean selected) {

        picIv.setSelected(selected);
        nameTv.setSelected(selected);
    }
}
