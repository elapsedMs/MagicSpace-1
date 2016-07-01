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
    /**
     * sceneId : 42
     * order : 0
     * itemsCount : 0
     * timeLimit : 0
     * tips :
     * bgimageUrl : 3384;userId=3970430042189702;url=;description=
     * items :
     */

    private List<ScenesBean> scenes;

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

    public List<ScenesBean> getScenes() {
        return scenes;
    }

    public void setScenes(List<ScenesBean> scenes) {
        this.scenes = scenes;
    }

    public static class ScenesBean {
        private String sceneId;
        private String order;
        private String itemsCount;
        private String timeLimit;
        private String tips;
        private String bgimageUrl;
        private String items;

        public String getSceneId() {
            return sceneId;
        }

        public void setSceneId(String sceneId) {
            this.sceneId = sceneId;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getItemsCount() {
            return itemsCount;
        }

        public void setItemsCount(String itemsCount) {
            this.itemsCount = itemsCount;
        }

        public String getTimeLimit() {
            return timeLimit;
        }

        public void setTimeLimit(String timeLimit) {
            this.timeLimit = timeLimit;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getBgimageUrl() {
            return bgimageUrl;
        }

        public void setBgimageUrl(String bgimageUrl) {
            this.bgimageUrl = bgimageUrl;
        }

        public String getItems() {
            return items;
        }

        public void setItems(String items) {
            this.items = items;
        }
    }
}
