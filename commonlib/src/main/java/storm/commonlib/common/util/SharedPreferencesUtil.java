package storm.commonlib.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.reflect.TypeToken;

import static android.content.Context.MODE_PRIVATE;
import static storm.commonlib.common.BaseApplication.getApplication;

public class SharedPreferencesUtil {

    private static final String PREFERENCES_LOG_NAME = "LOG_NAME";

    public SharedPreferencesUtil() {
    }

    private static Editor getEditor(Context applicationContext, String preferencesName) {
        SharedPreferences preferences = applicationContext.getSharedPreferences(preferencesName, MODE_PRIVATE);
        return preferences.edit();
    }

    private static SharedPreferences getPreferences(Context applicationContext, String preferencesName) {
        return applicationContext.getSharedPreferences(preferencesName, MODE_PRIVATE);
    }

    private static void saveObject(Context context, Object object) {
        String json = JsonUtil.convertObjectToJson(object);
        String key = getKeyFromClass(object.getClass());
        saveJsonInSharedPreferences(context, key, json);
    }

    private static void deleteAll() {
        SharedPreferences preferences = getPreferences(getApplication(), getLoginAccountId().toString());
        preferences.edit().clear().apply();
    }

    private synchronized static <T> T getObject(Context context, Class<T> clazz) {
        String json = getJsonFromSharedPreferences(context, getKeyFromClass(clazz));
        return JsonUtil.convertJsonToObject(json, TypeToken.get(clazz));
    }

    private static String getJsonFromSharedPreferences(Context context, String key) {
        SharedPreferences preferences = getPreferences(context, getLoginAccountId());
        return preferences.getString(key, StringUtil.EMPTY);
    }

    private static void saveJsonInSharedPreferences(Context context, String key, String json) {
        Editor editor = getEditor(context, getLoginAccountId());
        editor.putString(key, json);
        editor.commit();
    }

    private static String getKeyFromClass(Class<?> aClass) {
        return aClass.getName().toUpperCase();
    }

    public static String getLoginAccountId() {
        SharedPreferences preferences = getPreferences(getApplication(), PREFERENCES_LOG_NAME);
        return preferences.getString(PREFERENCES_LOG_NAME, "");
    }

    public static void saveLoginAccountId(String accountId) {
        Editor editor = getEditor(getApplication(), PREFERENCES_LOG_NAME);
        editor.putString(PREFERENCES_LOG_NAME, accountId);
        editor.commit();
    }

    public static String getToken() {
        return StringUtil.EMPTY;
    }
}