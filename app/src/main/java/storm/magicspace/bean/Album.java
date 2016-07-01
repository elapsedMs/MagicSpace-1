package storm.magicspace.bean;

import java.io.Serializable;

/**
 * Created by gdq on 16/6/19.
 */
public class Album implements Serializable {


    /**
     * userId : 135601920027846561
     * nickName : J-lin
     * portraitImage : http://bbs.mojing.cn/uc_server/images/noavatar_big.gif
     * contentId : 3399
     * createTime : 1459922401451
     * duration : 0
     * url : http://share.moyan.mojing.cn/MEdata/file/135601920027846561/1459922399.jpg
     * commentCount : 0
     * appreciateCount : 0
     * thumbImageUrl : http://share.moyan.mojing.cn/MEdata/file/135601920027846561/1459922399_thumb.jpg
     * contentType : 2
     * isCollected : 0
     * isAppreciated : 0
     * description : e6bf80e9809f
     */

    private String userId;
    private String nickName;
    private String portraitImage;
    private String contentId;
    private String createTime;
    private String duration;
    private String url;
    private String commentCount;
    private String appreciateCount;
    private String thumbImageUrl;
    private String contentType;
    private String isCollected;
    private String isAppreciated;
    private String description;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPortraitImage() {
        return portraitImage;
    }

    public void setPortraitImage(String portraitImage) {
        this.portraitImage = portraitImage;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getAppreciateCount() {
        return appreciateCount;
    }

    public void setAppreciateCount(String appreciateCount) {
        this.appreciateCount = appreciateCount;
    }

    public String getThumbImageUrl() {
        return thumbImageUrl;
    }

    public void setThumbImageUrl(String thumbImageUrl) {
        this.thumbImageUrl = thumbImageUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(String isCollected) {
        this.isCollected = isCollected;
    }

    public String getIsAppreciated() {
        return isAppreciated;
    }

    public void setIsAppreciated(String isAppreciated) {
        this.isAppreciated = isAppreciated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

