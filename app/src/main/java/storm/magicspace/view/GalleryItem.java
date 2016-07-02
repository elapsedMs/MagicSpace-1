package storm.magicspace.view;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.util.Constants;
import com.squareup.picasso.Picasso;

import storm.commonlib.common.util.ImageSize;
import storm.commonlib.common.util.ImageUtils;
import storm.magicspace.R;
import storm.magicspace.bean.Company;

/**
 * Created by py on 15/12/15.
 */
public class GalleryItem extends FrameLayout {

    private TextView organization;
    private TextView position;
    public RoundedImageView image;
    private Context mContext;
    private RelativeLayout rl_gallery_item;

    public GalleryItem(Context context) {
        super(context);

        initView(context);
        this.mContext = context;
    }

    public GalleryItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context);
        this.mContext = context;
    }

    public GalleryItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView(context);
        this.mContext = context;
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.gallery_item, this, true);
        organization = (TextView) findViewById(R.id.gallery_item_organization);
        position = (TextView) findViewById(R.id.gallery_item_position);
        image = (RoundedImageView) findViewById(R.id.gallery_item_image);
        rl_gallery_item = (RelativeLayout) findViewById(R.id.rl_gallery_item);
    }

    public void bindData(Company company, boolean isSelectedItem) {
//        company.name = company.name == null ? EMPTY : company.name;
//        String grade_str = getInstance().getDepartmentLevel().getContentByServerId(company.level);
//        organization.setText(!grade_str.equals(getContext().getString(R.string.other)) ? company.name + "(" + grade_str + ")" : company.name);

//        position.setText(getContext().getString(R.string.position_finding) + company.job_count + getContext().getString(R.string.position_count));
        DisplayMetrics dm = this.mContext.getApplicationContext().getResources().getDisplayMetrics();

//        ImageUtils.display(image, company.logo, ImageSize.Orig, R.drawable.img_company, Constants.EMPTY_SIZE);
        Picasso.with(mContext).load(company.thumbImageUrl).into(image);
        rebuildChannelItemView(dm, (int) (isSelectedItem ? 120 * 1.35f : 120), (int) (isSelectedItem ? 70 * 1.35f : 70));
        organization.setVisibility(isSelectedItem ? VISIBLE : GONE);
        position.setVisibility(isSelectedItem ? VISIBLE : GONE);
    }

    private void rebuildChannelItemView(DisplayMetrics dm, int width, int height) {
        setImageViewParams((int) (width * dm.densityDpi / 160 + 0.5f), (int) (height * dm.densityDpi / 160 + 0.5f));
        setTipViewParams((int) (width * dm.densityDpi / 160 + 0.5f), (int) (height * dm.densityDpi / 160 + 0.5f));
    }

    private void setImageViewParams(int widthType, int heightType) {
        image.setLayoutParams(new LayoutParams(widthType, heightType));
        image.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    private void setTipViewParams(int widthType, int heightType) {
        rl_gallery_item.setLayoutParams(new LayoutParams(widthType, heightType));
    }

}
