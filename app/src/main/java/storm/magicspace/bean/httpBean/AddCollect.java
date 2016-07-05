package storm.magicspace.bean.httpBean;

/**
 * Created by lixiaolu on 16/7/5.
 */
public class AddCollect {

    /**
     * slideId : 1
     * order : 0
     * title : 啥沙沙焦点图
     * hyberlink : http://share.moyan.mojing.cn/MEdata/file/3970430042189702/1463845159508_thumb.jpg
     * url : http://share.moyan.mojing.cn/MEdata/file/3970430042189702/1463845159508_thumb.jpg
     * thumbnailurl : http://share.moyan.mojing.cn/MEdata/file/3970430042189702/1463845159508_thumb.jpg
     * type : 1
     */

    private String slideId;
    private String order;
    private String title;
    private String hyberlink;
    private String url;
    private String thumbnailurl;
    private String type;

    public String getSlideId() {
        return slideId;
    }

    public void setSlideId(String slideId) {
        this.slideId = slideId;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHyberlink() {
        return hyberlink;
    }

    public void setHyberlink(String hyberlink) {
        this.hyberlink = hyberlink;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailurl() {
        return thumbnailurl;
    }

    public void setThumbnailurl(String thumbnailurl) {
        this.thumbnailurl = thumbnailurl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
