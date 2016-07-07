package storm.magicspace.activity;

import android.os.Bundle;

import com.umeng.socialize.PlatformConfig;

import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseActivity;
import storm.commonlib.common.http.baseHttpBean.BaseResponse;
import storm.magicspace.R;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.util.LocalSPUtil;

import static storm.commonlib.common.CommonConstants.ACTIVITY_STYLE_WITH_LOADING;
import static storm.commonlib.common.util.StringUtil.EMPTY;

public class SplashActivity extends BaseActivity {

    public SplashActivity() {
        super(R.layout.activity_splash, ACTIVITY_STYLE_WITH_LOADING);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //豆瓣RENREN平台目前只能在服务器端配置
        //新浪微博
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        //易信
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");

        String token = LocalSPUtil.getToken();
        if (token != null && !token.equalsIgnoreCase(EMPTY)) {
            LoginByToken();
        } else {
            goToNext(LoginActivity.class);
        }
    }

    private void LoginByToken() {
        new AutoLoginTask().execute();
    }

    private class AutoLoginTask extends BaseASyncTask<String, BaseResponse> {
        @Override
        public BaseResponse doRequest(String param) {
            return HTTPManager.AutoLogin();
        }

        @Override
        public void onSuccess(BaseResponse baseResponse) {
            super.onSuccess(baseResponse);
            dismissBaseDialog();
            goToNext(MainActivity.class);
        }

        @Override
        public void onFailed() {
            super.onFailed();
            dismissBaseDialog();
            goToNext(LoginActivity.class);
        }

        @Override
        public void onSuccessWithoutResult(BaseResponse baseResponse) {
            super.onSuccessWithoutResult(baseResponse);
            dismissBaseDialog();
            goToNext(MainActivity.class);
        }
    }

}