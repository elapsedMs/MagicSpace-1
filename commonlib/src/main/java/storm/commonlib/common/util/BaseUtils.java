package storm.commonlib.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public class BaseUtils {
    private final static String TAG = BaseUtils.class.getName();

    /**
     * 复制属性值
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static <T> T copyProperties(Object source, T target) {
        try {
            PropertyUtils.copyProperties(target, source);
            return target;
        } catch (Exception e) {
            LogUtil.e(TAG, "copyProperties error", e);
            return null;
        }
    }

    /**
     * 获取MD5字符串
     *
     * @param context 加密内容
     * @return 返回加密的MD5（小写）
     */
    public static String getMD5(String context) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(context.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            LogUtil.e(TAG, "MD5 加密异常", e);
        } catch (UnsupportedEncodingException e) {
            LogUtil.e(TAG, "MD5 加密异常", e);
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString().toLowerCase();
        //16位加密，从第9位到25位
//        return md5StrBuff.substring(8, 24).toString().toUpperCase();
    }

    /**
     * 检查列表对象是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(Collection list) {
        if (list == null) return true;
        return list.isEmpty();
    }

    /**
     * 检查对象是否为空
     *
     * @param value 对象
     * @return 是否为空
     */
    public static boolean isEmpty(String value) {
        return value == null || value.trim().equals("");
    }

    /**
     * 获取日期格式化字符串
     *
     * @param date   日期对象
     * @param format 日期格式
     * @return 日期格式化字符串
     */
    public static String formatDate(Date date, String format) {
        if (date == null) return null;
        SimpleDateFormat theDate = new SimpleDateFormat(format);
        return theDate.format(date);
    }

    /**
     * 设置时间值
     *
     * @param date         时间
     * @param keyValueList Calendar类型（如：分钟、天等）和要修改值的列表
     * @return 设置之后的时间
     */
    public static Date setTime(Date date, int... keyValueList) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int count = keyValueList.length / 2;
        for (int i = 0; i < count; i++) {
            calendar.set(keyValueList[i * 2], keyValueList[i * 2 + 1]);
        }

        return calendar.getTime();
    }

    /**
     * 根据字符串（yyyy-MM-dd HH:mm:ss格式）获取日期对象
     *
     * @param dateString 日期格式字符串
     * @return 日期对象，若失败返回null
     */
    public static Date getLongDate(String dateString) {
        return getDate(dateString, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 根据字符串（yyyy-MM-dd格式）获取日期对象
     *
     * @param dateString 日期格式字符串
     * @return 日期对象，若失败返回null
     */

    public static Date getShortDate(String dateString) {
        return getDate(dateString, "yyyy-MM-dd");
    }

    /**
     * 根据字符串格式获取日期对象
     *
     * @param dateString 日期格式字符串
     * @param format     日期格式
     * @return 日期对象，若失败返回null
     */
    public static Date getDate(String dateString, String format) {
        if (isEmpty(dateString)) return null;
        try {
            SimpleDateFormat theDate = new SimpleDateFormat(format);
            return new Date(theDate.parse(dateString).getTime());
        } catch (Exception ex) {
//            Log.e(TAG, "时间转换失败，dateString="+dateString+",format="+format, ex);
        }
        return null;
    }

    /**
     * 获取设置的日期
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return
     */
    public static Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        return calendar.getTime();
    }

    /**
     * 显示时间差：X分钟（秒、小时、天、月）前
     *
     * @param showDate 显示的时间
     * @param currDate 当前时间
     * @return 时间差
     */
    public static String showTimeDifference(Date showDate, Date currDate) {
        if (showDate == null) return "";
        if (currDate == null) return "";

        String strTime = "";

        long lNowTime = currDate.getTime();
        long lShowTime = showDate.getTime();

        // 是否是当前日期之前的时间
        boolean isBefore = true;
        if (lNowTime < lShowTime) isBefore = false;

        long lDifferenceTime = Math.abs(lNowTime - lShowTime);

        if (lDifferenceTime < 60000) {
            // 一分钟内
            long time = lDifferenceTime / 1000;
            strTime = time + "秒钟";
        } else if (lDifferenceTime >= 60000 && lDifferenceTime < 3600000) {
            // 一小时内
            long time = lDifferenceTime / 60000;
            strTime = time + "分钟";
        } else if (lDifferenceTime >= 3600000 && lDifferenceTime < 86400000) {
            // 一天内
            long time = lDifferenceTime / 3600000;
            strTime = time + "小时";
        } else if (lDifferenceTime >= 86400000 && lDifferenceTime < 604800000) {
            // 一周内
            long time = lDifferenceTime / 86400000;
            strTime = time + "天";
        } else if (lDifferenceTime >= 604800000 && lDifferenceTime < 2419200000L) {
            // 一月内
            long time = lDifferenceTime / 604800000;
            strTime = time + "周";
        } else {
            // 超过一个月
            Calendar bigCalendar = Calendar.getInstance();
            Calendar smallCalendar = Calendar.getInstance();

            if (isBefore) {
                bigCalendar.setTime(currDate);
                smallCalendar.setTime(showDate);
            } else {
                bigCalendar.setTime(showDate);
                smallCalendar.setTime(currDate);
            }

            long year = Math.abs(bigCalendar.get(Calendar.YEAR) - smallCalendar.get(Calendar.YEAR));
            long month = bigCalendar.get(Calendar.MONTH) - smallCalendar.get(Calendar.MONTH);

            long showMonth = year * 12 + month;
            long showYear = showMonth / 12;
            showMonth = showMonth - showYear * 12;

            if (showYear > 0) strTime = showYear + "年";
            if (showMonth > 0) strTime += showMonth + "个月";
        }

        // 若显示时间在当前时间之后，则都显示为0秒
        if (!isBefore) return "0秒前";

        return strTime + (isBefore ? "前" : "后");
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static Date getCurrDate() {
        return getDate(new Date());
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static Date getDate(Date date) {
        return setTime(date,
                Calendar.HOUR_OF_DAY, 0,
                Calendar.MINUTE, 0,
                Calendar.SECOND, 0,
                Calendar.MILLISECOND, 0);
    }

    /**
     * 显示年龄（小于1个月，算1个月；1岁以下显示n个月）
     *
     * @param birthday 生日
     * @param currDate 当前时间
     * @return 年龄
     */
    public static String showAge(Date birthday, Date currDate) {
        if (birthday == null) return "--岁";
        if (currDate == null) return "--岁";

        String strTime = "";

        long lBirthdayTime = birthday.getTime();
        long lNowTime = currDate.getTime();

        // 是否是当前日期之前的时间
        if (lNowTime <= lBirthdayTime) return "--岁";

        // 超过一个月
        Calendar bigCalendar = Calendar.getInstance();
        Calendar smallCalendar = Calendar.getInstance();

        bigCalendar.setTime(currDate);
        smallCalendar.setTime(birthday);

        long year = Math.abs(bigCalendar.get(Calendar.YEAR) - smallCalendar.get(Calendar.YEAR));
        long month = bigCalendar.get(Calendar.MONTH) - smallCalendar.get(Calendar.MONTH);
        long day = bigCalendar.get(Calendar.DAY_OF_MONTH) - smallCalendar.get(Calendar.DAY_OF_MONTH);

        long showMonth = year * 12 + month;
        long showYear = showMonth / 12;
        showMonth = showMonth - showYear * 12;
        if (day > 0) showMonth++;

        if (showYear > 0) strTime = showYear + "岁";
        if (showMonth > 0 && showYear <= 0) strTime += showMonth + "个月";

        if (isEmpty(strTime)) strTime = "1个月";

        return strTime;
    }

    public static String getDatePeriodShow(Date start, Date end) {
        return getDatePeriodShow(start, end, "yyyy", "-", null, "至今");
    }

    /**
     * 获取时间段显示字符串
     *
     * @param start           开始时间
     * @param end             结束时间
     * @param format          时间显示格式
     * @param link            时间之间连接符
     * @param startNullString 开始时间为空时显示字符串
     * @param endNullString   结束时间为空时显示字符串
     * @return
     */
    public static String getDatePeriodShow(Date start, Date end, String format, String link, String startNullString, String endNullString) {
        if (start == null) return "";
        String result = formatDate(start, format);
        result += " " + link + " ";
        result += (end == null) ? endNullString : formatDate(end, format);
        return result;
    }

    /**
     * 格式化浮点数值
     *
     * @param value  数值
     * @param format 格式
     * @return 格式化字符串
     */
    public static String formatFloat(float value, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(value);
    }

    /**
     * 数值转换
     *
     * @param value 值
     * @return 数值，失败返回-1
     */
    public static int parseInt(String value) {
        if (isEmpty(value)) return -1;
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            LogUtil.w(TAG, "转换数值失败：" + value, e);
        }
        return -1;
    }

    /**
     * 获取缩略字符串
     *
     * @param str   字符串
     * @param count 显示的数量
     * @return 压缩后的字符串
     */
    public static String getThumbnailString(String str, int count) {
        if (isEmpty(str)) return "";

        if (str.length() <= count) return str;

        return str.substring(0, count) + "..";
    }

    /**
     * 获取字符串列表
     *
     * @param string    字符串
     * @param delimiter 分隔符
     * @return 字符串列表
     */
    public static List<String> stringToList(String string, String delimiter) {
        return stringToList(string, delimiter, false);
    }

    /**
     * 获取字符串列表
     *
     * @param string    字符串
     * @param delimiter 分隔符
     * @param trim      是否清除内容为空项
     * @return 字符串列表
     */
    public static List<String> stringToList(String string, String delimiter, boolean trim) {
        if (isEmpty(string)) return new ArrayList<String>();
        String[] array = string.split(delimiter);
        List<String> list;
        if (trim) {
            list = new ArrayList<String>();
            for (int i = 0; i < array.length; i++) {
                if (isEmpty(array[i])) continue;
                list.add(array[i]);
            }
        } else list = Arrays.asList(array);
        return list;
    }

    /**
     * 获取字符列表转字符串
     *
     * @param list      字符列表
     * @param delimiter 分隔符
     * @return 字符串
     */
    public static String listToString(List<String> list, String delimiter) {
        if (isEmpty(list)) return "";
        String string = "";
        for (String var : list) {
            string += var + delimiter;
        }
        return string.substring(0, string.length() - 1);
    }

}