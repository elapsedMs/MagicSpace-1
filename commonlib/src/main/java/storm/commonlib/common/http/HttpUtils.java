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

    public static <T> T request(boolean isAccountRequest, RequestTypes requestType, String path, Object paramObject, Class<T> tClass) throws Exception {
        String postBody = null;
        String localHostUrl = isAccountRequest ? HttpConstants.ACCOUNT_HOST_URL : hostUri;

        if (paramObject != null) postBody = JsonProvider.toJson(paramObject);
        String result = httpRequest(localHostUrl + path, postBody, requestType);

        tClass = tClass == null ? (Class<T>) Object.class : tClass;
        return JsonProvider.toObject(result, tClass);
    }

    private static String httpRequest(String url, String postBody, RequestTypes requestType) throws Exception {
        HttpClient client = buildClient();
        HttpMethodBase method = BuildMethodBase(url, postBody, requestType);
        setHeader(requestType, method);

        int statusCode = client.executeMethod(method);//状态，一般200为OK状态，其他情况会抛出如404,500,403等错误

        if (statusCode != SC_OK && statusCode != SC_INTERNAL_SERVER_ERROR) {
            throw new MessageException(ExceptionTypes.NetworkFail, EMPTY);
        }
        String result = method.getResponseBodyAsString();
        client.getHttpConnectionManager().closeIdleConnections(1);
        LogUtil.i("HttpUtils", "requestType: " + requestType + "         path: " + url + "\n postBody : " + postBody + "\n" + result);
        return result;
    }

    private static HttpMethodBase BuildMethodBase(String url, String postBody, RequestTypes requestType) {
        HttpMethodBase method = null;
        switch (requestType) {
            case GET:
                method = new GetMethod(url);
                break;

            case POST:
                PostMethod m = new PostMethod(url);
                m.setRequestBody(postBody);
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