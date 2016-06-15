package storm.commonlib.common.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import java.util.HashMap;
import java.util.Map;

import storm.commonlib.common.util.ImageSize;


/**
 * Created by yongjiu on 15/8/15.
 */
public class ImageCache extends LruCache<String, Bitmap> {

    private static Map<ImageSize, ImageCache> mCaches = new HashMap<ImageSize, ImageCache>();

    public static ImageCache getCache(ImageSize size) {
        if (mCaches.containsKey(size)) return mCaches.get(size);
//        return null;
        long maxMemory = Runtime.getRuntime().maxMemory();
        return mCaches.put(size, new ImageCache((int) (maxMemory / 8)));
    }

    public static void init(Context context) {
        long maxMemory = Runtime.getRuntime().maxMemory();
        mCaches.put(ImageSize.Avatar, new ImageCache((int) (maxMemory / 8)));
        mCaches.put(ImageSize.Thumbs, new ImageCache((int) (maxMemory / 10)));
        mCaches.put(ImageSize.PostSingle, new ImageCache((int) (maxMemory / 12)));
    }

    private ImageCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

}