package storm.commonlib.common.util;

import org.joda.time.DateTime;

import java.util.Date;

public class TimeUtil {
    public static final long ONE_MINUTE = 1000 * 60;

    private static long offsetInMillis = 0;

    private TimeUtil() {
    }

    public static DateTime dateFormat(long time) {
        return new DateTime(time);
    }

    public static void setReferenceTime(DateTime dateTime) {
        setReferenceTime(dateTime.getMillis());
    }

    private static void setReferenceTime(long millis) {
        offsetInMillis = internalNow().getMillis() - millis;
    }

    public static DateTime now() {
        return adjustTime(internalNow());
    }

    public static Date adjustTime(Date time) {
        return adjustTime(new DateTime(time)).toDate();
    }

    public static DateTime adjustTime(DateTime time) {
        return minusMillis(time, offsetInMillis);
    }

    // An fix to DateTime's API, allow pass argument as long
    public static DateTime minusMillis(DateTime dateTime, long millis) {
        if (millis == 0)
            return dateTime;

        long instant = dateTime.getChronology().millis().subtract(dateTime.getMillis(), millis);
        return dateTime.withMillis(instant);
    }

    private static DateTime internalNow() {
        return new DateTime();
    }

    public static void resetTime() {
        offsetInMillis = 0;
    }

    public static String formatToYMD(DateTime dateTime) {
        return dateTime.toString("YYYY-MM-dd");
    }

    public static DateTime today() {
        return now().withTimeAtStartOfDay();
    }

    public static DateTime yesterday() {
        return today().minusDays(1);
    }

    public static DateTime tomorrow() {
        return today().plusDays(1);
    }

    public static DateTime thisMonth() {
        return today().withDayOfMonth(1);
    }

    public static String getYmdOfDate(Date date) {
        return new DateTime(date).toString("yyyy-MM-dd");
    }

    public static String getHmOfDate(Date date) {
        return new DateTime(date).toString("HH:mm");
    }

    public static String getYMdHmsOfDate(DateTime referenceTime) {
        return referenceTime.toString("yyyy-MM-dd HH:mm:ss");
    }

    public static String getYMdHmOfDate(DateTime referenceTime) {
        return referenceTime.toString("yyyy-MM-dd HH:mm");
    }

    public static String getMonthAndDayOfDate(DateTime referenceTime) {
        return referenceTime.toString("M.d");
    }

    public static DateTime getThreeMonthAgoOfDate() {
        return TimeUtil.today().minusMonths(3);
    }

    public static String getHmsOfDate(DateTime dateTime) {
        return dateTime.toString("HH:mm:ss");
    }

    public static String getExistTime(long createdTime) {
        long time = System.currentTimeMillis() - createdTime * 1000;
        int seconds = (int) (time / 1000);
        int minutes = seconds / 60;
        int hours = minutes / 60;
        int days = hours / 24;
        if (seconds > 60) {
            if (minutes > 60) {
                if (hours > 24) {
                    if (days > 3) {
                        return getYmdOfDate(new Date(createdTime * 1000));
                    } else {
                        return days + "天前";
                    }
                } else {
                    return hours + "小时前";
                }
            } else {
                return minutes + "分钟前";
            }
        } else {
            return "刚才";
        }
    }

    public static boolean shouldShowTime(long startTime, long endTime) {
        return endTime - startTime >= 5 * 1000;
    }
}