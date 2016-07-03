package storm.magicspace.http;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import storm.commonlib.common.BaseApplication;
import storm.commonlib.common.util.BaseUtil;
import storm.commonlib.common.util.JsonUtil;
import storm.magicspace.http.reponse.GetVerifyCodeResponse;
import storm.magicspace.http.reponse.LoginResponse;
import storm.magicspace.http.reponse.RegisterResponse;

import static storm.commonlib.common.BaseApplication.getApplication;
import static storm.commonlib.common.http.HttpConstants.ACCOUNT_HOST_URL;

/**
 * Created by lixiaolu on 16/6/28.
 */
public class AccountHttpManager {

    private static String TAG = "AccountHttpManager";
    private static AQuery aq;

    public static LoginResponse doLogin(String name, String password, LoginJson loginJson) {
        //debug.info("UGCServer getMJUserInfo is start......");
        String result = "";
        String url = ACCOUNT_HOST_URL + "apilogin";
        Map<String, Object> params = new HashMap<>();
        String open_verify = BaseUtil.MD5(name + "&" + password + URLConstant.MJ_USER_CENTER_KEY + URLConstant.MJ_USER_CENTER_KEY);
        CustomJSONObject obj = null;
        try {
            obj = new CustomJSONObject();
            obj.put("login_name", name);
            obj.put("login_pwd", password);
            obj.put("open_verify", open_verify);
        } catch (org.json.JSONException e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        }

        String openid = "";
        try {
            openid = URLEncoder.encode(obj.toString(), "utf-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        }
        params.put("open_id", openid);

        try {
            loginJson.setJson(result = doObjectPost(url, params));
        } catch (Exception ignored) {
        }
        return JsonUtil.convertJsonToObject(result, TypeToken.get(LoginResponse.class));
    }

    public static String doObjectPost(String urlString, Map<String, Object> nameValuePairs) throws Exception {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        PrintWriter out = new PrintWriter(connection.getOutputStream());
        boolean first = true;

        for (Map.Entry<String, Object> pair : nameValuePairs.entrySet()) {
            if (first)
                first = false;
            else
                out.print("&");
            String name = pair.getKey();
            Object value = pair.getValue();
            out.print(name);
            out.print('=');
            out.print(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        out.close();

        InputStream in = connection.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len;
        while ((len = in.read(buff)) != -1) {
            buffer.write(buff, 0, len);
        }
        in.close();
        String result = new String(buffer.toByteArray());
        return result;
    }

    public static GetVerifyCodeResponse getVerifyCode(String phone, String type) {
        try {
            Log.i(TAG, "reqSms  phoneNum" + phone);
            String url = ACCOUNT_HOST_URL + "sendsmscode";

            Map<String, Object> params = new HashMap<String, Object>();

            String open_verify = BaseUtil.MD5(type + "&" + phone + URLConstant.MJ_USER_CENTER_KEY + URLConstant.MJ_USER_CENTER_KEY);

            CustomJSONObject obj = new CustomJSONObject();
            obj.put("send_type", type);
            obj.put("regist_tel", phone);
            obj.put("open_verify", open_verify);
            String openid = URLEncoder.encode(obj.toString(), "utf-8");
            Log.i(TAG, openid);
            params.put("open_id", openid);
            aq = new AQuery(getApplication());

            aq.ajax(url, params, JSONObject.class,
                    new AjaxCallback<JSONObject>() {
                        @Override
                        public void callback(String url, JSONObject json,
                                             AjaxStatus status) {
                            Log.i(TAG, "reqSms callback json = "
                                    + (json == null ? "" : json));
                            if (json != null) {
                                try {
                                    boolean smsStatus = json
                                            .getBoolean("status");
                                    String msg = json.getString("msg");
                                    if (smsStatus) {
                                        if (!TextUtils.isEmpty(msg)) {
                                            Toast.makeText(getApplication(),
                                                    msg, Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    } else {
                                        if (!TextUtils.isEmpty(msg)) {
                                            Toast.makeText(getApplication(),
                                                    msg, Toast.LENGTH_SHORT)
                                                    .show();
                                        } else {
                                            Toast.makeText(getApplication(),
                                                    "获取验证码失败，请稍后再试",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.i(TAG, "reqSms", e);
                                }
                            } else {
                                Toast.makeText(getApplication(),
                                        "获取验证码失败，请检查网络", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "reqSms", e);
        }

        return null;
    }

//
//        String result = "";
//        String url = ACCOUNT_HOST_URL + "sendsmscode";
//        Map<String, Object> params = new HashMap<>();
//        String open_verify = BaseUtil.MD5(sendType + "&" + phone + URLConstant.MJ_USER_CENTER_KEY + URLConstant.MJ_USER_CENTER_KEY);
//        CustomJSONObject obj = null;
//        try {
//            obj = new CustomJSONObject();
//            obj.put("send_type", "tel_regist");
//            obj.put("regist_tel", phone);
//            obj.put("open_verify", open_verify);
//        } catch (org.json.JSONException e1) {
//            // TODO 自动生成的 catch 块
//            e1.printStackTrace();
//        }
//
//        String openid = "";
//        try {
//            openid = URLEncoder.encode(obj.toString(), "utf-8");
//        } catch (UnsupportedEncodingException e1) {
//            // TODO 自动生成的 catch 块
//            e1.printStackTrace();
//        }
//        params.put("open_id", openid);
//
//        try {
//            result = doObjectPost(url, params);
//            Log.i("AccountManager", "Account Http : verify code : response" + result);
//        } catch (Exception ignored) {
//        }
//        return JsonUtil.convertJsonToObject(result, TypeToken.get(LoginResponse.class));
//    }

    public static void reqSms(String phoneNum) {
        try {
            Log.i(TAG, "reqSms  phoneNum" + phoneNum);
            String url = ACCOUNT_HOST_URL + "sendsmscode";

            Map<String, Object> params = new HashMap<String, Object>();

            String open_verify = BaseUtil.MD5("tel_regist" + "&" + phoneNum + URLConstant.MJ_USER_CENTER_KEY + URLConstant.MJ_USER_CENTER_KEY);

            CustomJSONObject obj = new CustomJSONObject();
            obj.put("send_type", "tel_regist");
            obj.put("regist_tel", phoneNum);
            obj.put("open_verify", open_verify);
            String openid = URLEncoder.encode(obj.toString(), "utf-8");
            Log.i(TAG, openid);
            params.put("open_id", openid);
            aq = new AQuery(getApplication());

            aq.ajax(url, params, JSONObject.class,
                    new AjaxCallback<JSONObject>() {
                        @Override
                        public void callback(String url, JSONObject json,
                                             AjaxStatus status) {
                            Log.i(TAG, "reqSms callback json = "
                                    + (json == null ? "" : json));
                            if (json != null) {
                                try {
                                    boolean smsStatus = json
                                            .getBoolean("status");
                                    String msg = json.getString("msg");
                                    if (smsStatus) {
                                        if (!TextUtils.isEmpty(msg)) {
                                            Toast.makeText(getApplication(),
                                                    msg, Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    } else {
                                        if (!TextUtils.isEmpty(msg)) {
                                            Toast.makeText(getApplication(),
                                                    msg, Toast.LENGTH_SHORT)
                                                    .show();
                                        } else {
                                            Toast.makeText(getApplication(),
                                                    "获取验证码失败，请稍后再试",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.i(TAG, "reqSms", e);
                                }
                            } else {
                                Toast.makeText(getApplication(),
                                        "获取验证码失败，请检查网络", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "reqSms", e);
        }

    }

    public static RegisterResponse doRegist(String phoneNum, String password, String captchaMsg) {
        final RegisterResponse[] response = {new RegisterResponse()};
        try {
            Log.i(TAG, "userReg");
            String url = "http://sso.mojing.cn/user/api/apiregist";
            Map<String, Object> params = new HashMap<>();

            String open_verify = BaseUtil.MD5(phoneNum + "&" + password + "&"
                    + captchaMsg + URLConstant.MJ_USER_CENTER_KEY + URLConstant.MJ_USER_CENTER_KEY);
            CustomJSONObject obj = new CustomJSONObject();
            obj.put("user_tel", phoneNum);
            obj.put("user_pwd", password);
            obj.put("user_check", captchaMsg);
            obj.put("open_verify", open_verify);
            Log.i(TAG, "phoneNum=" + phoneNum + ",login_pwd=" + password
                    + ",msg=" + captchaMsg);
            final String[] openid = {URLEncoder.encode(obj.toString(), "utf-8")};
            params.put("open_id", openid[0]);

            AQuery aQuery = new AQuery(BaseApplication.getApplication());
            aQuery.ajax(url, params, JSONObject.class,
                    new AjaxCallback<JSONObject>() {
                        @Override
                        public void callback(String url, JSONObject json,
                                             AjaxStatus status) {
                            Log.i("AccountManager",
                                    "userReg callBack json = "
                                            + String.valueOf(json));
                            response[0] = JsonUtil.convertJsonToObject(String.valueOf(json), TypeToken.get(RegisterResponse.class));
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "", e);
        }
        return response[0];
    }

    public static RegisterResponse changePwd(String phoneNum, String password, String msg) {
        final RegisterResponse[] response = {new RegisterResponse()};
        String url = "http://sso.mojing.cn/user/api/getuserpassword";
        Map<String, Object> params = new HashMap<String, Object>();
        String open_verify = BaseUtil.MD5(phoneNum + "&" + password + "&" + msg
                + URLConstant.MJ_USER_CENTER_KEY + URLConstant.MJ_USER_CENTER_KEY);
        try {
            CustomJSONObject jsonparam = new CustomJSONObject();
            jsonparam.put("user_tel", phoneNum);
            jsonparam.put("user_new_pwd", password);
            jsonparam.put("user_check", msg);
            jsonparam.put("open_verify", open_verify);
            String open_id = URLEncoder.encode(jsonparam.toString(), "utf-8");
            params.put("open_id", open_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                if (json != null) {
                    response[0] = JsonUtil.convertJsonToObject(String.valueOf(json), TypeToken.get(RegisterResponse.class));
                }
            }
        });
        return response[0];

    }

    public static class LoginJson {
        private String json;

        public String getJson() {
            return json;
        }

        public void setJson(String json) {
            this.json = json;
        }
    }

}