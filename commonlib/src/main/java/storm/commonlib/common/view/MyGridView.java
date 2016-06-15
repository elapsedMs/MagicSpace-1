package storm.commonlib.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MyGridView extends GridView {
    private Context context;
    private boolean canSelet = false;
    private ArrayList<String> imgList;

    public MyGridView(Context context) {
        super(context);
        this.context = context;
        setOnItemClickListener(new MGridViewItemClick());
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOnItemClickListener(new MGridViewItemClick());
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        setOnItemClickListener(new MGridViewItemClick());
        // TODO 自动生成的构造函数存根
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO 自动生成的方法存根  
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    public void setViewClick(ArrayList<String> imgList) {
        this.canSelet = true;
        this.imgList = imgList;
    }

    private class MGridViewItemClick implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (canSelet) {
            }
        }
    }
}