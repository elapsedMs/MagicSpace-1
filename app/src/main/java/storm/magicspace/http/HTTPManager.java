package storm.magicspace.http;

import storm.commonlib.common.http.RequestTypes;
import storm.commonlib.common.http.ServiceUtils;
import storm.commonlib.common.http.baseHttpBean.BaseResponse;
import storm.magicspace.http.reponse.AlbumResponse;

import static storm.commonlib.common.util.StringUtil.EMPTY;

public class HTTPManager {

    /**
     * test
     *
     * @return
     */
    public static AlbumResponse test() {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_GET_MATERIAL_LIST,
                EMPTY,
                AlbumResponse.class
        );
    }

    /**
     * @return
     */
    public static BaseResponse getAccountInfo() {
        return ServiceUtils.request(
                RequestTypes.POST,
                URLConstant.URL_GET_ACCOUNT_INFO,
                EMPTY,
                BaseResponse.class,
                "userId", "123672761172619501",
                "userinfoId", "135601920002522269"
        );
    }

    public static class TestObject extends BaseResponse {
    }
}