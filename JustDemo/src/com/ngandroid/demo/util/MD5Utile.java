package com.ngandroid.demo.util;

import java.security.MessageDigest;

public class MD5Utile {

	public final static String MD5(String s) { 
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
		'a', 'b', 'c', 'd', 'e', 'f' }; 
		try { 
		   byte[] strTemp = s.getBytes(); 
		   //使用MD5创建MessageDigest对象 
		   MessageDigest mdTemp = MessageDigest.getInstance("MD5"); 
		   mdTemp.update(strTemp); 
		   byte[] md = mdTemp.digest(); 
		   int j = md.length; 
		   char str[] = new char[j * 2]; 
		   int k = 0; 
		   for (int i = 0; i < j; i++) { 
		    byte b = md[i]; 
		    //System.out.println((int)b); 
		    //将没个数(int)b进行双字节加密 
		    str[k++] = hexDigits[b >> 4 & 0xf]; 
		    str[k++] = hexDigits[b & 0xf]; 
		   } 
		   return new String(str); 
		} catch (Exception e) {return null;} 
		} 
	
	/**
	 * 生成MD5
	 * @param source
	 * @return MD5值
	 */
	public static String md5(String source) {
		StringBuffer sb = new StringBuffer(32);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(source.getBytes("utf-8"));
			for (int i = 0; i < array.length; i++) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.toUpperCase().substring(1, 3));
			}
		} catch (Exception e) {
			return null;
		}
		return sb.toString();
	}
}
