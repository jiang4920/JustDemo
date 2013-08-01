package com.ngandroid.demo.content;

import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;

import com.ngandroid.demo.util.MD5Utile;

public class LoginEntry {
	private static final String TAG = "JustDemo LoginEntry.java";
	
	public String email = "";
	public String password = "";
	private String time = "";
	private String checksum = "";
	static final String dataType = "1";
	
	public static final String KEY = "5742c5fe1b15a7dffa4a9d83c4698eb0";
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("email=");
		sb.append(email);
		sb.append("&password=");
		sb.append(password);
		sb.append("&time=");
		sb.append(""+ getTime());
		String tmp = parse("@", sb.toString());
		System.out.println("md5 source:"+tmp);
		checksum = MD5Utile.MD5(tmp+KEY);
		sb.append("&checksum=");
		sb.append(checksum);
		sb.append("&dataType=");
		sb.append(dataType);
//		Log.v(TAG, sb.toString());
//		String tmp2 = parse("@", sb.toString());
		return sb.toString();
	}
	
	public String parse(String reg, String str){
	    return str.toString().replaceAll(reg, URLEncoder.encode(reg));
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
//		Log.v(TAG, "checksum:"+checksum);
		map.put("checksum", checksum);
		map.put("dataType", dataType);
		return map;
	}
	
	public String getTime(){
		time =  ""+System.currentTimeMillis()/1000;
		return time;
	}
	
}
