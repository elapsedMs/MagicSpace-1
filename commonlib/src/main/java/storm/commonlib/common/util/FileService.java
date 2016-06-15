package storm.commonlib.common.util;


import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

/**
 * Created by yongjiu on 15/8/15.
 */
public interface FileService {

    @GET("/img/{size}/{file}")
    retrofit.client.Response downloadImage(@Path("file") String file_id, @Path("size") String size);

    @GET("/audio/orig/{file}")
    retrofit.client.Response downloadFile(@Path("file") String file_id);

    @Multipart
    @POST("/file/upload")
    String uploadFile(@Part("file") TypedFile file);






}