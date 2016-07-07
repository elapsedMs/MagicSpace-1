package storm.magicspace.http;

/**
 * Created by lixiaolu on 16/7/4.
 */
public class Conpon {

    /**
     * slideId : 1
     * order : 0
     * title : 啥沙沙焦点图
     * hyberlink : http://app.stemmind.com/vr/objs/25.png.apk
     * url : http://app.stemmind.com/vr/objs/25.png
     * thumbnailurl : http://app.stemmind.com/vr/objs/25.png
     * type : 1
     */

    private String couponId;
    private String order;
    private String title;
    private String hyberlink;
    private String url;
    private String thumbnailurl;
    private String type;
    private String price;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
