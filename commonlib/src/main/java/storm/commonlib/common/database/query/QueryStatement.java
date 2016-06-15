package storm.commonlib.common.database.query;

import org.roboguice.shaded.goole.common.base.Joiner;

public class QueryStatement extends SqlStatement {

    public static final String DESCENDING = "DESC";
    public static final String ASCENDING = "ASC";
    public static final String IN = "IN";

    protected QueryStatement(boolean isDistinct, CharSequence... columns) {
        super();

        builder
                .append(isDistinct ? "SELECT DISTINCT " : "SELECT ")
                .append(Joiner.on(',').join(columns));
    }

    public static QueryStatement select(CharSequence... columns) {
        return new QueryStatement(false, columns);
    }

    public static QueryStatement selectDistinct(CharSequence... columns) {
        return new QueryStatement(true, columns);
    }

    public QueryStatement from(CharSequence tableName) {
        builder
                .append(" FROM ")
                .append(tableName);

        return this;
    }

    public QueryStatement join(CharSequence tableName) {
        builder
                .append(" JOIN ")
                .append(tableName);

        return this;
    }

    public QueryStatement leftJoin(CharSequence tableName) {
        builder
                .append(" LEFT JOIN ")
                .append(tableName);

        return this;
    }

    public QueryStatement on(CharSequence leftHand, CharSequence rightHand) {
        builder
                .append(" ON ")
                .append(leftHand).append(" = ").append(rightHand);
        return this;
    }

    public QueryStatement where(CharSequence criteria) {
        builder
                .append(" WHERE ")
                .append("(")
                .append(criteria)
                .append(")");

        return this;
    }

    public QueryStatement or(CharSequence criteria) {
        builder
                .append(" OR ")
                .append("(")
                .append(criteria)
                .append(")");

        return this;
    }

    public QueryStatement and(CharSequence criteria) {
        builder
                .append(" AND ")
                .append("(")
                .append(criteria)
                .append(")");

        return this;
    }

    public QueryStatement groupBy(CharSequence column) {
        builder
                .append(" GROUP BY ")
                .append(column);

        return this;
    }

    public QueryStatement orderBy(CharSequence column, CharSequence orderType) {
        builder
                .append(" ORDER BY ")
                .append(column)
                .append(" ")
                .append(orderType);

        return this;
    }

    public QueryStatement limit(int numberOfResults) {
        builder
                .append(" LIMIT ")
                .append(numberOfResults);

        return this;
    }

    public SqlQuery toQuery() {
        String sql = builder.append(";").toString();

        return new SqlQuery(sql);
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
