package storm.magicspace.bean;

import java.util.List;

/**
 * Created by xt on 16/6/28.
 */
public class IssueUCGContent {

    /**
     * userId :
     * nickName :
     * portraitImage :
     * contentId : 3465
     * createTime : 0000
     * duration :
     * url :
     * commentCount : 0
     * appreciateCount : 0
     * thumbImageUrl :
     * contentType : 2
     * isCollected : false
     * isAppreciated : false
     * description :
     * scenes : [{"sceneId":"42","order":"0","itemsCount":"0","timeLimit":"0","tips":"","bgimageUrl":"3384;userId=3970430042189702;url=;description=","items":""}]
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
    private String price;

    /**
     * sceneId : 42
     * order : 0
     * itemsCount : 0
     * timeLimit : 0
     * tips :
     * bgimageUrl : 3384;userId=3970430042189702;url=;description=
     * items :
     */

    private List<UGCScene> scenes;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<UGCScene> getScenes() {
        return scenes;
    }

    public void setScenes(List<UGCScene> scenes) {
        this.scenes = scenes;
    }

}
