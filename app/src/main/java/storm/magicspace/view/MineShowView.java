package storm.magicspace.view;

import android.content.Context;
import android.util.AttributeSet;

import storm.commonlib.common.base.BaseView;
import storm.commonlib.common.view.RoundedImageView;
import storm.magicspace.R;

/**
 * Created by lixiaolu on 16/7/4.
 */
public class MineShowView extends BaseView {

    private RoundedImageView imageView;

    public MineShowView(Context context) {
        super(context, R.layout.mine_show_gallery);
    }

    public MineShowView(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.mine_show_gallery);
    }

    public MineShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle, R.layout.mine_show_gallery);
    }

    @Override
    public void initView(Context context) {
        super.initView(context);
        imageView = (RoundedImageView) findViewById(R.id.mine_show_iamge);
    }


    public RoundedImageView getImageView() {
        return imageView;
    }
}
