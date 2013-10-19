/**
 * Plate.java[V 1.0.0]
 * classes : com.ngandroid.demo.content.plate.Plate
 * jiangyuchen Create at 2013-10-18 上午10:06:49
 */
package com.ngandroid.demo.content.plate;

import com.ngandroid.demo.util.NGAURL;

import android.graphics.Bitmap;

/**
 * com.ngandroid.demo.content.plate.Plate
 * @author jiangyuchen
 *
 * create at 2013-10-18 上午10:06:49
 */
public class Plate {
    private static final String TAG = "JustDemo Plate";
    /** 子板块id*/
    int fid;
    /** 名称*/
    String name;
    /** 描述*/
    String info;
    /** 中文简称*/
    String nameshort;
    
    int heightlight;
    
    Bitmap icon;
    
    public String getIconUrl(){
    	if(fid== -7){
    		return NGAURL.URL_BASE_ICON+354+".png";
    	}
    	if(fid== 311){
    		return NGAURL.URL_BASE_ICON+414+".png";
    	}
        return NGAURL.URL_BASE_ICON+fid+".png";
    }
}
