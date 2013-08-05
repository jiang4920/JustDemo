package com.ngandroid.demo.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpUtil {

    private static final String TAG = "JustDemo HttpUtil";
	static final String USER_AGENT = "AndroidNga/1.0";
	
    public String post(String postUrl, String body) {
    	Log.v(TAG, body);
        try {
            //创建连接
            URL url = new URL(postUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setInstanceFollowRedirects(true);
//            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(body.length()));
            connection.setRequestProperty("Accept-Charset", "GBK");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.connect();

            //POST请求
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            out.writeBytes(body);
            out.flush();
            out.close();

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
           	Log.v(TAG, sb.toString());
            reader.close();
            // 断开连接
            connection.disconnect();
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
}
