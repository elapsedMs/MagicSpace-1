package storm.magicspace.bean;

/**
 * Created by gdq on 16/6/25.
 */
public class CirclePic {

    /**
     * msg : 访问的接口正在建设中
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
