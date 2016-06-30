package storm.commonlib.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基础方法提供类
 */
public class BaseUtil {
    private final static String TAG = BaseUtil.class.getName();
    public static final String UTF_8 = "utf-8";

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

        for (byte aByteArray : byteArray) {
            if (Integer.toHexString(0xFF & aByteArray).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & aByteArray));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & aByteArray));
        }
        return md5StrBuff.toString().toLowerCase();
        //16位加密，从第9位到25位
//        return md5StrBuff.substring(8, 24).toString().toUpperCase();
    }

    /**
     * 将数值进行URL编码
     *
     * @param obj 编码对象
     * @return 编码值
     */
    public static String getURLEncode(Object obj) {
        if (obj == null) return null;
        String value = null;
        try {
            value = URLEncoder.encode(obj.toString(), UTF_8).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            LogUtil.e(TAG, "getURLEncode error", e);
        }
        return value;
    }


    /**
     * account 相关Md5
     *
     * @param
     * @return
     */
    public final static String MD5(String s) {
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < md.length; i++) {
                int val = ((int) md[i]) & 0xff;
                if (val < 16)
                    sb.append("0");
                sb.append(Integer.toHexString(val));

            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 检查列表对象是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(Collection list) {
        return list == null || list.isEmpty();
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
     * 检查是否以冒号结尾，如果是则去掉
     *
     * @param str 检查的字符串
     * @return 检查后的字符串
     */
    public static String checkEndColon(String str) {
        return checkEndChar(str, "：");
    }

    /**
     * 检查字符串是否有冒号结尾，如果是则去掉
     *
     * @param str     字符串
     * @param endChar 检查的结束字符
     * @return 检查后的字符串
     */
    public static String checkEndChar(String str, String endChar) {
        if (isEmpty(str)) return "";
        if (str.endsWith(endChar)) return str.substring(0, str.length() - 1);
        return str;
    }

    /**
     * 获取日期格式化字符串（yyyy-MM-dd HH:mm:ss）
     *
     * @param date 日期对象
     * @return 日期格式化字符串
     */
    public static String formatLongDate(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取日期格式化字符串（MM-dd HH:mm）
     *
     * @param date 日期对象
     * @return 日期格式化字符串
     */
    public static String formatShortDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 获取日期格式化字符串（HH:mm）
     *
     * @param date 日期对象
     * @return 日期格式化字符串
     */
    public static String formatHour(Date date) {
        return formatDate(date, "HH:mm");
    }

    /**
     * 获取日期的中文格式化字符串（yyyy年M月d日）
     *
     * @param date 日期对象
     * @return 日期格式化字符串
     */
    public static String formatChineseDate(Date date) {
        return formatDate(date, "yyyy年M月d日");
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
     * 计算时间偏移量
     *
     * @param startDate    开始时间
     * @param calendarType 偏移的Calendar类型（如：分钟、天等）
     * @param offset       偏移量
     * @return 偏移后的时间
     */
    public static Date computingTime(Date startDate, int calendarType, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(calendarType, offset);
        return calendar.getTime();
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
     * @return 时间差
     */
    public static String showTimeDifference(Date showDate) {
        return showTimeDifference(showDate, new Date());
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
     * @return 年龄
     */
    public static String showAge(Date birthday) {
        return showAge(birthday, getCurrDate());
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

    /**
     * 获取时间段
     *
     * @param hour 当前小时数
     * @return 时间段文本
     */
    public static String getHourPeriod(int hour) {
        if (hour >= 24) hour -= 24;
        return hour + ":00~" + (hour == 23 ? 0 : hour + 1) + ":00";
    }

    /**
     * 显示个人经历的时间
     *
     * @param time 时间字符串
     * @return
     */
    public static String getExperienceTimeShow(Date time) {
        return time == null ? "至今" : formatDate(time, "yyyy.MM");
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
     * 格式化浮点数值（0.##，保留两位小数）
     *
     * @param value 数值
     * @return 格式化字符串
     */
    public static String formatFloat(float value) {
        return formatFloat(value, "0.##");
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
     * 格式化电话号码
     *
     * @param phoneNumber 电话号码
     * @return 格式化之后的电话号码
     */
    public static String formatPhone(String phoneNumber) {
        if (isEmpty(phoneNumber)) return "";
        phoneNumber = phoneNumber.replace("(", "");
        phoneNumber = phoneNumber.replace(")", "-");
        phoneNumber = phoneNumber.replace("+", "");

        // 去除国家
        String[] replaceList = new String[]{"86-", "86"};
        for (String replaceStr : replaceList) {
            if (phoneNumber.startsWith(replaceStr)) {
                phoneNumber = phoneNumber.substring(replaceStr.length());
                break;
            }
        }

        // 判断三位区号
        boolean isFormated = false;
        String[] phoneStartList = new String[]{"010", "020", "021", "022", "023", "024", "025", "027", "028", "029", "852", "853"};

        for (String phoneStart : phoneStartList) {
            if (phoneNumber.startsWith(phoneStart)) {
                phoneNumber = phoneStart + "-" + phoneNumber.substring(phoneStart.length());
                isFormated = true;
                break;
            }
        }

        if (!isFormated) {
            // 四位区号
            if (phoneNumber.startsWith("0")) {
                phoneNumber = phoneNumber.substring(0, 4) + "-" + phoneNumber.substring(4);
            }
        }

        phoneNumber = phoneNumber.replace("--", "-");

        return phoneNumber;
    }

    /**
     * 数值转换
     *
     * @param value 值
     * @return 数值，失败返回-1
     */
    public static float parseFloat(String value) {
        if (isEmpty(value)) return -1;
        try {
            return Float.parseFloat(value);
        } catch (Exception e) {
            LogUtil.w(TAG, "转换数值失败：" + value, e);
        }
        return -1;
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
     * 验证邮箱地址是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        boolean flag;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return [0-9]{5,9}
     */
    public static boolean isMobile(String mobiles) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^(1)\\d{10}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证是否数字
     *
     * @param number
     * @return
     */
    public static boolean isNum(String number) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(number);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 获取缩略的邮箱显示
     *
     * @param mail  邮箱
     * @param count 显示字母数量
     * @return 邮箱缩略字符串
     */
    public static String getThumbnailMail(String mail, int count) {
        if (isEmpty(mail)) return "";
        int atIndex = mail.indexOf("@");

        if (atIndex < 0) {
            return getThumbnailString(mail, count);
        }

        String startStr = mail.substring(0, atIndex);
        String endStr = mail.substring(atIndex + 1, mail.length());

        return getThumbnailString(startStr, count) + "@" + getThumbnailString(endStr, count);
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
     * 获取字符列表转字符串(分号分隔)
     *
     * @param list 字符列表
     * @return 字符串
     */
    public static String listToString(List<String> list) {
        return listToString(list, ";");
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

    /**
     * 显示2行文字
     *
     * @param text 文字
     * @param max  单行最大数量
     * @return 多行文本
     */
    public static String show2LineText(String text, int max) {
        if (isEmpty(text)) return "";
        if (text.length() <= max) return text;

        int end = (text.length() + 1) / 2;
        text = text.substring(0, end) + "\n" + text.substring(end);
        return text;
    }

    public static boolean checkPassword(String password) {
        return Pattern.matches("^((?=.*[0-9])(?=.*[a-zA-Z]).{6,20})$", password);
    }

    public static float decimaFormatFloat(String bit, Float bmi) {
        return Float.parseFloat(new DecimalFormat(bit).format(bmi));
    }
}