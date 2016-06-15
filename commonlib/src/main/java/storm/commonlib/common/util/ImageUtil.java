package storm.commonlib.common.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {
    private final static String TAG = ImageUtil.class.getName();

    public static final int REQ_WIDTH = 124;
    public static final int REQ_HEIGHT = 124;
    public static final int COMPRESS_QUALIT = 100;

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if (width > reqHeight || height > reqHeight) {
            int halfWidth = width / 2;
            int halfHeight = height / 2;

            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize = inSampleSize * 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFile(String filepath, int reqWidth, int reqHeight) {
        if (filepath == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, options);
        options.inSampleSize = ImageUtil.calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filepath, options);
    }

    public static File getScaledBitmapFile(String filePath) {
        if (filePath == null)
            return null;

        Bitmap bitmap = null;
        File file = new File(filePath);
        try {
            bitmap = decodeSampledBitmapFromFile(filePath, REQ_WIDTH, REQ_HEIGHT);
            if (bitmap != null) {
                int degree = readPictureDegree(filePath);
                bitmap = rotatePicture(degree, bitmap);
                FileOutputStream outputStream = new FileOutputStream(filePath);
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALIT, outputStream)) {
                    outputStream.flush();
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null)
                bitmap.recycle();
        }
        return file;
    }

    public static void specialCase(String imagePath, File file1) {
        int degree = ImageUtil.readPictureDegree(imagePath);
        if (degree != 0 || Build.MODEL.equals("C6502")) {
            Bitmap rotate = BitmapFactory.decodeFile(imagePath);
            rotate = ImageUtil.rotatePicture(90, rotate);
            try {
                FileOutputStream outputStream = new FileOutputStream(file1);
                boolean result = rotate.compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALIT, outputStream);
                if (result) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (rotate != null) rotate.recycle();
            }
        }
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            LogUtil.e(TAG, "readPictureDegree error", e);
        }
        return degree;
    }


    public static void setImageDegree(String path, String type) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getPcitureDegree(String absolutePath) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(absolutePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap rotatePicture(int degree, Bitmap bitmap) {
        if (degree == 0) return bitmap;
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}