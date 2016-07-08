package storm.commonlib.common.http.baseHttpBean;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    public int status;          //状态码， 0 成功，其他为失败
    public String status_msg;   //状态码的表述信息
    public String version;      //当前返回结果的cms版本
    public int channel;         //当前返回结果的cms渠道
    public long date;           //服务器返回结果时的时间戳
    public String language;     //当前返回结果的语言
    public int data_type;       //0为横版，1为竖版
    public T data;              //消息体
    public String action;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatus_msg() {
        return status_msg;
    }

    public void setStatus_msg(String status_msg) {
        this.status_msg = status_msg;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getData_type() {
        return data_type;
    }

    public void setData_type(int data_type) {
        this.data_type = data_type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}