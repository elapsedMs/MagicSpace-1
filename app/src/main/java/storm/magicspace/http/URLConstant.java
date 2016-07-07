package storm.magicspace.http;

/**
 * Created by lixiaolu on 16/6/22.
 */
public class URLConstant {

//    public function addCollect() {}
//    public function deleteCollect() {}
//    public function deleteUGCContent() {}
//    public function getCollectUGCContentList() {}
//    public function getConcernPeopleList() {}
//    public function getFocusContentList() {}
//    public function searchUGCContent() {}
//
//
//    public function getMaterialDetail() {}
//    public function getMaterialList() {}
//    public function getUGCContentList() {}
//    public function getUGCContentDetail() {}
//    public function getUGCContentTypeList() {}
//    public function issueUGCContent() {}
//    public function updateUGCContentScenes() {}
//    public function getAccountInfo() {}
//    public function getAccountStatisticsInfo() {}
//    public function appreciate() {}
//    public function addCollect() {}
//    public function deleteCollect() {}
//    public function deleteUGCContent() {}
//    public function getCollectUGCContentList() {}

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

    //魔镜API的Key
    public static String MJ_KEY = "Bf@)(*$s1&2^3XVF#Mj";
    public static final String MJ_USER_CENTER_KEY = "0p9o8i7u";

    public static final String SHARED_URL = "http://app.stemmind.com/vr/html/gamedetail.php?c=";

    public static final String GAME_SHARED_URL = "http://app.stemmind.com/vr/a/player.php?s=ugc&c=";

    public static final String ADDREPORT = "addReport";
}