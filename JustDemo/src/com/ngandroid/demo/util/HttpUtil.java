package com.ngandroid.demo.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class HttpUtil {

    private static final String TAG = "JustDemo HttpUtil";
	public static final String USER_AGENT = "nga_178_com_app";
	
	public static String COOKIE = null;
	
    public InputStream post(String postUrl, String body) {
    	Log.v(TAG, "body :"+body);
    	if(body == null){
    		body = "";
    	}
        try {
            //创建连接
            URL url = new URL(postUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestProperty("Cookie", COOKIE);  
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            connection.setRequestProperty("Content-Length", String.valueOf(body.length()));
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            if(!body.equals("")){
            	connection.setDoOutput(true);
            }
            connection.setDoInput(true);
            if(!body.equals("")){
            	connection.setRequestMethod("POST");
            }else{
            	connection.setRequestMethod("GET");
            }
            connection.setUseCaches(false);
            connection.setRequestProperty("User-Agent", USER_AGENT);
            Log.v(TAG, "cookie:"+COOKIE);
            connection.connect();
            //POST请求
            if(!body.equals("")){
	            DataOutputStream out = new DataOutputStream(
	                    connection.getOutputStream());
	            out.writeBytes(body);
	            out.flush();
	            out.close();
            }
            if(COOKIE== null){
	            String cookieVal = "";  
	            String uid = "";
	            String cid = "";
	            String key = null;  
	            //取cookie  
	            for(int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++){  
	                if(key.equalsIgnoreCase("set-cookie")){  
	                    cookieVal = connection.getHeaderField(i);  
	                    cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));  
	                    if (cookieVal.indexOf("_sid=") == 0) {
							cid = cookieVal.substring(5);
						}
						if (cookieVal.indexOf("_178c=") == 0) {
							uid = cookieVal
									.substring(6, cookieVal.indexOf('%'));
						}
	                }  
	            }  
	            
	            COOKIE = "ngaPassportUid=" + uid
	    				+ "; ngaPassportCid=" +cid;
            }
            return connection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
