package storm.magicspace.http;

import storm.commonlib.common.http.RequestTypes;
import storm.commonlib.common.http.ServiceUtils;
import storm.magicspace.bean.httpBean.CirclePicResponse;
import storm.magicspace.bean.httpBean.EggImageListResponse;
import storm.magicspace.bean.httpBean.IssueUCGContentResponse;
import storm.magicspace.bean.httpBean.MyCollectionResponse;
import storm.magicspace.bean.httpBean.MyWorksResponse;
import storm.magicspace.bean.httpBean.UpdateUGCContentScenesResponse;
import storm.magicspace.bean.httpBean.UserInfoResponse;
import storm.magicspace.http.reponse.AlbumResponse;
import storm.magicspace.http.reponse.EggHttpResponse;

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
                RequestTypes.GET,
                URLConstant.URL_GET_MY_WORKS,
                EMPTY,
                MyWorksResponse.class,
                "userId", "3970430042189702",
                "authorId", "3970430042189702"
        );
    }

    /**
     * 获取我的收藏
     *
     * @param userId
     * @return
     */
    public static MyCollectionResponse getMyCollection(String userId) {
        return ServiceUtils.request(
                RequestTypes.GET,
                URLConstant.URL_GET_MY_WORKS,
                EMPTY,
                MyCollectionResponse.class,
                "userId", "3970430042189702"
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
                                                          String url) {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_ISSUE_UGC_CONTENT,
                EMPTY,
                IssueUCGContentResponse.class,
                "userId", "3970430042189702",
                "description", description,
                "url", url
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
                "userId", "3970430042189702",
                "contendId", contendId,
                "data", data
        );
    }

    public static CirclePicResponse getAlbumCirclePic() {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_GET_FOCUS_CONTENT_LIST,
                EMPTY,
                CirclePicResponse.class
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
}