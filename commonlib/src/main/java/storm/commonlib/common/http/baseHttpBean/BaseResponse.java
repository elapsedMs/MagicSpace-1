package storm.commonlib.common.http.baseHttpBean;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    public int errCode;          //状态码， 0 成功，其他为失败
    public String errInfo;   //状态码的表述信息
    public String action;      // ？
    public T data;              //消息体

}