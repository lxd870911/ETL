package top.wetech.tools.service;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/3/21.
 */
public class HttpUtils {
    private static Logger logger = Logger.getLogger(HttpUtils.class);

    /**
     * description: 发送post请求
     * param: [url, params]
     * return: java.lang.String
     * date: 2018/6/13
     * time: 15:39
     */
    public static String sendPost(String url, Map<String, String> params) {
        String str = "";
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(url);
            List<NameValuePair> list = getNameValuePairArr(params);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
            httppost.setEntity(entity);
            CloseableHttpResponse response;
            try {
                response = httpclient.execute(httppost);
                HttpEntity httpentity = response.getEntity();
                if (httpentity != null) {
                    str = EntityUtils.toString(httpentity, "UTF-8");
                }
            } catch (Exception e) {
                logger.error("send post request to url: " + url + " with parameters: " + params + " exception!", e);
            }
        } catch (Exception e) {
            logger.error("send post request to url: " + url + " with parameters: " + params + " exception!", e);
        }
        return str;
    }

    private static List<NameValuePair> getNameValuePairArr(
            Map<String, String> parasMap) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> parasEntry : parasMap.entrySet()) {
            String parasName = parasEntry.getKey();
            String parasValue = parasEntry.getValue();
            nvps.add(new BasicNameValuePair(parasName, parasValue));
        }
        return nvps;
    }

    /**
     * description: post请求
     * param: [urlStr, postData为json字符串]
     * return: java.lang.String
     * time: 2018/6/29 13:40
     */
    public static String post(String urlStr, String postData) {
        String result = null;
        try {
            //创建连接
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=gbk");
            connection.connect();
            //POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(postData.getBytes("UTF-8"));
            out.flush();
            out.close();
            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            result = sb.toString();
            logger.info(sb);
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (Exception e) {
            logger.error("send post request to url: " + urlStr + " with parameters: " + postData + " exception!", e);
        }
        return result;
    }

}
