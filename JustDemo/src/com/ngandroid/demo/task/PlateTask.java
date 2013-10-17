package com.ngandroid.demo.task;

import com.ngandroid.demo.content.UserResponse;
import com.ngandroid.demo.content.plate.PlateResponse;
import com.ngandroid.demo.util.HttpUtil;
import com.ngandroid.demo.util.NGAURL;

import android.os.AsyncTask;

public class PlateTask extends AsyncTask<String, String, PlateResponse> {
	private static final String TAG = "JustDemo PlateTask.java";

	@Override
	protected PlateResponse doInBackground(String... params) {
		HttpUtil util = new HttpUtil();
		PlateResponse response = new PlateResponse();
		return response.parse(util.post(NGAURL.URL_PLATE+"?uid="+UserResponse.getInstance().uid, null));
	}

	@Override
	protected void onPostExecute(PlateResponse result) {
		super.onPostExecute(result);
	}
	
	
}
