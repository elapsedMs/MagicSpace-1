package storm.commonlib.common.http;

public class HttpConstants {
    //    http://app.stemmind.com/3d/api/tp/index.php/getMaterialList
    public static final int TIME_OUT = 3 * 1000;


    private static final String SERVER_DOMAIN_URL = "http://app.stemmind.com/";
    private static final String SERVER_SECOND_URL = "3d/api/tp/index.php/";
    private static final String SERVER_TEST_URL = "3d/api/do.php";

    public static String ACCOUNT_HOST_URL = "http://sso.mojing.cn/user/api/";

    public static final String DEBUG_HOST = SERVER_DOMAIN_URL + SERVER_SECOND_URL;
//    public static final String DEBUG_HOST = "";

    public static final String REAL_HOST = DEBUG_HOST;
    public static final String X_AUTH_TOKEN = "X-AUTH-TOKEN";
    public static final String CONTENT_TYPE = "Content-Type";

    //    public static final String APPLICATION_JSON = "application/json;charset=UTF-8";
    public static final String APPLICATION_JSON = "application/x-www-form-urlencoded;charset=UTF-8";

    public static final String DEV_FILE_HOST = "http://test.st.medtree.cn";
}