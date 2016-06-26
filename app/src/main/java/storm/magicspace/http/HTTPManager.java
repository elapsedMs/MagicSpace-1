package storm.magicspace.http;

import storm.commonlib.common.http.RequestTypes;
import storm.commonlib.common.http.ServiceUtils;
import storm.commonlib.common.http.baseHttpBean.BaseResponse;
import storm.magicspace.bean.httpBean.AccountInfoResponse;
import storm.magicspace.bean.httpBean.EggImageListResponse;
import storm.magicspace.bean.httpBean.MyCollectionResponse;
import storm.magicspace.bean.httpBean.MyWorksResponse;
import storm.magicspace.http.reponse.AlbumResponse;
import storm.magicspace.http.reponse.EggHttpResponse;

import static storm.commonlib.common.util.StringUtil.EMPTY;

public class HTTPManager {

    public static AlbumResponse test() {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_GET_MATERIAL_LIST,
                EMPTY,
                AlbumResponse.class
        );
    }

    /**
     * 获取账户信息
     *
     * @return AccountInfoResponse
     */
    public static AccountInfoResponse getAccountInfo() {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_GET_ACCOUNT_INFO,
                EMPTY,
                AccountInfoResponse.class,
                "userId", "3970430042189702",
                "userInfoId", "3945313002126939"
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
                RequestTypes.POST,
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

    public static class TestObject extends BaseResponse {
    }

    public static EggHttpResponse getEggList() {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_GET_EGG_LIST,
                EMPTY,
                EggHttpResponse.class
        );
    }
}