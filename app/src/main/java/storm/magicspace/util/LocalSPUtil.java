package storm.magicspace.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.BaseApplication;
import storm.commonlib.common.util.SharedPreferencesUtil;
import storm.magicspace.base.MagicApplication;
import storm.magicspace.bean.Album;
import storm.magicspace.http.reponse.LoginResponse;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by lixiaolu on 16/6/28.
 */
public class LocalSPUtil extends SharedPreferencesUtil {

    private static final String ALBUM = "ALBUM";
    private static final String FINISH_ALBUM = "FINISH_ALBUM";
    private static final String UNFINISH_ALBUM = "UNFINISH_ALBUM";

    public static void saveAccountInfo(LoginResponse.AccountInfo data) {
        saveObject(BaseApplication.getApplication(), data);
    }

    public static LoginResponse.AccountInfo getAccountInfo() {
        return getObject(BaseApplication.getApplication(), LoginResponse.AccountInfo.class);
    }

    public static void saveAlbum(List<Album> list) {
        SharedPreferences.Editor editor = getEditor(MagicApplication.getApplication(), ALBUM);
        String album = new Gson().toJson(list);
        editor.putString(ALBUM, album);
        editor.commit();
    }

    public static List<Album> getAlbum() {
        SharedPreferences sharedPreferences = MagicApplication.getApplication().getSharedPreferences(ALBUM, MODE_PRIVATE);
        return new Gson().fromJson(sharedPreferences.getString(ALBUM, ""), new TypeToken<List<Album>>() {
        }.getType());
    }


    public static void saveFinishAlbum(Context context, Album album) {
        SharedPreferences.Editor editor = getEditor(MagicApplication.getApplication(), ALBUM);
        List<Album> list = new ArrayList<>();
        WeakReference<List<Album>> weakReference = new WeakReference<>(list);
        weakReference.get().add(album);
        String albumString = new Gson().toJson(getFinishAlbum(context).addAll(weakReference.get()));
        editor.putString(FINISH_ALBUM, albumString);
        editor.commit();
        weakReference.get().clear();
        weakReference.clear();
    }

    public static void saveFinishAlbum(List<Album> list) {
        SharedPreferences.Editor editor = getEditor(MagicApplication.getApplication(), ALBUM);
        WeakReference<List<Album>> weakReference = new WeakReference<>(list);
        String albumString = new Gson().toJson(weakReference.get());
        editor.putString(FINISH_ALBUM, albumString);
        editor.commit();
        weakReference.get().clear();
        weakReference.clear();
    }

    public static void deleteFinishAlbum(Context context, Album album) {
        List<Album> list = getFinishAlbum(context);
        list.remove(album);
        saveFinishAlbum(list);
    }

    public static List<Album> getFinishAlbum(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ALBUM, MODE_PRIVATE);
        return new Gson().fromJson(sharedPreferences.getString(FINISH_ALBUM, ""), new TypeToken<List<Album>>() {
        }.getType());
    }

    public static void saveUnFinishAlbum(Context context, Album album) {
        SharedPreferences.Editor editor = getEditor(MagicApplication.getApplication(), ALBUM);
        List<Album> list = new ArrayList<>();
        WeakReference<List<Album>> weakReference = new WeakReference<>(list);
        weakReference.get().add(album);
        String albumString = new Gson().toJson(getFinishAlbum(context).addAll(weakReference.get()));
        editor.putString(UNFINISH_ALBUM, albumString);
        editor.commit();
        weakReference.get().clear();
        weakReference.clear();
    }

    public static void saveUnFinishAlbum(List<Album> list) {
        SharedPreferences.Editor editor = getEditor(MagicApplication.getApplication(), ALBUM);
        WeakReference<List<Album>> weakReference = new WeakReference<>(list);
        String albumString = new Gson().toJson(weakReference.get());
        editor.putString(UNFINISH_ALBUM, albumString);
        editor.commit();
        weakReference.get().clear();
        weakReference.clear();
    }

    public static void deleteUnFinishAlbum(Context context, Album album) {
        List<Album> list = getFinishAlbum(context);
        list.remove(album);
        saveFinishAlbum(list);
    }

    public static List<Album> getUnFinishAlbum(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ALBUM, MODE_PRIVATE);
        return new Gson().fromJson(sharedPreferences.getString(UNFINISH_ALBUM, ""), new TypeToken<List<Album>>() {
        }.getType());
    }
}
