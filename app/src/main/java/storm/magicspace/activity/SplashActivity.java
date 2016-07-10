package storm.magicspace.activity;

import android.os.Bundle;

import com.umeng.socialize.PlatformConfig;

import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseActivity;
import storm.commonlib.common.http.baseHttpBean.BaseResponse;
import storm.magicspace.R;
import storm.magicspace.bean.CheckUpdate;
import storm.magicspace.bean.httpBean.CheckUpdateResponse;
import storm.magicspace.http.HTTPManager;
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

        PlatformConfig.setWeixin("wxe1bd4b6f12b6491a", "a454e2ff97ec283009e677a628ebd37d");

        //新浪微博
        PlatformConfig.setSinaWeibo("2320803191", "f8c5701a92c13bbdfa349caaabd611a0");

        //qq
        PlatformConfig.setQQZone("1105505772", "uAaqPPyC1SEVEkly");


        new CheckAppUpdate().execute();


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
                String token = LocalSPUtil.getToken();
                if (token != null && !token.equalsIgnoreCase(EMPTY)) {
                    LoginByToken();
                } else {
                    goToNext(LoginActivity.class);
                }
            } else {
                new UpdateDialog(SplashActivity.this, checkUpdate, new UpdateDialog.OnDialogCancelListener() {
                    @Override
                    public void onCancel() {
                        String token = LocalSPUtil.getToken();
                        if (token != null && !token.equalsIgnoreCase(EMPTY)) {
                            LoginByToken();
                        } else {
                            goToNext(LoginActivity.class);
                        }
                    }
                }).showDialog();
            }
        }

        @Override
        public void onFailed() {
            super.onFailed();
            dismissBaseDialog();
            String token = LocalSPUtil.getToken();
            if (token != null && !token.equalsIgnoreCase(EMPTY)) {
                LoginByToken();
            } else {
                goToNext(LoginActivity.class);
            }
        }

        @Override
        public void onSuccessWithoutResult(CheckUpdateResponse baseResponse) {
            super.onSuccessWithoutResult(baseResponse);
            dismissBaseDialog();
            String token = LocalSPUtil.getToken();
            if (token != null && !token.equalsIgnoreCase(EMPTY)) {
                LoginByToken();
            } else {
                goToNext(LoginActivity.class);
            }
        }
    }

}