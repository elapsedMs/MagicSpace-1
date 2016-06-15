package storm.commonlib.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static com.google.gson.Gson mGson = null;

    public static void overrideGson(com.google.gson.Gson gson) {
        mGson = gson;
    }

    public static com.google.gson.Gson gson() {
        if (mGson == null) {
            overrideGson(new com.google.gson.GsonBuilder().create());
        }
        return mGson;
    }

    public static String toJson(Object o) {
        return JsonUtil.gson().toJson(o);
    }

    public static <T> T fromJson(String json, Class<T> cls) {
        return JsonUtil.gson().fromJson(json, cls);
    }


    private static Gson getGsonContext() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(YYYY_MM_DD_HH_MM_SS);
        return gsonBuilder.create();
    }

    public static <T> String convertObjectToJson(T objects) {
        return getGsonContext().toJson(objects);
    }

    public static <T> T convertJsonToObject(String expected, TypeToken token) {
        return getGsonContext().fromJson(expected, token.getType());
    }

    public static <T> List<T> convertObjectListFromJson(String json, TypeToken token) {
        List<T> list = new ArrayList<T>();

        if (StringUtil.isBlank(json)) {
            return list;
        }

        try {
            list = getGsonContext().fromJson(json, token.getType());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

}