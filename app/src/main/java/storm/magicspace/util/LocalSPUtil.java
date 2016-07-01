package storm.magicspace.util;

import storm.commonlib.common.BaseApplication;
import storm.commonlib.common.util.SharedPreferencesUtil;
import storm.magicspace.http.reponse.LoginResponse;

/**
 * Created by lixiaolu on 16/6/28.
 */
public class LocalSPUtil extends SharedPreferencesUtil {

    public static void saveAccountInfo(LoginResponse.AccountInfo data) {
        saveObject(BaseApplication.getApplication(), data);
    }

    public static LoginResponse.AccountInfo getAccountInfo() {
        return getObject(BaseApplication.getApplication(), LoginResponse.AccountInfo.class);
    }

}