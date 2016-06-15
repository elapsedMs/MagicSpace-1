package storm.commonlib.common.database.query;


import static storm.commonlib.common.database.utils.SqlHelper.toSqlValue;

public class SqlExpression extends SqlSnippet {
    public static final String LIKE = "LIKE";
    public static final String QUERY_ARG = "?";
    public static final String STRING_QUERY_ARG = "'" + "?" + "'";

    protected SqlExpression(CharSequence content) {
        super(content);
    }

    protected SqlExpression(String format, Object... args) {
        super(format, args);
    }

    public SqlExpression and(CharSequence another) {
        builder
                .append(" AND (")
                .append(another)
                .append(")");
        return this;
    }

    public SqlExpression or(CharSequence another) {
        builder
                .append(" OR (")
                .append(another)
                .append(")");
        return this;
    }

    public static SqlExpression expression(CharSequence expression) {
        return new SqlExpression("(%s)", expression);
    }

    public static SqlExpression expression(CharSequence leftHand, String operator, CharSequence rightHand) {
        return new SqlExpression("(%s %s %s)", leftHand, operator, rightHand);
    }

    public static SqlExpression expression(CharSequence leftHand, String operator, Object rightHand) {
        return expression(leftHand, operator, toSqlValue(rightHand));
    }

    public static SqlExpression isNull(CharSequence leftHand) {
        return expression(leftHand, "IS", "NULL");
    }

    public static SqlExpression isNotNull(CharSequence leftHand) {
        return expression(leftHand, "IS NOT ", "NULL");
    }

    public static SqlExpression equal(CharSequence leftHand, CharSequence rightHand) {
        return expression(leftHand, "=", rightHand);
    }

    public static SqlExpression equal(CharSequence leftHand, Object rightHand) {
        return equal(leftHand, toSqlValue(rightHand));
    }

    public static SqlExpression notEqual(CharSequence leftHand, CharSequence rightHand) {
        return expression(leftHand, "<>", rightHand);
    }

    public static SqlExpression notEqual(CharSequence leftHand, Object rightHand) {
        return notEqual(leftHand, toSqlValue(rightHand));
    }
}
