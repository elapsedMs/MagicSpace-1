package storm.commonlib.common.util;

import android.content.Context;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class StringUtil {
    private static final Pattern whiteSpacePattern = Pattern.compile("\\s+");
    private static final Pattern numberPattern = Pattern.compile("[0-9]*");
    public static final String EMPTY = "";


    public static boolean isEmpty(CharSequence str) {
        return TextUtils.isEmpty(str);
    }

    public static boolean isCode(String code) {
        return Pattern.matches("^\\d{4}$", code);
    }

    public static boolean isPhone(String phone) {
        return Pattern.matches("^(1)\\d{10}$", phone);
    }

    /**
     * 验证密码强度
     * 1.至少一个数字
     * 2.至少一个字母
     * 3.密码长度大于6位
     * 4.密码长度小于20位
     */
    public static boolean validateStrength(String password) {
        return Pattern.matches("^((?=.*[0-9])(?=.*[a-zA-Z]).{6,20})$", password);
    }

    public static String encodeString(String text) {
        if (isEmpty(text)) return null;
        try {
            return URLEncoder.encode(text, "utf-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 文本加密（密码）
     */
    public static String getMd5(String password) {
        java.security.MessageDigest messageDigest = null;
        try {
            messageDigest = java.security.MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(password.getBytes("UTF-8"));
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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
    }

    public static boolean isNotBlank(String text) {
        return text != null && !"".equalsIgnoreCase(text.trim());
    }

    public static boolean isBlank(String text) {
        return !isNotBlank(text);
    }

    public static boolean isNumber(String text) {
        return text != null && numberPattern.matcher(text).matches();
    }

    public static String removeAllWhiteSpace(String text) {
        return text == null ? null : whiteSpacePattern.matcher(text).replaceAll("");
    }

    public static String formatTimeToHHMMString(Date time) {
        return time == null ? null : new SimpleDateFormat("HH:mm").format(time);
    }

    public static String formatTemplateString(Context context, int templateId, Object... params) {
        String template = context.getResources().getText(templateId).toString();
        return format(template, params);
    }

    public static boolean isEqual(String text, String target) {
        return !isBlank(text) && text.equals(target);
    }


    public static String toStringHex(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");//UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

}