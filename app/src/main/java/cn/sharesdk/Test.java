package cn.sharesdk;

import java.util.List;

/**
 * Created by lixiaolu on 16/7/7.
 */
public class Test {


    /**
     * date : 0
     * channel : 0
     * data_type : 0
     * data : [{}]
     */

    private int date;
    private int channel;
    private int data_type;
    private List<DataBean> data;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getData_type() {
        return data_type;
    }

    public void setData_type(int data_type) {
        this.data_type = data_type;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
    }
}
