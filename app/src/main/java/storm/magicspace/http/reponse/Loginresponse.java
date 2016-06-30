package storm.magicspace.http.reponse;

/**
 * Created by lixiaolu on 16/6/28.
 */
public class LoginResponse {

    /**
     * status : true
     * msg : 成功
     * data : {"user_no":"3961629889036189","user_tel":"18601324008","user_name":"黑魂","user_email":"0"}
     */

    private boolean status;
    private String msg;
    /**
     * user_no : 3961629889036189
     * user_tel : 18601324008
     * user_name : 黑魂
     * user_email : 0
     */

    private AccountInfo data;

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

    public AccountInfo getData() {
        return data;
    }

    public void setData(AccountInfo data) {
        this.data = data;
    }

    public static class AccountInfo {
        private String user_no;
        private String user_tel;
        private String user_name;
        private String user_email;

        public String getUser_no() {
            return user_no;
        }

        public void setUser_no(String user_no) {
            this.user_no = user_no;
        }

        public String getUser_tel() {
            return user_tel;
        }

        public void setUser_tel(String user_tel) {
            this.user_tel = user_tel;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }
    }
}
