package storm.magicspace.bean.httpBean;

import java.util.List;

/**
 * Created by xt on 16/6/26.
 */
public class EggImage {


    /**
     * typeId : 2
     * name : 家居
     * parentTypeId : 1
     * objects : [{"objectId":"","userId":"1","nickName":"官方","typeId":"2","type":"家居","url":"http://app.stemmind.com/vr/objs/09.png","price":"0","createTime":"1425025458785"},{"objectId":"","userId":"1","nickName":"官方","typeId":"2","type":"家居","url":"http://app.stemmind.com/vr/objs/08.png","price":"0","createTime":"0"},{"objectId":"","userId":"1","nickName":"官方","typeId":"2","type":"家居","url":"http://app.stemmind.com/vr/objs/07.png","price":"0","createTime":"0"},{"objectId":"","userId":"1","nickName":"官方","typeId":"2","type":"家居","url":"http://app.stemmind.com/vr/objs/06.png","price":"0","createTime":"0"},{"objectId":"","userId":"1","nickName":"官方","typeId":"2","type":"家居","url":"http://app.stemmind.com/vr/objs/05.png","price":"0","createTime":"0"}]
     */

    private String typeId;
    private String name;
    private String parentTypeId;
    private String imgurl;
    /**
     * objectId :
     * userId : 1
     * nickName : 官方
     * typeId : 2
     * type : 家居
     * url : http://app.stemmind.com/vr/objs/09.png
     * price : 0
     * createTime : 1425025458785
     */

    private List<ObjectsBean> objects;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentTypeId() {
        return parentTypeId;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setParentTypeId(String parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    public List<ObjectsBean> getObjects() {
        return objects;
    }

    public void setObjects(List<ObjectsBean> objects) {
        this.objects = objects;
    }

    public static class ObjectsBean {
        private String objectId;
        private String userId;
        private String nickName;
        private String typeId;
        private String type;
        private String url;
        private String price;
        private String createTime;

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
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

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
