package storm.magicspace.bean;

import java.io.Serializable;

/**
 * Created by pengyong on 2016/6/25.
 */
public class EggInfo implements Serializable {
    public String userId;
    public String nickName;
    public String portraitImage;
    public String contentId;
    public String createTime;
    public String duration;
    public String url;
    public String commentCount;
    public String appreciateCount;
    public String thumbImageUrl;
    public String contentType;
    public String isCollected;
    public String isAppreciated;
    public String description;



    public String playCount;
    public String avgtime;
    public String creditEarned;
    public String itemCount;
    public String title;
    public String myBestTime;

    //// TODO: 16/7/1 需要加一个字段 isAudit,my best time
}
