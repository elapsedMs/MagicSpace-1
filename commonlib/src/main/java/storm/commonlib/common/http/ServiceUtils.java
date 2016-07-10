package storm.commonlib.common.http;

import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;

import storm.commonlib.R;
import storm.commonlib.common.http.baseHttpBean.BaseResponse;
import storm.commonlib.common.util.LogUtil;
import storm.commonlib.common.util.MessageUtil;
import storm.commonlib.common.util.SharedPreferencesUtil;

import static storm.commonlib.common.BaseApplication.getApplication;
import static storm.commonlib.common.util.SharedPreferencesUtil.getToken;

/**
 * 远程服务基类
 */
public abstract class ServiceUtils {
    private final static String TAG = ServiceUtils.class.getName();
    public static final String HTTP_UTILS = "HttpUtils";

    /**
     * 请求远程服务
     *
     * @param requestType  请求类型
     * @param path         访问路径
     * @param errorTip     错误提示信息
     * @param tClass       返回类的类型
     * @param keyValueList 参数名及参数值列表
     * @return 返回结果
     */
    public static <T extends BaseResponse> T request(RequestTypes requestType, String path, String errorTip, Class<T> tClass, Object... keyValueList) {
        return baseRequest(true, requestType, path, errorTip, true, tClass, Arrays.asList(keyValueList));
    }

    public static <T extends BaseResponse> T initRequest(RequestTypes requestType, String path, String errorTip, Class<T> tClass, Object... keyValueList) {
        return baseRequest(true, requestType, path, errorTip, true, tClass, Arrays.asList(keyValueList));
    }


    private static <T extends BaseResponse> T baseRequest(boolean isShowMessage, RequestTypes requestType, String path, String errorTip, boolean checkResult, Class<T> tClass, java.util.List<Object> keyValueList) {
        // 远程同步
        RemotingSyncProvider.remotingSync(path);

        Object params;
        // 处理参数
        HashMap<String, Object> paramsMap = null;
        if (keyValueList != null && keyValueList.size() >= 2) {
            paramsMap = new HashMap<>();
            int count = keyValueList.size() / 2;
            for (int i = 0; i < count; i++) {
                paramsMap.put(keyValueList.get(i * 2).toString(), keyValueList.get(i * 2 + 1));
            }

            params = paramsMap;
        } else {
            params = keyValueList;
        }

        // 设置Token
        if (SharedPreferencesUtil.getToken() != null)
            HttpUtils.setToken(getToken());

        // 处理get请求
        if (requestType == RequestTypes.GET && paramsMap != null) {
            path += "?";
            for (String key : paramsMap.keySet()) {
                Object value = paramsMap.get(key);
                if (value != null) {
                    path += key + "=" + value + "&";
                }
            }
            path = path.substring(0, path.length() - 1);
            params = null;
        }

//        if (requestType == RequestTypes.POST && paramsMap != null) {
//            for (String key : paramsMap.keySet()) {
//
//            }
//        }

        T result = null;
        try {
//            if (requestType == RequestTypes.POST)
            result = HttpUtils.request(requestType, path, paramsMap, tClass);
//            result = HttpUtils.request(requestType, path, paramsMap, tClass);
        } catch (Exception e) {
            handleException(errorTip, e, isShowMessage);
        }
        return result;
    }

    /**
     * 上传文件服务
     *
     * @param path     访问路径
     * @param errorTip 错误提示信息
     * @param tClass   返回类的类型
     * @param file     要上传的文件
     * @return 返回结果
     */
    public static <T extends BaseResponse> T uploadFile(String path, String errorTip, Class<T> tClass, File file) {
        HttpUtils.setToken(getToken());

        T result = null;
        try {
            result = HttpUtils.uploadFile(path, file, tClass);
        } catch (Exception e) {
            handleException(errorTip, e);
        }
        return result;
    }


    /**
     * 处理异常(默认显示提示信息)
     *
     * @param logTip 异常日志提示
     * @param e      异常
     */
    public static void handleException(String logTip, Exception e) {
        handleException(logTip, e, true);
    }

    /**
     * 处理异常
     *
     * @param logTip        异常日志提示
     * @param e             异常
     * @param isShowMessage 是否显示提示信息
     */
    public static void handleException(String logTip, Exception e, boolean isShowMessage) {
        if (e instanceof MessageException) {
            String message = ((MessageException) e).getInfo();
            if (isShowMessage) showMessage(message);
        } else if (e instanceof ConnectException) {
            if (isShowMessage) showMessage(R.string.http_connect_error);
        } else if (e instanceof UnknownHostException) {
            if (isShowMessage) showMessage(R.string.http_host_error);
        } else if (e instanceof UndeclaredThrowableException) {
            if (isShowMessage) showMessage(R.string.http_reach_error);
        } else if (e instanceof SocketTimeoutException) {
            if (isShowMessage) showMessage(R.string.http_time_out);
        } else if (e instanceof JsonSyntaxException) {
            if (isShowMessage) showMessage(R.string.http_data_trans_error);
        } else {
            if (isShowMessage) showMessage(R.string.error_network);
        }

        LogUtil.e(TAG, logTip, e);
    }

    public static void showMessage(int resId) {
        MessageUtil.showHttpBaseMessage(getApplication().getString(resId));
    }

    public static void showMessage(String message) {
        MessageUtil.showHttpBaseMessage(message);
    }


}
