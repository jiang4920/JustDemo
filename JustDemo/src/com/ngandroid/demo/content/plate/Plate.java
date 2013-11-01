/**
 * Plate.java[V 1.0.0]
 * classes : com.ngandroid.demo.content.plate.Plate
 * jiangyuchen Create at 2013-10-18 上午10:06:49
 */
package com.ngandroid.demo.content.plate;

import java.io.Serializable;

import com.ngandroid.demo.util.NGAURL;

import android.graphics.Bitmap;

/**
 * com.ngandroid.demo.content.plate.Plate
 * @author jiangyuchen
 *
 * create at 2013-10-18 上午10:06:49
 */
public class Plate implements Serializable {
    /** serialVersionUID*/
    private static final long serialVersionUID = 1L;
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
    
    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getNameshort() {
        return nameshort;
    }

    public void setNameshort(String nameshort) {
        this.nameshort = nameshort;
    }

    public int getHeightlight() {
        return heightlight;
    }

    public void setHeightlight(int heightlight) {
        this.heightlight = heightlight;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

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
