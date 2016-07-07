package storm.magicspace.activity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.PlatformConfig;

import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseActivity;
import storm.commonlib.common.http.baseHttpBean.BaseResponse;
import storm.commonlib.common.http.baseHttpBean.ListResponse;
import storm.commonlib.common.http.baseHttpBean.ObjectResponse;
import storm.commonlib.common.util.JsonUtil;
import storm.magicspace.R;
import storm.magicspace.bean.httpBean.MyWorksResponse;
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

        new Object();
        new ObjectResponse<>();
        Log.i("lixiaolu", JsonUtil.convertObjectToJson(new MyWorksResponse()));
        Log.i("lixiaolu", JsonUtil.convertObjectToJson(new ListResponse<Void>()));
        String text = "{\"date\":0,\"channel\":0,\"data_type\":0,\"status\":0,\"data\":[]}";

        JsonUtil.convertJsonToObject(text, TypeToken.get(MyWorksResponse.class));
        PlatformConfig.setWeixin("wxe1bd4b6f12b6491a", "a454e2ff97ec283009e677a628ebd37d");

        //新浪微博
        PlatformConfig.setSinaWeibo("2320803191", "f8c5701a92c13bbdfa349caaabd611a0");

        //qq
        PlatformConfig.setQQZone("1105505772", "uAaqPPyC1SEVEkly");

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