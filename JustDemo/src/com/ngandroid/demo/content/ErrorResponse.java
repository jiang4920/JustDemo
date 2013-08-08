package com.ngandroid.demo.content;

import org.dom4j.Document;

public class ErrorResponse extends Response {
	private static final String TAG = "JustDemo ErrorPOJO.java";
	
	private String mReason;
	
	public void setSeason(String reason){
		mReason = reason;
	}
	
	public String getReason(){
		return mReason;
	}

    /**
     * <p>Title: parse</p>
     * <p>Description: </p>
     * @param doc
     * @see com.ngandroid.demo.content.Response#parse(org.dom4j.Document)
     */
    @Override
    public Response parse(Document doc) {
        return this;
    }
	
}
