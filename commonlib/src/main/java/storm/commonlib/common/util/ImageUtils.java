package storm.commonlib.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import storm.commonlib.common.cache.FileCache;
import storm.commonlib.common.cache.ImageCache;
import storm.commonlib.common.http.HttpConstants;

/**
 * Created by yongjiu on 15/8/15.
 */
public class ImageUtils {

    private static Context mContext;

    public static void display(ImageView view, final String image_id, final ImageSize size, final int sizeType) {
        ImageUtils.display(view, image_id, size, -1, sizeType);
    }

    public static void display(final ImageView view, final String image_id, final ImageSize size, final int default_res_id, final int sizeType) {
        // 一级缓存(内存)
        final ImageCache cache = ImageCache.getCache(size);
        if (cache != null) {
            Bitmap bitmap = null;
            if (image_id != null) {
                bitmap = cache.get(image_id);
            }
            if (bitmap != null) {
                view.setImageBitmap(bitmap);
                return;
            }
        }

        // 二级缓存(文件)
        Bitmap bitmap = null;
        if (image_id != null) {
            bitmap = FileCache.getBitmap(image_id, size.toString(), sizeType);
        }
        LogUtil.i("ImageUtil", "level 2");
        if (bitmap != null) {
            view.setImageBitmap(bitmap);
            if (cache != null) {
                cache.put(image_id, bitmap);
            }
            return;
        }

        // 三级缓存(网络)
        final WeakReference<ImageView> reference = new WeakReference<ImageView>(view);
        view.setClickable(false);
        new AsyncTask<Object, Object, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Object... params) {
                try {
                    File file = FileUtils.downloadImageBySize(image_id, size.toString());
                    LogUtil.i("ImageUtil", "down load image : level 3");
                    return FileCache.getBitmap(file, sizeType);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                view.setClickable(true);
                ImageView view = reference.get();
                if (bitmap == null) {
                    if (view != null && default_res_id > 0) {
                        view.setClickable(true);
                        view.setImageResource(default_res_id);
                    }
                } else {
//                    int bitmapWidth = bitmap.getWidth();
//                    int x = (bitmapWidth / 2 - 60) >= 0 ? (bitmapWidth / 2 - 60) : 0;
//                    LogUtil.i("ImageUtil", "bitmap.getWidth :" + bitmapWidth + "  X :" + x);
//                    int bitmapHeight = bitmap.getHeight();
//                    int y = (bitmapHeight / 2 - 60) >= 0 ? (bitmapHeight / 2 - 60) : 0;
//                    LogUtil.i("ImageUtil", "bitmap.getHeight :" + bitmapHeight + " Y:" + y);
//                    Bitmap current = Bitmap.createBitmap(bitmap, x, y, 120 > (bitmapWidth + x) ? bitmapWidth : 120, 120 > (bitmapHeight + y) ? bitmapHeight : 120);
                    if (view != null) {
                        view.setImageBitmap(bitmap);
                    }
                    if (cache != null) {
                        cache.put(image_id, bitmap);
                    }
                }
            }
        }.execute();

    }

    public static Bitmap getShareLogo(final String image_id, final ImageSize size, final int sizeType) {
        // 一级缓存(内存)
        final ImageCache cache = ImageCache.getCache(size);
        if (cache != null) {
            Bitmap bitmap = null;
            if (image_id != null) {
                bitmap = cache.get(image_id);
            }
            if (bitmap != null) {
                return bitmap;
            }
        }

        // 二级缓存(文件)
        Bitmap bitmap = null;
        if (image_id != null) {
            bitmap = FileCache.getBitmap(image_id, size.toString(), sizeType);
        }
        if (bitmap != null) {
            if (cache != null) {
                cache.put(image_id, bitmap);
            }
            return bitmap;
        }

        // 三级缓存(网络)
        new AsyncTask<Object, Object, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Object... params) {
                try {
                    File file = FileUtils.downloadImageBySize(image_id, size.toString());
                    return FileCache.getBitmap(file, sizeType);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (bitmap == null) {

                } else {
                    if (cache != null) {
                        cache.put(image_id, bitmap);
                    }
                }
            }
        }.execute();
        return null;
    }


    public static void delete(ImageSize imageSize, String imageID) {
        final ImageCache cache = ImageCache.getCache(imageSize);
        if (cache != null) {
            cache.remove(imageID);
        }
    }

    public static void loadImage(final ImageView imageView, final ImageSize image_size, int onLoadingImage, int onFailImage, final String image_id) {
        //显示图片的配置
        final DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(onLoadingImage)
                .showImageOnFail(onFailImage)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        imageView.post(new Runnable() {
            @Override
            public void run() {
                ImageLoader.getInstance().displayImage(HttpConstants.DEV_FILE_HOST + "/img/" + image_size.toString() + "/" + image_id, imageView, options);

            }
        });

    }

}