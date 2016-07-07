package storm.magicspace.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import storm.commonlib.common.BaseApplication;
import storm.magicspace.R;

/**
 * Created by lixiaolu on 16/7/6.
 */
public class AuthLoginView extends LinearLayout implements View.OnClickListener {
    private UMShareAPI mShareAPI = null;
    private Context context;
    private Activity activity;
    private ImageView shareSina;
    private ImageView shareWechat;
    private ImageView shareQQ;

    public AuthLoginView(Context context) {
        super(context);
        mShareAPI = UMShareAPI.get(context);
        initView(context);
    }

    public AuthLoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mShareAPI = UMShareAPI.get(context);
        initView(context);
    }

    public AuthLoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mShareAPI = UMShareAPI.get(context);
        initView(context);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    public void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.auth_login, this, true);

        shareSina = (ImageView) findViewById(R.id.share_xinlang);
        shareWechat = (ImageView) findViewById(R.id.share_weixin);
        shareQQ = (ImageView) findViewById(R.id.share_qq);

        shareSina.setOnClickListener(this);
        shareWechat.setOnClickListener(this);
        shareQQ.setOnClickListener(this);
//
    }

    @Override
    public void onClick(View v) {
        SHARE_MEDIA platform = null;
        switch (v.getId()) {
            case R.id.share_xinlang:
                platform = SHARE_MEDIA.SINA;
                break;
            case R.id.share_qq:
                platform = SHARE_MEDIA.QQ;
                break;
            case R.id.share_weixin:
                platform = SHARE_MEDIA.WEIXIN;
                break;
        }
        mShareAPI.doOauthVerify(activity, platform, umAuthListener);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(BaseApplication.getApplication(), "Authorize succeed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(BaseApplication.getApplication(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(BaseApplication.getApplication(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

}
