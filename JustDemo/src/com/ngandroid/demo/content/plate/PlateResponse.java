package com.ngandroid.demo.content.plate;

import java.io.IOException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import android.test.IsolatedContext;
import android.util.Log;

public class PlateResponse {
	private static final String TAG = "JustDemo PlateResponse.java";

	public PlateResponse parse(InputStream inputStream) {
		SAXReader reader = new SAXReader();
		Document doc;
		byte[] b = new byte[1024];
		try {
		while(true){
				int len = inputStream.read(b);
				if(len<=0){
					break;
				}
				Log.v(TAG, ""+new String(b, 0 ,len));
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
//		try {
//			doc = reader.read(inputStream);
//			Log.v(TAG, doc.asXML());
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
		return null;
	}
	
	
	
}
