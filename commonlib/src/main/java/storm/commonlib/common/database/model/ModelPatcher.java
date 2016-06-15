package storm.commonlib.common.database.model;

import android.database.Cursor;

public interface ModelPatcher<T> {
    void patchObject(Cursor cursor, T model);
}