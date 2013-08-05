package com.ngandroid.demo.content;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.ngandroid.demo.util.MD5Utile;

public abstract class BaseEntry {
	protected static final String DATA_TYPE = "&dataType=1";
	protected static final String DATA_MD5_KEY = "&checksum=";
	public static final String KEY = "5742c5fe1b15a7dffa4a9d83c4698eb0";
	private static final String TAG = "Entry";
	
	private List<PostParam> mParams = new ArrayList<BaseEntry.PostParam>();
	
	private PostParam mTimeParam;
	
	public BaseEntry(){
	}
	
	/**
	 * 将POST参数转化为URL编码
	 * @param source
	 * @return
	 */
	String urlEncode(String source){
		return URLEncoder.encode(source);
	}

	/**
	 * 获取用户参数
	 * @param isUrlEncode 是否需要转化为URL编码
	 * @return
	 */
	public String getParams(boolean isUrlEncode) {
		StringBuffer sb = new StringBuffer();
		for(PostParam param:mParams){
			sb.append(param.key+"=");
			sb.append((isUrlEncode?urlEncode(param.value):param.value)+"&");
		}
		String paramsString = sb.toString().substring(0, sb.toString().length()-1); //去掉最后一个“&”
		Log.v(TAG, paramsString);
		return paramsString + getTimeParam().toString() ;
	}
	
	private PostParam getTimeParam(){
		if(mTimeParam == null){
			PostParam param = new PostParam();
			param.key = "time";
			param.value = ""+getTime();
			mTimeParam = param;
		}
		return mTimeParam;
	}
	
	protected abstract void formatParamas();
	
	public String getPostBody(){
		formatParamas();
		return getParams(false) +getChecksumParam().toString()+DATA_TYPE;
	}
	
	private PostParam getChecksumParam(){
		PostParam param = new PostParam();
		param.key = "checksum";
		param.value = MD5Utile.MD5(getParams(true) +KEY);
		return param;
	}
	
	public void setParams(List<PostParam> params){
		mParams = params;
	}
	
	public void addParam(String key, String value){
		PostParam param = new PostParam();
		param.key = key;
		param.value = value;
		mParams.add(param);
	}
	
	public static long getTime() {
		return  System.currentTimeMillis() / 1000;
	}
	
	public class PostParam{
		public String key;
		public String value;
		public String toString(){
			return "&"+key+"="+value;
		}
	}
}
