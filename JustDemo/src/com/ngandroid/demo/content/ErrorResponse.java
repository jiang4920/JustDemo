package com.ngandroid.demo.content;

public class ErrorResponse extends Response {
	private static final String TAG = "JustDemo ErrorPOJO.java";
	
	private String mReason;
	
	public void setSeason(String reason){
		mReason = reason;
	}
	
	public String getReason(){
		return mReason;
	}
	
}
