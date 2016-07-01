package storm.magicspace.http.reponse;

/**
 * Created by lixiaolu on 16/7/1.
 */
public class RegisterResponse {
    /**
     * status : true
     * msg : 3992379388679395
     */

    private boolean status;
    private String msg;
    private int code;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
