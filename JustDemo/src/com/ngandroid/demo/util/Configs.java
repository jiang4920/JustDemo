package com.ngandroid.demo.util;

import android.content.Context;

import com.ngandroid.demo.content.UserResponse;
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
	public static void resetCookie(Context context){
	    mCookie = UserResponse.getUser(context).cookie;
	}
	public static String getCookie(Context context){
	    if(mCookie == null||mCookie.length()==0){
	        mCookie = UserResponse.getUser(context).cookie;
	    }
	    return mCookie;
	}
	
}
