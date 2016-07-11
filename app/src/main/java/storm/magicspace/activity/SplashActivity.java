package storm.magicspace.activity;

import android.os.Bundle;

import com.umeng.socialize.PlatformConfig;

import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseActivity;
import storm.commonlib.common.http.HttpUtils;
import storm.commonlib.common.http.baseHttpBean.BaseResponse;
import storm.magicspace.R;
import storm.magicspace.bean.CheckUpdate;
import storm.magicspace.bean.httpBean.CheckUpdateResponse;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.http.URLConstant;
import storm.magicspace.http.reponse.InitResponse;
import storm.magicspace.util.LocalSPUtil;
import storm.magicspace.view.UpdateDialog;

import static storm.commonlib.common.CommonConstants.ACTIVITY_STYLE_WITH_LOADING;
import static storm.commonlib.common.util.StringUtil.EMPTY;

public class SplashActivity extends BaseActivity {

    public SplashActivity() {
        super(R.layout.activity_splash, ACTIVITY_STYLE_WITH_LOADING);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initURL();

        initYouMeng();

    }

    private void initURL() {
        HttpUtils.setHostUri(URLConstant.INIT_HOST);
        new InitTask().execute();
    }

    private void initYouMeng() {
        //微信
        PlatformConfig.setWeixin("wxe1bd4b6f12b6491a", "a454e2ff97ec283009e677a628ebd37d");

        //新浪微博
        PlatformConfig.setSinaWeibo("2320803191", "f8c5701a92c13bbdfa349caaabd611a0");

        //qq
        PlatformConfig.setQQZone("1105505772", "uAaqPPyC1SEVEkly");
    }

    private void loginByToken() {
        String token = LocalSPUtil.getToken();
        if (token != null && !token.equalsIgnoreCase(EMPTY)) {
            new AutoLoginTask().execute();
        } else {
            goToNext(LoginActivity.class);
        }
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

    //启动时检查版本更新
    private class CheckAppUpdate extends BaseASyncTask<String, CheckUpdateResponse> {
        @Override
        public CheckUpdateResponse doRequest(String param) {
            return HTTPManager.checkAppUpdate();
        }

        @Override
        public void onSuccess(CheckUpdateResponse baseResponse) {
            super.onSuccess(baseResponse);
            dismissBaseDialog();
            CheckUpdate checkUpdate = baseResponse.getData();
            checkUpdate.forceInstall = "0";
            checkUpdate.apkPath = "http://www.imooc.com/mobile/imooc.apk";
            if (Integer.parseInt(checkUpdate.forceInstall) < 0) {
                loginByToken();
            } else {
                new UpdateDialog(SplashActivity.this, checkUpdate, new UpdateDialog.OnDialogCancelListener() {
                    @Override
                    public void onCancel() {
                        loginByToken();
                    }
                }).showDialog();
            }

        }

        @Override
        public void onFailed() {
            super.onFailed();
            dismissBaseDialog();
            loginByToken();
        }

        @Override
        public void onSuccessWithoutResult(CheckUpdateResponse baseResponse) {
            super.onSuccessWithoutResult(baseResponse);
            dismissBaseDialog();
            loginByToken();
        }
    }

    private class InitTask extends BaseASyncTask<Void, InitResponse> {

        @Override
        public InitResponse doRequest(Void param) {
            return HTTPManager.initAppConfig();

        }

        @Override
        public void onFailed() {
            super.onFailed();

            loginByToken();
            HttpUtils.setHostUri(URLConstant.API_HOST);
        }

        @Override
        public void onFailed(InitResponse initResponse) {
            super.onFailed(initResponse);
            loginByToken();
            LocalSPUtil.saveAppConfig(initResponse.data);
            HttpUtils.setHostUri(URLConstant.API_HOST);
        }

        @Override
        public void onSuccess(InitResponse initResponse) {
            super.onSuccess(initResponse);
            new CheckAppUpdate().execute();
            HttpUtils.setHostUri(URLConstant.API_HOST);
        }

        @Override
        public void onSuccessWithoutResult(InitResponse initResponse) {
            super.onSuccessWithoutResult(initResponse);
            loginByToken();
            HttpUtils.setHostUri(URLConstant.API_HOST);
        }
    }
}