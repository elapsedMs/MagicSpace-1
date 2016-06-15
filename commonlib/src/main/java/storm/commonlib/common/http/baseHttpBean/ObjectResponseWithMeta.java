package storm.commonlib.common.http.baseHttpBean;

public class ObjectResponseWithMeta<T, M> extends ObjectResponse<T> {
    public M meta;
    public M getMeta() {
        return meta;
    }
    public void setMeta(M meta) {
        this.meta = meta;
    }
}
