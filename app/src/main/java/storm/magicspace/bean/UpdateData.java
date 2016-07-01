package storm.magicspace.bean;

/**
 * Created by xt on 16/6/30.
 */
public class UpdateData {

    /**
     * bgimageUrl : 3381
     * items : {"itemId":"1","itemMediaUrl":"http: //app.stemmind.com/vr/objs/32.png","x":"120","y":"20","scalex":"30","rotatex":"12","rotatey":"0","rotatez":"25","colortint":"0","transparency":"100","effert":"0","enabled":"1"}
     * itemsCount : 0
     * order : 0
     * sceneId : 82
     * timeLimit : 0
     * tips :
     */

    private String bgimageUrl;
    /**
     * itemId : 1
     * itemMediaUrl : http: //app.stemmind.com/vr/objs/32.png
     * x : 120
     * y : 20
     * scalex : 30
     * rotatex : 12
     * rotatey : 0
     * rotatez : 25
     * colortint : 0
     * transparency : 100
     * effert : 0
     * enabled : 1
     */

    private ItemsBean items;
    private int itemsCount;
    private int order;
    private String sceneId;
    private int timeLimit;
    private String tips;

    public String getBgimageUrl() {
        return bgimageUrl;
    }

    public void setBgimageUrl(String bgimageUrl) {
        this.bgimageUrl = bgimageUrl;
    }

    public ItemsBean getItems() {
        return items;
    }

    public void setItems(ItemsBean items) {
        this.items = items;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public static class ItemsBean {
        private String itemId;
        private String itemMediaUrl;
        private String x;
        private String y;
        private String scalex;
        private String rotatex;
        private String rotatey;
        private String rotatez;
        private String colortint;
        private String transparency;
        private String effert;
        private String enabled;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemMediaUrl() {
            return itemMediaUrl;
        }

        public void setItemMediaUrl(String itemMediaUrl) {
            this.itemMediaUrl = itemMediaUrl;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getScalex() {
            return scalex;
        }

        public void setScalex(String scalex) {
            this.scalex = scalex;
        }

        public String getRotatex() {
            return rotatex;
        }

        public void setRotatex(String rotatex) {
            this.rotatex = rotatex;
        }

        public String getRotatey() {
            return rotatey;
        }

        public void setRotatey(String rotatey) {
            this.rotatey = rotatey;
        }

        public String getRotatez() {
            return rotatez;
        }

        public void setRotatez(String rotatez) {
            this.rotatez = rotatez;
        }

        public String getColortint() {
            return colortint;
        }

        public void setColortint(String colortint) {
            this.colortint = colortint;
        }

        public String getTransparency() {
            return transparency;
        }

        public void setTransparency(String transparency) {
            this.transparency = transparency;
        }

        public String getEffert() {
            return effert;
        }

        public void setEffert(String effert) {
            this.effert = effert;
        }

        public String getEnabled() {
            return enabled;
        }

        public void setEnabled(String enabled) {
            this.enabled = enabled;
        }
    }
}
