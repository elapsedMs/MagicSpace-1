package storm.magicspace.http;

import storm.commonlib.common.http.RequestTypes;
import storm.commonlib.common.http.ServiceUtils;
import storm.commonlib.common.http.baseHttpBean.BaseResponse;
import storm.magicspace.bean.httpBean.AccountInfoResponse;
import storm.magicspace.bean.httpBean.MyCollectionResponse;
import storm.magicspace.bean.httpBean.MyWorksResponse;

import static storm.commonlib.common.util.StringUtil.EMPTY;

public class HTTPManager {

    public static TestObject test() {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_GET_MATERIAL_LIST,
                EMPTY,
                TestObject.class
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
                "userId", "3970430042189702"
        );
    }

    /**
     * 获取我的收藏
     *
     * @param userId
     * @return
     */
    public static MyCollectionResponse getMycollection(String userId) {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_GET_MY_WORKS,
                EMPTY,
                MyCollectionResponse.class,
                "userId", "3970430042189702"
        );
    }

    public static class TestObject extends BaseResponse {
    }
}