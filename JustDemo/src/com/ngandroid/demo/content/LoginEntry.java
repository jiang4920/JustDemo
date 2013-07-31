package com.ngandroid.demo.content;

import java.net.URLEncoder;
import java.util.HashMap;

import android.util.Log;

import com.ngandroid.demo.util.MD5Utile;

public class LoginEntry {
	private static final String TAG = "JustDemo LoginEntry.java";
	
	public String email = "";
	public String password = "";
	private String time = "";
	private String checksum = "";
	static final String dataType = "0";
	
	public static final String KEY = "5742c5fe1b15a7dffa4a9d83c4698eb0";
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("email=");
		sb.append(email);
		sb.append("&password=");
		sb.append(password);
		sb.append("&time=");
		sb.append(""+ getTime());
		checksum = MD5Utile.MD5(URLEncoder.encode(sb.toString())+KEY);
		if(checksum.equals(MD5Utile.md5(URLEncoder.encode(sb.toString())+KEY))){
			Log.v(TAG, "equals" +checksum);
		}else{
			Log.v(TAG, "! equals" +checksum +" || "+MD5Utile.md5(URLEncoder.encode(sb.toString())+KEY));
		}
		sb.append("&checksum=");
		sb.append(checksum);
		sb.append("&dataType=");
		sb.append(dataType);
		Log.v(TAG, sb.toString());
		return sb.toString();
	}
	
	public HashMap<String, String> getMap(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("password", password);
		map.put("time", getTime());
		
		StringBuffer sb = new StringBuffer();
		sb.append("email=");
		sb.append(email);
		sb.append("&password=");
		sb.append(password);
		sb.append("&time=");
		sb.append(""+ getTime());
		checksum = MD5Utile.MD5(URLEncoder.encode(sb.toString())+KEY);
		Log.v(TAG, "checksum:"+checksum);
		map.put("checksum", checksum);
		map.put("dataType", dataType);
		return map;
	}
	
	public String getTime(){
		time =  ""+System.currentTimeMillis();
		return time;
	}
	
}
