package com.ngandroid.demo.util;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class Configs {
	private static final String TAG = "JustDemo Configs.java";
	
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();
		ImageLoader.getInstance().init(config);
	}
	
	private static String mCookie = "";
	public static String getCookie(Context context){
	    if(mCookie == null||mCookie.length()==0){
    	    Cursor c = SQLiteUtil.getInstance(context).query(SQLiteUtil.TABLE_USER, null, null, null, null, null, null);
            for(c.moveToFirst(); !c.isAfterLast();c.moveToNext()){
                Log.v(TAG, "uid:"+c.getString(c.getColumnIndex("uid")) + " email:"+c.getString(c.getColumnIndex("email"))+" expiretime"+ c.getString(c.getColumnIndex("expiretime"))
                        +" nickname:"+c.getString(c.getColumnIndex("nickname"))+" loginTime:"+ c.getString(c.getColumnIndex("loginTime"))
                        + " keepLogin:"+c.getString(c.getColumnIndex("keepLogin"))+" cookie:"+(mCookie = c.getString(c.getColumnIndex("cookie")))
                        );
            }
            c.close();
	    }
	    return mCookie;
	}
	
}
