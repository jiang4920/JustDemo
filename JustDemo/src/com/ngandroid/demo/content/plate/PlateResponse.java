package com.ngandroid.demo.content.plate;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import android.util.Log;

public class PlateResponse{
	private static final String TAG = "JustDemo PlateResponse.java";

	private ArrayList<PlateGroup> groupList = new ArrayList<PlateGroup>();
	
	public static final int RESULT_STATE_ERROR = 0;
	public static final int RESULT_STATE_SUCCESS = 1;
	
	private int state = 0;
	public int getState(){
	    return state;
	}
	
	public ArrayList<PlateGroup> getResult(){
	    return groupList;
	}
	
	public PlateResponse parse(InputStream inputStream) {
		SAXReader reader = new SAXReader();
		Document doc;
        try {
            doc = reader.read(new InputStreamReader(inputStream, "GBK"));
            Log.v(TAG, doc.asXML());
            Element root = doc.getRootElement();
            for (Iterator<Element> iter = root.elementIterator(); iter.hasNext();) {
                Element element = iter.next();
                for (Iterator<Element> iterInner = element.elementIterator(); iterInner
                        .hasNext();) {
                    PlateGroup group = new PlateGroup();
                    Element elementInner = iterInner.next();
                    group.name = elementInner.attributeValue("name");
                    for (Iterator<Element> plateIterator = elementInner.elementIterator(); plateIterator
                            .hasNext();) {
                        Plate plate = new Plate();
                        Element elementPlate = plateIterator.next();
                        plate.fid = Integer.parseInt(elementPlate.attributeValue("fid"));
                        plate.name = elementPlate.attributeValue("name");
                        plate.nameshort = elementPlate.attributeValue("nameshort");
                        plate.info = elementPlate.attributeValue("info");
                        try{
                            plate.heightlight = Integer.parseInt(elementPlate.attributeValue("heightlight"));
                        }catch (Exception e) {
                        }
                        if(group.name == null || group.name.length()==0){
                            group.name =  plate.name;   //有些group节点没有name，将第一个板块的名字赋值给group
                        }
                        group.add(plate);
                    }
                    groupList.add(group);
                }
            }
            for(PlateGroup g:groupList){
                Log.v(TAG, "group="+g.name);
                for(Plate p:g){
                    Log.v(TAG, "name="+p.name+"     fid="+p.fid);
                }
            }
            state = RESULT_STATE_SUCCESS;
            return this;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        state = RESULT_STATE_ERROR;
		return null;
	}
	
	
	
}
