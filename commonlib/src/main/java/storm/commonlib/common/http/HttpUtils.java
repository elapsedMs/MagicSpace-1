package storm.commonlib.common.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import storm.commonlib.common.util.JsonUtil;
import storm.commonlib.common.util.LogUtil;

import static org.apache.commons.httpclient.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.commons.httpclient.HttpStatus.SC_OK;
import static storm.commonlib.common.http.HttpConstants.APPLICATION_JSON;
import static storm.commonlib.common.http.HttpConstants.CONTENT_TYPE;
import static storm.commonlib.common.http.HttpConstants.TIME_OUT;
import static storm.commonlib.common.http.HttpConstants.X_AUTH_TOKEN;
import static storm.commonlib.common.util.StringUtil.EMPTY;

public class HttpUtils {

    static String hostUri;
    static String token;

    public static <T> T request(RequestTypes requestType, String path, HashMap paramObject, Class<T> tClass) throws Exception {
        String localHostUrl = hostUri;

        String result = httpRequest(localHostUrl + path, paramObject, requestType);

        tClass = tClass == null ? (Class<T>) Object.class : tClass;
        return JsonProvider.toObject(result, tClass);
    }

    private static String httpRequest(String url, HashMap paramObject, RequestTypes requestType) throws Exception {
        HttpClient client = buildClient();
        HttpMethodBase method = BuildMethodBase(url, paramObject, requestType);
        setHeader(requestType, method);

        int statusCode = client.executeMethod(method);//状态，一般200为OK状态，其他情况会抛出如404,500,403等错误

        if (statusCode != SC_OK && statusCode != SC_INTERNAL_SERVER_ERROR) {
            throw new MessageException(ExceptionTypes.NetworkFail, EMPTY);
        }
        String result = method.getResponseBodyAsString();
        client.getHttpConnectionManager().closeIdleConnections(1);
        LogUtil.i("HttpUtils", "Result :" + result);
        return result;
    }

    //    {"status":0,"status_msg":"success","version":"1.0","channel":0,"date":1467244088,"data_type":1,"language":"chinese","action":"","data":[{"userId":"3970430042189702","nickName":"\u4e00\u5668\u5165\u9b42","portraitImage":"http:\/\/sso.mojing.cn\/assets\/web\/images\/usercenter\/noavatar_big.jpg","contentId":"3416","createTime":"1465867750000","duration":"0","url":"http:\/\/share.moyan.mojing.cn\/MEdata\/file\/3970430042189702\/1463845159508.jpg","commentCount":"0","appreciateCount":"0","thumbImageUrl":"http:\/\/share.moyan.mojing.cn\/MEdata\/file\/3970430042189702\/1463845159508_thumb.jpg","contentType":"2","playCount":null,"creditEarned":null,"isCollected":"0","isAppreciated":"0","description":"\u8fd9\u662f\u5565\u5b50\u561b\uff1f"},{"userId":"3970430042189702","nickName":"\u4e00\u5668\u5165\u9b42","portraitImage":"http:\/\/sso.mojing.cn\/assets\/web\/images\/usercenter\/noavatar_big.jpg","contentId":"3404","createTime":"1463845159953","duration":"0","url":"http:\/\/share.moyan.mojing.cn\/MEdata\/file\/3970430042189702\/1463845159508.jpg","commentCount":"0","appreciateCount":"0","thumbImageUrl":"http:\/\/share.moyan.mojing.cn\/MEdata\/file\/3970430042189702\/1463845159508_thumb.jpg","contentType":"2","playCount":null,"creditEarned":null,"isCollected":"0","isAppreciated":"0","description":"\u8fd9\u662f\u5565\u5b50\u561b\uff1f"}]}
    private static HttpMethodBase BuildMethodBase(String url, HashMap params, RequestTypes requestType) {
        HttpMethodBase method = null;
        String postBody = EMPTY;
        if (params != null) postBody = JsonUtil.convertObjectToJson(params);
        switch (requestType) {
            case GET:
                method = new GetMethod(url);
                break;

            case POST:
                PostMethod m = new PostMethod(url);
                Iterator iter = params.entrySet().iterator();
                String currentBody = EMPTY;
                while (iter.hasNext()) {
                    HashMap.Entry entry = (Map.Entry) iter.next();
                    String key = entry.getKey().toString();
                    String val = entry.getValue().toString();
                    currentBody = currentBody + "" + key + "=" + val + (iter.hasNext() ? ";" : "");
                }
                m.setRequestBody(currentBody);
                LogUtil.i("HttpUtils", "requestType: " + requestType + "         path: " + url + "\n currentBody : " + currentBody + "\n");

                method = m;
                break;

            case PUT:
                PutMethod pm = new PutMethod(url);
                pm.setRequestBody(postBody);
                method = pm;
                break;

            case DELETE:
                method = new DeleteMethod(url);
                break;
        }
        return method;
    }

    private static HttpClient buildClient() {
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT);// 设置连接超时时间
        return client;
    }

    private static void setHeader(RequestTypes requestType, HttpMethodBase method) {
//        b80af1c37c5c439d967d21656bf39ca1
        if (token != null && !token.equals(EMPTY)) {
            method.setRequestHeader(X_AUTH_TOKEN, token);
        }

        if (requestType != RequestTypes.DELETE)
            method.setRequestHeader(CONTENT_TYPE, APPLICATION_JSON);
    }

    public static <T> T uploadFile(String path, File file, Class<T> tClass) throws Exception {
        String result = uploadFile(hostUri + path, file);
        tClass = tClass == null ? (Class<T>) Object.class : tClass;
        return JsonProvider.toObject(result, tClass);
    }

    private static String uploadFile(String url, File file) throws Exception {
        if (!file.exists()) return null;

        String result = null;
        PostMethod method = new PostMethod(url);
        try {
            HttpClient client = new HttpClient();

            // 设置头
            if (token != null && !token.equals(EMPTY)) {
                method.setRequestHeader(X_AUTH_TOKEN, token);
            }
            FilePart fp = new FilePart("file", file, "multipart/form-data;", "utf-8");
            Part[] parts = {fp};

            //对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
            MultipartRequestEntity mre = new MultipartRequestEntity(parts, method.getParams());
            method.setRequestEntity(mre);
            client.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT);// 设置连接超时时间
            int statusCode = client.executeMethod(method);

            if (statusCode != SC_OK && statusCode != SC_INTERNAL_SERVER_ERROR) {
                throw new MessageException(ExceptionTypes.NetworkFail, "上传文件失败！");
            }

            result = method.getResponseBodyAsString();
        } finally {
            method.releaseConnection();
        }
        return result;
    }

    public static void setHostUri(String hostUri) {
        HttpUtils.hostUri = hostUri;
    }

    public static void setToken(String token) {
        HttpUtils.token = token;
    }

    public static void setAppModel(boolean isDebug) {
        setHostUri(isDebug ? HttpConstants.DEBUG_HOST : HttpConstants.REAL_HOST);
    }
}