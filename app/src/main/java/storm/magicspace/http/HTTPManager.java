package storm.magicspace.http;

import storm.commonlib.common.http.RequestTypes;
import storm.commonlib.common.http.ServiceUtils;
import storm.magicspace.bean.httpBean.CirclePicResponse;
import storm.magicspace.bean.httpBean.EggImageListResponse;
import storm.magicspace.bean.httpBean.IssueUCGContentResponse;
import storm.magicspace.bean.httpBean.MyCollectionResponse;
import storm.magicspace.bean.httpBean.MyWorksResponse;
import storm.magicspace.bean.httpBean.SyncAccountResponse;
import storm.magicspace.bean.httpBean.UpdateUGCContentScenesResponse;
import storm.magicspace.bean.httpBean.UserInfoResponse;
import storm.magicspace.http.reponse.AlbumResponse;
import storm.magicspace.http.reponse.EggHttpResponse;
import storm.magicspace.util.LocalSPUtil;

import static storm.commonlib.common.util.StringUtil.EMPTY;

public class HTTPManager {

    public static AlbumResponse test(String contentListType) {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_GET_MATERIAL_LIST,
                EMPTY,
                AlbumResponse.class,
                "contentListType", contentListType
        );
    }

    /**
     * 获取账户信息
     *
     * @return AccountInfoResponse
     */
    public static UserInfoResponse getAccountInfo() {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_GET_ACCOUNT_INFO,
                EMPTY,
                UserInfoResponse.class,
                "userId", "1"
        );
    }

    /**
     * 获取我的作品
     *
     * @param userId
     * @param authorId
     * @return
     */
    public static MyWorksResponse getMyWorks(String userId, String authorId) {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_GET_MY_WORKS,
                EMPTY,
                MyWorksResponse.class,
                "userId", LocalSPUtil.getAccountInfo().getUser_no(),
                "authorId", LocalSPUtil.getAccountInfo().getUser_no()
        );
    }

    /**
     * 获取我的收藏
     *
     * @return
     */
    public static MyCollectionResponse getMyCollection(String type) {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_GET_MY_COLLECTION,
                EMPTY,
                MyCollectionResponse.class,
                "userId", LocalSPUtil.getAccountInfo().getUser_no(),
                "contentTypeId", type
        );
    }

    /**
     * 获取彩蛋图片列表
     */
    public static EggImageListResponse getEggImageList() {
        return ServiceUtils.request(
                RequestTypes.GET,
                URLConstant.URL_GET_EGG_IMAGE_LIST,
                EMPTY,
                EggImageListResponse.class
        );

    }

    /**
     * 发表UGC主题
     */
    public static IssueUCGContentResponse issueUCCContent(String userId,
                                                          String description,
                                                          String url,
                                                          String sourceId) {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_ISSUE_UGC_CONTENT,
                EMPTY,
                IssueUCGContentResponse.class,
                "userId", LocalSPUtil.getAccountInfo().getUser_no(),
                "description", description,
                "url", url,
                "sourceId", sourceId
        );
    }

    /**
     * 更新UGC主题
     */
    public static UpdateUGCContentScenesResponse updateUGCContentScenes(String userId,
                                                                        String contendId,
                                                                        String data) {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_UPDATE_UGC_CONTENT_SCENES,
                EMPTY,
                UpdateUGCContentScenesResponse.class,
                "userId", LocalSPUtil.getAccountInfo().getUser_no(),
                "contentId", contendId,
                "data", data
        );
    }

    public static SyncAccountResponse syncAccount(String userId, String data) {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_SYNC_ACCOUNT,
                EMPTY,
                SyncAccountResponse.class,
                "userId", userId,
                "data", data
        );
    }


    public static CirclePicResponse getAlbumCirclePic() {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_GET_FOCUS_CONTENT_LIST,
                EMPTY,
                CirclePicResponse.class,
                "", ""
        );
    }

    public static EggHttpResponse getEggList() {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_GET_EGG_LIST,
                EMPTY,
                EggHttpResponse.class,
                "userId", "0",
                "page", "1",
                "pageSize", "2"
        );
    }

    public static EggHttpResponse addReport() {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.ADDREPORT,
                EMPTY,
                EggHttpResponse.class,
                "userId", "0",
                "versionCode", "1",
                "content", "1",
                "contact", "1"
        );
    }
}