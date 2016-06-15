package storm.commonlib.common.http;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.Date;

import storm.commonlib.common.util.BaseUtils;
import storm.commonlib.common.util.StringUtil;

import static storm.commonlib.common.http.ServiceUtils.handleException;


public class JsonProvider {
    private final static String TAG = JsonProvider.class.getName();

    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
//                    .serializeNulls()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                    .registerTypeAdapter(Timestamp.class, new JsonSerializer<Timestamp>() {
//                        @Override
//                        public JsonElement serialize(Timestamp date, Type type, JsonSerializationContext jsonSerializationContext) {
//                            return new JsonPrimitive(String.valueOf(new Timestamp(123)));
//                        }//Timestamp时间格式需要带时分秒
//                    })
//                    .registerTypeAdapter(Timestamp.class, new JsonDeserializer<Timestamp>(){
//                        @Override
//                        public Timestamp deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//                            String value = jsonElement.getAsString();
//                            Date date = BaseUtil.getLongDate(value);
//                            if (date!=null) return date;
//                            return null;
//                        }
//                    })
                    .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                        @Override
                        public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                            String value = jsonElement.getAsString();
                            // yyyy-MM-dd HH:mm:ss
                            Date date = BaseUtils.getLongDate(value);
                            if (date != null) return date;
                            // yyyy-MM-dd
                            date = BaseUtils.getShortDate(value);
                            if (date != null) return date;
                            // // yyyy-MM-dd HH:mm
                            date = BaseUtils.getDate(value, "yyyy-MM-dd HH:mm");
                            if (date != null) return date;
                            return null;
                        }
                    })
//                    .registerTypeAdapter(RelationTypes.class, new JsonDeserializer<RelationTypes>(){
//                        @Override
//                        public RelationTypes deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//                            int value = jsonElement.getAsInt();
//                            return RelationTypes.parse(value);
//                        }
//                    })
                    .create();
        }
        return gson;
    }

    public static String toJson(Object obj) {
        return getGson().toJson(obj);
    }

    public static <T> T toObject(String json, Class<T> tClass) {
        try {
            return getGson().fromJson(json, tClass);
        } catch (Exception e) {
            handleException(StringUtil.EMPTY, e, true);
        }
        return null;
    }

    public static <T> T toObject(String json, Type type) {
        if (BaseUtils.isEmpty(json)) return null;
        try {
            return getGson().fromJson(json, type);
        } catch (JsonSyntaxException e) {
            handleException(StringUtil.EMPTY, e, true);
        }
        return null;
    }

    public static String toJson(Object obj, String... excludefileds) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .setExclusionStrategies(new JsonKit(excludefileds))
                .create();
        return gson.toJson(obj);
    }

    private static class JsonKit implements ExclusionStrategy {
        String[] keys;

        public JsonKit(String[] keys) {
            this.keys = keys;
        }

        @Override
        public boolean shouldSkipClass(Class<?> arg0) {
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes arg0) {
            for (String key : keys) {
                if (key.equals(arg0.getName())) {
                    return true;
                }
            }
            return false;
        }
    }
}