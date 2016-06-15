package storm.commonlib.common.database.query;//package com.medtree.client.util.database.query;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.roboguice.shaded.goole.common.base.Function;

import java.util.List;

import static java.util.Arrays.asList;
import static org.roboguice.shaded.goole.common.collect.Iterables.toArray;
import static org.roboguice.shaded.goole.common.collect.Iterables.transform;
import static storm.commonlib.common.database.utils.SqlHelper.toQueryValue;

public class SqlQuery {
    private final String sql;

    public SqlQuery(String sql) {
        this.sql = sql;
    }

    public Cursor execute(SQLiteDatabase database, String... args) {
        return database.rawQuery(sql, args);
    }

    public Cursor execute(SQLiteDatabase database, Object... args) {
        List<Object> objects = asList(args);

        Iterable<String> argStrings = transform(objects, new Function<Object, String>() {
            @Override
            public String apply(Object obj) {
                return toQueryValue(obj);
            }
        });

        return database.rawQuery(sql, toArray(argStrings, String.class));
    }

    @Override
    public String toString() {
        return sql;
    }
}
