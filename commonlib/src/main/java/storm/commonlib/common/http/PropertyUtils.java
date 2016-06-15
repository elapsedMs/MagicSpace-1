package storm.commonlib.common.http;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PropertyUtils {

    private static final String TAG = "";

    public static void copyProperties(Object dist, Object orig) {
//
//        List<Field> fields_dist = getFields(dist);
//
//        try {
//            for (Field field : fields_orig) {
//                String fieldNameOrig = field.getName();
//
//                Method getMethod = getDeclaredMethod(orig, addGetString(field.getName(), field.getType()), null);
//
//                if (getMethod == null) continue;
//
//                Object obj = getMethod.invoke(orig, null);
//                for (Field field2 : fields_dist) {
//                    String fieldNameDist = field2.getName();
//                    if (fieldNameOrig.equals(fieldNameDist)) {
//                        Method setMethod = getDeclaredMethod(dist, addSetString(field.getName()), field2.getType());
//                        if (setMethod == null) break;
//                        setMethod.invoke(dist, obj);
//                        break;
//                    }
//                }
//
//            }
//        } catch (Exception e) {
//            LogUtil.e(TAG, "copyProperties error", e);
//        }
    }

    private static List<Field> getFields(Object obj) {
        List<Field> fields_list = new ArrayList<Field>();
        if (obj == null) return null;
        Class tClass = obj.getClass();

        // 获取所有父类属性
        Field[] fields = tClass.getDeclaredFields();
        while (fields.length > 0) {
            addArray2List(fields_list, fields);
            if (tClass == null)
                break;
            tClass = tClass.getSuperclass();
            if (tClass != null)
                fields = tClass.getDeclaredFields();
        }
        return fields_list;
    }

    private static void addArray2List(List<Field> list, Field[] array) {
        if (array == null || array.length <= 0) return;
        if (list == null) list = new ArrayList<Field>();
        for (Field field : array) {
            list.add(field);
        }
    }

    private static String addGetString(String fieldName, Class<?> type) {
        StringBuffer sb = new StringBuffer();
        if (type == boolean.class || type == Boolean.class) {
            sb.append("is");
        } else {
            sb.append("get");
        }
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        return sb.toString();
    }

    private static String addSetString(String fieldName) {
        StringBuffer sb = new StringBuffer();
        sb.append("set");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        return sb.toString();
    }

    private static Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
                // Log.e(TAG, "getDeclaredMethod error", e);
            }
        }
        return null;
    }

}