package com.ngandroid.demo.task;

import com.ngandroid.demo.content.UserResponse;
import com.ngandroid.demo.content.plate.PlateGroup;
import com.ngandroid.demo.content.plate.PlateResponse;
import com.ngandroid.demo.util.Configs;
import com.ngandroid.demo.util.HttpUtil;
import com.ngandroid.demo.util.NGAURL;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PlateTask extends AsyncTask<String, String, PlateResponse> {
	private static final String TAG = "JustDemo PlateTask.java";
	Context mContext;
	LinearLayout mPlateLayout;
	public PlateTask(Context context, LinearLayout plateLayout){
	    mContext = context;
	    mPlateLayout = plateLayout;
	}
	
	@Override
	protected PlateResponse doInBackground(String... params) {
		HttpUtil util = new HttpUtil();
		PlateResponse response = new PlateResponse();
		return response.parse(util.post(NGAURL.URL_PLATE+"?uid="+UserResponse.getInstance().uid, null, Configs.getCookie(mContext)));
	}

	@Override
	protected void onPostExecute(PlateResponse result) {
	    if(result != null){
	        for(PlateGroup group:result.getResult()){
	            mPlateLayout.addView(group.getView(mContext), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	            Log.v(TAG, "group:"+group.name);
	        }
	    }else{
	        Toast.makeText(mContext, "登陆失败,请检查网络！", Toast.LENGTH_SHORT).show();
	    }
	}
	
	
}
