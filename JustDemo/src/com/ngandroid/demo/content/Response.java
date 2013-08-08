package com.ngandroid.demo.content;

import org.dom4j.Document;

public abstract class Response {
	private static final String TAG = "JustDemo BaseResponse.java";
	
	public abstract Response parse(Document doc);
}
