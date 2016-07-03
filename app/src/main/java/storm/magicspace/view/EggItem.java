package storm.magicspace.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import storm.commonlib.common.base.BaseView;
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
    public void initView(Context context) {
        super.initView(context);
        tv_egg_name = (TextView) findViewById(R.id.tv_egg_name);
        tv_egg_date = (TextView) findViewById(R.id.tv_egg_date);
        tv_egg_date.setVisibility(GONE);
        tv_egg_works_mojing = (TextView) findViewById(R.id.tv_egg_works_mojing);
        tv_egg_works_egg = (TextView) findViewById(R.id.tv_egg_works_egg);
        tv_egg_works_gold = (TextView) findViewById(R.id.tv_egg_works_gold);
        tv_egg_works_time = (TextView) findViewById(R.id.tv_egg_works_time);
        iv_egg = (ImageView) findViewById(R.id.iv_egg);
    }

    @Override
    public void bindData(Object object) {
        super.bindData(object);
        EggInfo info = (EggInfo) object;
        if (info != null) {
            tv_egg_works_mojing.setText(info.playCount == null ? "" : info.playCount);
            tv_egg_works_gold.setText(info.creditEarned == null ? "" : info.creditEarned);
            tv_egg_works_egg.setText(info.itemCount == null ? "" : info.itemCount);
            tv_egg_name.setText(info.title == null ? "" : info.title);
            Picasso.with(mContext).load(info.thumbImageUrl).into(iv_egg);
            tv_egg_date.setText(info.createTime == null ? "" : info.createTime);
            tv_egg_works_time.setText(info.avgtime == null ? "" : info.avgtime);
            String time_str = "";

            if (info.createTime != null) {
                time_str = timestamp2Date(info.createTime);
            }

            if (time_str.length() > 16) {
                tv_egg_date.setText(time_str.substring(0, 10));
            }

        }
    }

    /* 将10 or 13 位时间戳转为时间字符串
    * convert the number 1407449951 1407499055617 to date/time format timestamp
    */
    public static String timestamp2Date(String str_num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (str_num.length() == 13) {
            String date = sdf.format(new Date(Long.valueOf(str_num)));
            return date;
        } else {
            String date = sdf.format(new Date(Integer.valueOf(str_num) * 1000L));
            return date;
        }
    }
}
