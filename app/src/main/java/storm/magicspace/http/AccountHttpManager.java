package storm.magicspace.http;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import storm.commonlib.common.util.BaseUtil;

import static storm.commonlib.common.http.HttpConstants.ACCOUNT_HOST_URL;

/**
 * Created by lixiaolu on 16/6/28.
 */
public class AccountHttpManager {

    public static String doLogin(String name, String password) {
        //debug.info("UGCServer getMJUserInfo is start......");
        String result = "";
        String url = ACCOUNT_HOST_URL + "apilogin";
        Map<String, Object> params = new HashMap<>();
        String open_verify = BaseUtil.MD5(name + "&" + password + URLConstant.MJ_KEY + URLConstant.MJ_USER_CENTER_KEY);
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
            result = doObjectPost(url, params);
        } catch (Exception ignored) {
        }
        return result;
    }

    public static String doObjectPost(String urlString, Map<String, Object> nameValuePairs) throws Exception {
        String result;
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
        return new String(buffer.toByteArray());
    }

}