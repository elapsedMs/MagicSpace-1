package storm.commonlib.common.database.utils;//package com.medtree.client.util.database.utils;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.joda.time.DateTime;
import org.roboguice.shaded.goole.common.base.Joiner;
import org.roboguice.shaded.goole.common.base.Predicate;
import org.roboguice.shaded.goole.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;

public class SqlHelper {
    public static final String TYPE_ID = "INTEGER PRIMARY KEY";
    public static final String TYPE_TEXT_ID = "TEXT PRIMARY KEY";
    public static final String TYPE_FOREIGN_KEY = "INTEGER NOT NULL";
    public static final String TYPE_ENUMERATION = "INTEGER NOT NULL";
    public static final String TYPE_DATETIME = "INTEGER NOT NULL";
    public static final String TYPE_INTEGER = "INTEGER";
    public static final String TYPE_REAL = "REAL";
    public static final String TYPE_TEXT = "TEXT";
    public static final String TYPE_TEXT_LIMIT_NOT_NULL = "TEXT NOT NULL";
    public static final String LIMIT_PRIMARY_KEY = "PRIMARY KEY";
    public static final String LIMIT_DEFAULT_NULL = "DEFAULT NULL";
    public static final String LIMIT_NOT_NULL = "NOT NULL";
    public static final String LIMIT_DEFAULT_0_NOT_NULL = "DEFAULT 0 NOT NULL";
    public static final String TYPE_BOOL = "BOOL";

    public static final String LOGGER_TAG = "SQL";

    public static final List<ValueTranslator> TRANSLATORS = new ArrayList<ValueTranslator>();

    public static final String NULL = "NULL";

    static {
        TRANSLATORS.add(new ValueTranslator() {
            @Override
            public boolean isApplicable(Class type, Object value) {
                return value == null;
            }

            @Override
            public String translateValue(Object value) {
                return NULL;
            }
        });

        TRANSLATORS.add(new StringValueTranslator<String>() {
            @Override
            public boolean isApplicable(Class type, Object value) {
                return CharSequence.class.isAssignableFrom(type);
            }

            @Override
            public String translateValue(String value) {
                return value;
            }
        });

        TRANSLATORS.add(new ValueTranslator<Persistable>() {
            @Override
            public boolean isApplicable(Class type, Object value) {
                return Persistable.class.isAssignableFrom(type);
            }

            @Override
            public String translateValue(Persistable value) {
                return value.getPersistableValue();
            }
        });

        TRANSLATORS.add(new ValueTranslator<Enum>() {
            @Override
            public boolean isApplicable(Class type, Object value) {
                return Enum.class.isAssignableFrom(type);
            }

            @Override
            public Integer translateValue(Enum value) {
                return value.ordinal();
            }
        });

        TRANSLATORS.add(new ValueTranslator<DateTime>() {
            @Override
            public boolean isApplicable(Class type, Object value) {
                return DateTime.class.isAssignableFrom(type);
            }

            @Override
            public Long translateValue(DateTime value) {
                return value.getMillis();
            }
        });

        TRANSLATORS.add(new ValueTranslator<Date>() {
            @Override
            public boolean isApplicable(Class type, Object value) {
                return Date.class.isAssignableFrom(type);
            }

            @Override
            public Long translateValue(Date value) {
                return value.getTime();
            }
        });

        TRANSLATORS.add(new ValueTranslator() {
            @Override
            public boolean isApplicable(Class type, Object value) {
                return true;
            }

            @Override
            public Object translateValue(Object value) {
                return value;
            }
        });
    }

    public static void createTable(SQLiteDatabase database, String tableName, String... columns) {
        Joiner joiner = Joiner.on(',');

        String columnsDefinition = joiner.join(columns);
        String sql = format("CREATE TABLE %s ( %s );", tableName, columnsDefinition);

        Log.d(LOGGER_TAG, sql);
        database.execSQL(sql);
    }

    public static String columnDef(String columnName, String columnDeclaration) {
        return format("%s %s", columnName, columnDeclaration);
    }

    public static String columnDef(String... columnDeclarations) {
        return Joiner.on(' ').join(columnDeclarations);
    }

    public static void insertIntoTable(SQLiteDatabase database, String tableName, Object... values) {
        String insertSql = "INSERT INTO %s VALUES (%s);";

        List<String> valueStrings = new ArrayList<String>();

        for (Object value : values) {
            String stringValue = toSqlValue(value);
            valueStrings.add(stringValue);
        }

        String valuesSql = Joiner.on(',').join(valueStrings);

        String sql = format(insertSql, tableName, valuesSql);

        database.execSQL(sql);
    }

    private static ValueTranslator findValueTranslator(final Object value) {
        final Class type = value == null ? null : value.getClass();

        return Iterables.find(TRANSLATORS, new Predicate<ValueTranslator>() {
            @Override
            public boolean apply(ValueTranslator valueTranslator) {
                return valueTranslator.isApplicable(type, value);
            }
        });
    }

    public static String toQueryValue(Object value) {
        return findValueTranslator(value).toQueryValue(value);
    }

    public static String toSqlValue(Object value) {
        return findValueTranslator(value).toSqlValue(value);
    }

    public abstract static class ValueTranslator<T> {
        public abstract boolean isApplicable(Class type, Object value);

        protected abstract Object translateValue(T value);

        public String toQueryValue(Object value) {
            //noinspection unchecked
            return translateValue((T) value).toString();
        }

        public String toSqlValue(Object value) {
            return toQueryValue(value);
        }
    }

    public abstract static class StringValueTranslator<T> extends ValueTranslator<T> {
        @Override
        public String toSqlValue(Object value) {
            return "\"" + toQueryValue(value) + "\"";
        }
    }
}
