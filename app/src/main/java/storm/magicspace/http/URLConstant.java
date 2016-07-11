package storm.magicspace.http;

import static storm.commonlib.common.util.StringUtil.EMPTY;
import static storm.magicspace.util.LocalSPUtil.getAppConfig;

/**
 * Created by lixiaolu on 16/6/22.
 */
public class URLConstant {
    //获取主页数据(图库数据)
    public static final String URL_GET_MATERIAL_LIST = "getMaterialList";

    //获取个人信息（我的）
    public static final String URL_GET_ACCOUNT_INFO = "getAccountInfo";

    //获取我的作品
    public static final String URL_GET_MY_WORKS = "getUGCContentListByUser";

    //获取我的收藏
    public static final String URL_GET_MY_COLLECTION = "getCollectUGCContentList";

    //获取主页轮播
    public static final String URL_GET_FOCUS_CONTENT_LIST = "getFocusContentList";

    //获取个人主页轮播
    public static final String URL_GET_COUPON_LIST = "getcouponList";

    //获取彩蛋区列表
    public static final String URL_GET_EGG_LIST = "getUGCContentList";

    //获取彩蛋列表
    public static final String URL_GET_EGG_IMAGE_LIST = "getDefaultObjectList";

    //发表UGC主题
    public static final String URL_ISSUE_UGC_CONTENT = "issueUGCContent";

    //更新UGC主题
    public static final String URL_SUBMIT_UGC_CONTENT = "submitUGCContent";

    //发布游戏
    public static final String URL_UPDATE_UGC_CONTENT_SCENES = "updateUGCContentScenes";

    //启动时检查版本更新
    public static final String CHECK_APP_UPDATE = "checkAppUpdate";

    //同步账户信息
    public static final String URL_SYNC_ACCOUNT = "syncAccount";

    //自动登录
    public static final String URL_AUTO_LOGIN = "userLogin";

    //添加收藏
    public static final String URL_ADD_COLLECTION = "addCollect";

    //登录
    public static final String LOGIN = "apilogin";

    //注册
    public static final String REGIESTER = "apiregist";

    //获取验证码
    public static final String GET_VERIFY_CODE = "sendsmscode";

    public static final String INIT_HOST = "http://app.stemmind.com/MFconf/?v=1";

    //API HOST
    private static final String SERVER_DOMAIN_URL = getAppConfig().apidomain == null || getAppConfig().apidomain.equals(EMPTY) ? "http://app.stemmind.com/" : getAppConfig().apidomain;
    private static final String SERVER_SECOND_URL = getAppConfig().apipath == null || getAppConfig().apipath.equals(EMPTY) ? "3d/api/tp/index.php/" : getAppConfig().apipath;
    public static final String API_HOST = SERVER_DOMAIN_URL + SERVER_SECOND_URL;

    //VR
    public static final String TYPE_TWO = getAppConfig().vrdomain == null || getAppConfig().vrdomain.equals(EMPTY) ? "http://app.stemmind.com" : getAppConfig().vrdomain;
    public static final String TYPE_TWO_TWO = getAppConfig().vrpath == null || getAppConfig().vrpath.equals(EMPTY) ? "/vr/a/" : getAppConfig().vrpath;

    //HTML
    public static final String TYPE_THREE = getAppConfig().htmldomain == null || getAppConfig().htmldomain.equals(EMPTY) ? "http://app.stemmind.com" : getAppConfig().htmldomain;
    public static final String TYPE_THREE_THEE = getAppConfig().htmlpath == null || getAppConfig().htmlpath.equals(EMPTY) ? "/vr/html/" : getAppConfig().htmlpath;

    //我的作品嵌入URL
    public static final String URL_2 = TYPE_TWO + TYPE_TWO_TWO + "preview.php?ua=app&s=ugc&c=";

    public static final String URL_4 = TYPE_TWO + TYPE_TWO_TWO + "player.php?ua=app&s=ugc&c=";
    public static final String URL_110 = TYPE_TWO + TYPE_TWO_TWO + "preview.php?ua=app&s=ugc&c=";

    public static final String URL_112 = TYPE_TWO + TYPE_TWO_TWO + "test.php?ua=app&c=";
    public static final String SHARE_US_URL = TYPE_THREE + TYPE_THREE_THEE + "download.php";
    public static final String SHARE_OUT_URL = TYPE_THREE + TYPE_THREE_THEE + "gamedetail.php?c=";

    public static final String EGG_GAME_PRE_SHARE_URL = TYPE_THREE + TYPE_THREE_THEE + "gamedetail.php?c=";
    public static final String OUT_SHAER_1 = TYPE_THREE + TYPE_THREE_THEE + "gamedetail.php?c=";
    public static final String URL_111 = TYPE_THREE + TYPE_THREE_THEE + "gamedetail.php?c=";
    public static final String MAIN_GUI = TYPE_THREE + TYPE_THREE_THEE + "guide.php";
    public static final String URL_113 = TYPE_THREE + TYPE_THREE_THEE + "gamedetail.php?c=";


    public static final String URL_WEBVIEW_TOPIC = TYPE_TWO + TYPE_TWO_TWO + "vreditor.php?ua=app&s=mat&c=";

    public static final String URL_WEBVIEW_GAME = TYPE_TWO + TYPE_TWO_TWO + "vreditor.php?ua=app&s=ugc&c=";

    public static final String URL_WEBVIEW_PREVIEW_TOPIC = TYPE_TWO + TYPE_TWO_TWO + "preview.php?ua=app&s=mat&c=";

    public static final String URL_WEBVIEW_PREVIEW_GAME = TYPE_TWO + TYPE_TWO_TWO + "" + "preview.php?ua=app&s=ugc&c=";

    //魔镜API的Key
    public static String MJ_KEY = "Bf@)(*$s1&2^3XVF#Mj";
    public static final String MJ_USER_CENTER_KEY = "0p9o8i7u";

    public static final String SHARED_URL = TYPE_THREE + TYPE_THREE_THEE + "gamedetail.php?c=";

    public static final String ADDREPORT = "addReport";
    public static final String GETAPPSHARELINK = "getAppShareLink";
    public static final String GAMEEND = "gameEnd";
    public static final String REQINFOCALLBACK = "reqInfoCallback";
}