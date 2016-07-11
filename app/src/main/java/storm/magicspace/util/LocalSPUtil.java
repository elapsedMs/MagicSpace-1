package storm.magicspace.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.BaseApplication;
import storm.commonlib.common.util.JsonUtil;
import storm.commonlib.common.util.SharedPreferencesUtil;
import storm.magicspace.base.MagicApplication;
import storm.magicspace.bean.Album;
import storm.magicspace.bean.httpBean.InitResult;
import storm.magicspace.http.reponse.LoginResponse;

import static android.content.Context.MODE_PRIVATE;
import static storm.commonlib.common.BaseApplication.getApplication;

/**
 * Created by lixiaolu on 16/6/28.
 */
public class LocalSPUtil extends SharedPreferencesUtil {

    private static final String ALBUM = "ALBUM";
    private static final String FINISH_ALBUM = "FINISH_ALBUM";
    private static final String UNFINISH_ALBUM = "UNFINISH_ALBUM";
    public static final String TOKEN = "TOKEN";
    public static final String USER_NO = "USER_NO";
    public static final String GUIDE = "GUIDE";
    public static final String GAME = "GAME";

    public static void saveAccountInfo(LoginResponse.AccountInfo data) {
        saveObject(BaseApplication.getApplication(), data);
    }

    public static LoginResponse.AccountInfo getAccountInfo() {
        return getObject(BaseApplication.getApplication(), LoginResponse.AccountInfo.class);
    }

    public static void saveAlbum(List<Album> list) {
        SharedPreferences.Editor editor = getEditor(MagicApplication.getApplication(), ALBUM);
        String album = JsonUtil.toJson(list);
        editor.putString(ALBUM, album);
        editor.commit();
    }

    public static List<Album> getAlbum() {
        SharedPreferences sharedPreferences = MagicApplication.getApplication().getSharedPreferences(ALBUM, MODE_PRIVATE);
        return new Gson().fromJson(sharedPreferences.getString(ALBUM, ""), new TypeToken<List<Album>>() {
        }.getType());
    }


    public static void saveFinishAlbum(Context context, Album album) {
        Log.d("sss", "saveFinishAlbum");
        String albumString = "";
        SharedPreferences.Editor editor = getEditor(MagicApplication.getApplication(), ALBUM);
        List<Album> list = new ArrayList<>();
        WeakReference<List<Album>> weakReference = new WeakReference<>(list);
        weakReference.get().add(album);
        if (getFinishAlbum(context) != null) {
            List<Album> list1 = getFinishAlbum(context);
            list1.addAll(weakReference.get());
            albumString = JsonUtil.toJson(list1);
        } else {
            albumString = JsonUtil.toJson((weakReference.get()));
        }
        editor.putString(FINISH_ALBUM, albumString);
        editor.commit();
        weakReference.get().clear();
        weakReference.clear();
    }

    private static void saveFinishAlbum(List<Album> list) {
        SharedPreferences.Editor editor = getEditor(MagicApplication.getApplication(), ALBUM);
        WeakReference<List<Album>> weakReference = new WeakReference<>(list);
        String albumString = JsonUtil.toJson(weakReference.get());
        editor.putString(FINISH_ALBUM, albumString);
        editor.commit();
        weakReference.get().clear();
        weakReference.clear();
    }

    public static void deleteFinishAlbum(Context context, Album album) {
        Log.d("sss", "deleteFinishAlbum");
        List<Album> list = getFinishAlbum(context);
        list.remove(album);
        saveFinishAlbum(list);
    }

    public static List<Album> getFinishAlbum(Context context) {
        Log.d("sss", "getFinishAlbum");
        SharedPreferences sharedPreferences = context.getSharedPreferences(ALBUM, MODE_PRIVATE);
        if (JsonUtil.convertObjectListFromJson(sharedPreferences.getString(FINISH_ALBUM, ""), new TypeToken<List<Album>>() {
        }) == null)
            return new ArrayList<Album>();
        return JsonUtil.convertObjectListFromJson(sharedPreferences.getString(FINISH_ALBUM, ""), new TypeToken<List<Album>>() {
        });
    }


    public static void saveUnFinishAlbum(Context context, Album album) {
        Log.d("sss", "saveUnFinishAlbum");

        String albumString = "";
        SharedPreferences.Editor editor = getEditor(MagicApplication.getApplication(), ALBUM);
        List<Album> list = new ArrayList<>();
        WeakReference<List<Album>> weakReference = new WeakReference<>(list);
        weakReference.get().add(album);
        if (getFinishAlbum(context) != null) {
            List<Album> list1 = getUnFinishAlbum(context);
            list1.addAll(weakReference.get());
            albumString = JsonUtil.toJson(list1);
        } else {
            albumString = JsonUtil.toJson(weakReference.get());
        }
        editor.putString(UNFINISH_ALBUM, albumString);
        editor.commit();
        weakReference.get().clear();
        weakReference.clear();
    }

    private static void saveUnFinishAlbum(List<Album> list) {
        SharedPreferences.Editor editor = getEditor(MagicApplication.getApplication(), ALBUM);
        WeakReference<List<Album>> weakReference = new WeakReference<>(list);
        String albumString = JsonUtil.toJson(weakReference.get());
        editor.putString(UNFINISH_ALBUM, albumString);
        editor.commit();
        weakReference.get().clear();
        weakReference.clear();
    }

    public static void deleteUnFinishAlbum(Context context, String contentId) {
        Log.d("sss", "deleteUnFinishAlbum");
        List<Album> list = getUnFinishAlbum(context);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getContentId().equals(contentId)) {
                list.remove(i);
                break;
            }

        }
        saveUnFinishAlbum(list);
    }

    private static List<Album> getUnFinishAlbum(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ALBUM, MODE_PRIVATE);
        if (JsonUtil.convertObjectListFromJson(sharedPreferences.getString(UNFINISH_ALBUM, ""), new TypeToken<List<Album>>() {
        }) == null)
            return new ArrayList<Album>();
        List<Album> list = JsonUtil.convertObjectListFromJson(sharedPreferences.getString(UNFINISH_ALBUM, ""), new TypeToken<List<Album>>() {
        });
        return list;
    }

    public static Album getUnFinishAlbum(Context context, String contentId) {
        Log.d("sss", "getUnFinishAlbum");
        List<Album> list = getUnFinishAlbum(context);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getContentId().equals(contentId))
                return list.get(i);
        }
        return null;
    }

    public static void saveToken(String token) {
        SharedPreferences.Editor editor = getEditor(getApplication(), TOKEN);
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public static String getToken() {
        SharedPreferences preferences = getPreferences(getApplication(), TOKEN);
        return preferences.getString(TOKEN, "");
    }

    public static void saveGuide(boolean showed) {
        SharedPreferences.Editor editor = getEditor(getApplication(), GAME);
        editor.putBoolean(GUIDE, showed);
        editor.commit();
    }

    public static boolean getGuide() {
        SharedPreferences preferences = getPreferences(getApplication(), GAME);
        return preferences.getBoolean(GUIDE, false);
    }

    public static void saveUserNo(String userNo) {
        SharedPreferences.Editor editor = getEditor(getApplication(), TOKEN);
        editor.putString(USER_NO, userNo);
        editor.commit();
    }

    public static String getTUserNo() {
        SharedPreferences preferences = getPreferences(getApplication(), TOKEN);
        return preferences.getString(USER_NO, "");
    }

    public static void saveAppConfig(InitResult initResult) {
        saveObject(BaseApplication.getApplication(), initResult);
    }

    public static InitResult getAppConfig() {
        InitResult result = getObject(BaseApplication.getApplication(), InitResult.class);
        return result == null ? new InitResult() : result;
    }
}
