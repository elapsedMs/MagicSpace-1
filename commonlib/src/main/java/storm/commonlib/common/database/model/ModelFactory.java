package storm.commonlib.common.database.model;

import android.content.ContentValues;

public interface ModelFactory<T> extends ModelBuilder<T> {
    ContentValues extractFromModel(T model);
}
