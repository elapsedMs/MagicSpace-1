package storm.magicspace.bean;

import java.util.List;

/**
 * Created by xt on 16/7/4.
 */
public class UGCScene {
    private String sceneId;
    private String order;
    private String itemsCount;
    private String timeLimit;
    private String tips;
    private String bgimageUrl;
    private List<UGCItem> items;

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

    public List<UGCItem> getItems() {
        return items;
    }

    public void setItems(List<UGCItem> items) {
        this.items = items;
    }
}
