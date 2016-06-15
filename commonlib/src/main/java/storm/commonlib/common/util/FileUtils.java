package storm.commonlib.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit.client.Response;
import storm.commonlib.common.cache.FileCache;

import static storm.commonlib.common.util.StringUtil.EMPTY;


/**
 * Created by yongjiu on 15/8/15.
 */
public class FileUtils {

    private static Response response;
    public static String mCameraImagePath;


    public static InputStream downloadStream(String file_id, String size) throws IOException {
        FileService service = MedTreeClient.file();
        try {
            response = service.downloadFile(file_id);
        } catch (Exception e) {
            return null;
        }
        return response.getBody().in();
    }

    public static InputStream downloadImage(String file_id, String size) throws IOException {
        FileService service = MedTreeClient.file();
        try {
            response = service.downloadImage(file_id, size);
        } catch (Exception e) {
            return null;
        }
        return response.getBody().in();
    }

    public static File downloadImageBySize(String file_id, String size) throws IOException {
        InputStream in = downloadImage(file_id, size);
        if (in == null) return new File(EMPTY);
        File file = FileCache.save(in, file_id, size);
        in.close();
        return file;
    }

    public static File getNewFile(Context context, String path) {
        File file;
        if (!isHasEnvironment(context)) return null;
        file = Environment.getExternalStorageDirectory();
        File newFile = new File(file, path);
        mCameraImagePath = newFile.getAbsolutePath();
        File parentDir = newFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            boolean b = parentDir.mkdirs();
            LogUtil.d("gdq", "mkdirs : " + b);
        }
        return newFile;
    }

    public static boolean isHasEnvironment(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            ViewUtil.showToast(context, "请确认已经插入SD卡");
            return false;
        }
    }


    public static String getLocalImagePath(Context context, Uri uri) {
        String filePath = null;
        int columnIndex = 0;
        android.database.Cursor cursor = null;

        try {
            String strURL = uri.toString();
            //文件uri
            if (strURL.startsWith("file://")) {
                filePath = strURL.substring("file://".length());
            }
            // 4.4即以后 uri形如:content://com.android.providers.media.documents/document/image%2706
            else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT &&
                    strURL.contains("com.android.providers.media.documents")) {
                String wholeID = DocumentsContract.getDocumentId(uri);
                //获得资源唯一ID
                String id = wholeID.split(":")[1];
                //定义索引字段
                String[] column = {MediaStore.Images.Media.DATA};
                String sel = MediaStore.Images.Media._ID + "=?";
                cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,
                        sel, new String[]{id}, null);
                columnIndex = cursor.getColumnIndex(column[0]);
                //4.4以前  uri形如:content://media/extenral/images/media/17766
            } else {
                String[] projection = {MediaStore.Images.Media.DATA};
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            }
        } catch (Exception e) {
            LogUtil.e("gdq", "getLocalImagePath error uri=" + uri, e);
        }

        if (cursor != null) {
            if (cursor.moveToFirst())
                filePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return filePath;
    }

    public static File handleBitmapToFile(String localImagePath, String toImageFilePath) {
        Bitmap bitmap = null;
        BufferedOutputStream bufferedOutputStream = null;
        File file = new File(toImageFilePath);
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(localImagePath, options);
            int inSampleSize = ImageUtil.calculateInSampleSize(options, ImageUtil.REQ_WIDTH, ImageUtil.REQ_HEIGHT);
            options.inSampleSize = inSampleSize;
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(localImagePath, options);
            if (bitmap == null) return null;
            int degree = ImageUtil.getPcitureDegree(localImagePath);
            bitmap = ImageUtil.rotatePicture(degree, bitmap);
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, ImageUtil.COMPRESS_QUALIT, bufferedOutputStream)) {
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null) bitmap.recycle();
        }
        return file;
    }

}