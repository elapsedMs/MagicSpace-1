package storm.magicspace.http;

import storm.commonlib.common.http.RequestTypes;
import storm.commonlib.common.http.ServiceUtils;
import storm.magicspace.bean.httpBean.EggImageListResponse;

import static storm.commonlib.common.util.StringUtil.EMPTY;

/**
 * Created by lixiaolu on 16/6/28.
 */
public class AccountHttpManager {

    public static EggImageListResponse doLogin() {
        return ServiceUtils.accountRequest(
                RequestTypes.POST,
                URLConstant.LOGIN,
                EMPTY,
                EggImageListResponse.class,
                "contentListType", ""
        );
    }
}
