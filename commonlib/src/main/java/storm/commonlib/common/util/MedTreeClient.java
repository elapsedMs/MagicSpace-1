package storm.commonlib.common.util;

import android.content.SharedPreferences;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;

public class MedTreeClient {

    private static final Map<Class<?>, Object> mServices = new HashMap<Class<?>, Object>();

    @Inject
    @Named("api")
    private static RestAdapter mApiAdapter;

    @Inject
    @Named("file")
    private static RestAdapter mFileAdapter;

//    @Inject
//    private static DbContext mDbContext;

    @Inject
    @Named("ym")
    private static SharedPreferences mSharedPreferences;

    public static SharedPreferences sharedPreferences() {
        return mSharedPreferences;
    }

//    /**
//     * Db service
//     */
//    public static DbContext db() {
//        return mDbContext;
//    }

    /**
     * File service
     */
    public static FileService file() {
        return MedTreeClient.service(mFileAdapter, FileService.class);
    }

    /**
     * Api service
     */
    public static <T> T api(Class<T> service) {
        return MedTreeClient.service(mApiAdapter, service);
    }

    /**
     * use api(service)
     */
    @Deprecated
    public static <T> T service(Class<T> service) {
        return MedTreeClient.api(service);
    }

    private static <T> T service(RestAdapter adapter, Class<T> service) {
        if (mServices.containsKey(service)) {
            return (T) mServices.get(service);
        }

        T instance = adapter.create(service);
        mServices.put(service, instance);
        return instance;
    }

}