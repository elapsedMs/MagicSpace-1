package storm.magicspace.http;

import storm.commonlib.common.http.RequestTypes;
import storm.commonlib.common.http.ServiceUtils;
import storm.commonlib.common.http.baseHttpBean.BaseResponse;
import storm.commonlib.common.util.StringUtil;

public class HTTPManager {

//    public static void login(LoginRequestParams params) {
//        return ServiceUtils.request(
//                RequestTypes.POST,
//                URL_USER_LOGIN,
//                EMPTY,
//                LoginResponse.class,
//                "user_name", params.userName,
//                "login_type", params.loginType,
//                "password", params.password
//        );
//    }
//


    public static TestObject test() {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_GET_MATERIAL_LIST,
                StringUtil.EMPTY,
                TestObject.class
        );

    }

    public static class TestObject extends BaseResponse {
    }
}