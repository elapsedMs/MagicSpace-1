package storm.magicspace.http;

import storm.commonlib.common.http.RequestTypes;
import storm.commonlib.common.http.ServiceUtils;
import storm.commonlib.common.http.baseHttpBean.BaseResponse;
import storm.commonlib.common.util.StringUtil;

public class HTTPManager {

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