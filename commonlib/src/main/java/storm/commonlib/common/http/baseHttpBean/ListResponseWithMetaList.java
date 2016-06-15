package storm.commonlib.common.http.baseHttpBean;

public class ListResponseWithMetaList<T, M> extends ListResponse<T> {
    public M meta;

    public M getMeta() {
        return meta;
    }

    public void setMeta(M meta) {
        this.meta = meta;
    }


}
