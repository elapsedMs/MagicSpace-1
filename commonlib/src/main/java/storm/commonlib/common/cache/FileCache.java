package storm.commonlib.common.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * require: android.permission.WRITE_EXTERNAL_STORAGE
 * Created by yongjiu on 15/8/15.
 */
public class FileCache {

    public static final String SIZE_FILE = "file";
    public static final int DEFAULT_SIZE_TYPE = 128 * 128;

    private static File mRoot;

    public static void init(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            mRoot = new File(Environment.getExternalStorageDirectory(), "medtree");
        } else {
            mRoot = null;
        }
        if (mRoot == null) return;
        mRoot.mkdirs();
    }

    public static Bitmap getBitmap(File file, int sizeType) {
        return getBitmap(file.getAbsolutePath(), sizeType);
    }

    public static Bitmap getBitmap(File file, int min, int max) {
        return getBitmap(file.getAbsolutePath(), min, max);
    }

    public static Bitmap getBitmap(String file, int sizeType) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, opts);
        opts.inSampleSize = computeSampleSize(opts, -1, sizeType);
        opts.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file, opts);
//        return BitmapFactory.decodeFile(file);
    }

    public static Bitmap getBitmap(String file, int min, int max) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, opts);
        opts.inSampleSize = computeSampleSize(opts, min, max);
        opts.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file, opts);
    }

    public static Bitmap getBitmap(String file_id, String size, int sizeType) {
        File file = FileCache.get(file_id, size);
        return file == null || !file.exists() ? null : FileCache.getBitmap(file, sizeType);
    }

    public static File get(String file_id, String size) {
        File file = new File(mRoot, String.format("cache_%s_%s", size, file_id));
        return mRoot == null ? null : file;
    }

    public static File get(String file_id) {
        return FileCache.get(file_id, SIZE_FILE);
    }

    public static File get(String file_id, int stype) {
        return new File(file_id);
    }

    public static File save(InputStream in, String file_id) {
        return FileCache.save(in, file_id, SIZE_FILE);
    }

    public static File save(InputStream in, String file_id, String s) {
        File file = FileCache.get(file_id, s);
        try {
            if (!file.exists()) file.createNewFile();
            OutputStream os = new FileOutputStream(file);
            try {
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer, 0, 1024)) != -1) {
                    os.write(buffer, 0, read);
                }
            } finally {
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) &&
                (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
}