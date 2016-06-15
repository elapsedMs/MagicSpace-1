package storm.commonlib.common.database.model;

import android.database.Cursor;

public interface ModelBuilder<T> {
    T buildModel(Cursor cursor);
}
