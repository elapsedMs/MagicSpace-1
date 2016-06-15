package storm.commonlib.common.http;

/**
 * 用户行为类型
 */
public enum ActionTypes {
    mt_app_start(1, "启动应用"),
    mt_app_exit(2, "关闭应用"),
    mt_app_active(3, "应用激活"),
    mt_app_inactive(4, "应用转后台"),
    mt_sign_in(5, "登录"),
    mt_sign_out(6, "退出"),

    mt_msg_friend(11, "通知点击-好友"),
    mt_msg_news(12, "通知点击-热闻"),
    mt_msg_other(13, "通知点击-其他"),

    mt_home(21, "医述"),
    mt_trend(22, "动态"),
    mt_connection(23, "人脉"),
    mt_discovery(24, "发现"),
    mt_mine(25, "我的"),

    mt_self(31, "留给自己"),
    mt_news_list(32, "热闻列表"),
    mt_news(33, "热闻"),
    mt_topic_list(34, "话题列表"),
    mt_topic(35, "话题"),

    mt_help_center(40, "去帮忙"),
    mt_help_create(41, "发帮助"),

    mt_help_details_center(42, "互助广场进详情"),
    mt_help_details_my_started(43, "我发起的进详情"),
    mt_help_details_my_helps(44, "我参与的进详情"),

    mt_help_details_trend_list(45, "动态列表进详情"),
    mt_help_details_trend_details(46, "动态详情进详情"),
    mt_help_details_msg_help(47, "帮帮忙消息进详情"),

    mt_end(-1, "结束");

    private int value;
    private String name;
    ActionTypes(int value, String name){
        this.value = value;
        this.name = name;
    }
    public int getValue() {
        return value;
    }
    public String getName() {
        return name;
    }
}
