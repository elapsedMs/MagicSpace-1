package storm.magicspace.view;


import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import storm.commonlib.common.base.BaseView;
import storm.commonlib.common.util.ImageSize;
import storm.magicspace.R;
import storm.magicspace.bean.EggInfo;


public class EggItem extends BaseView {
    private TextView tv_egg_name, tv_egg_date, tv_egg_works_mojing, tv_egg_works_egg, tv_egg_works_gold, tv_egg_works_time;
    private ImageView iv_egg;
    private Context mContext;

    public EggItem(Context context, AttributeSet attrs,
                   int defStyle) {
        super(context, attrs, defStyle, R.layout.view_egg_item);
        this.mContext = context;
    }

    public EggItem(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.view_egg_item);
        this.mContext = context;
    }

    public EggItem(Context context) {
        super(context, R.layout.view_egg_item);
        this.mContext = context;
    }

    @Override
    public void bindData(Object object) {
        super.bindData(object);
        EggInfo info = (EggInfo) object;
        tv_egg_name.setText(info.nickName == null ? "" : info.nickName);
        Picasso.with(mContext).load(info.thumbImageUrl).into(iv_egg);
        tv_egg_date.setText(info.createTime == null ? "" : info.createTime);

    }

    @Override
    public void initView(Context context) {
        super.initView(context);
        tv_egg_name = (TextView) findViewById(R.id.tv_egg_name);
        tv_egg_date = (TextView) findViewById(R.id.tv_egg_date);
        tv_egg_works_mojing = (TextView) findViewById(R.id.tv_egg_works_mojing);
        tv_egg_works_egg = (TextView) findViewById(R.id.tv_egg_works_egg);
        tv_egg_works_gold = (TextView) findViewById(R.id.tv_egg_works_gold);
        tv_egg_works_time = (TextView) findViewById(R.id.tv_egg_works_time);
        iv_egg = (ImageView) findViewById(R.id.iv_egg);
    }


}
