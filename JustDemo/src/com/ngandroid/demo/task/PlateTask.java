package com.ngandroid.demo.task;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.ngandroid.demo.content.LoginEntry;
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

    public PlateTask(Context context, LinearLayout plateLayout) {
        mContext = context;
        mPlateLayout = plateLayout;
    }

    @Override
    protected PlateResponse doInBackground(String... params) {
        PlateResponse response = new PlateResponse();
        String url = LoginEntry.uriAPI;
        Log.v(TAG, "url:" + url);
        try {
            HttpGet httpGet = new HttpGet(NGAURL.URL_PLATE+"?uid="+UserResponse.getUser(mContext).uid);
            httpGet.setHeader("Content-Type",
                    "application/x-www-form-urlencoded");
            httpGet.setHeader("User-Agent", HttpUtil.USER_AGENT);
            httpGet.setHeader("Accept-Charset", "UTF-8");
            httpGet.setHeader("Cookie", Configs.getCookie(mContext));
            HttpResponse httpResponse;
            httpResponse = new DefaultHttpClient().execute(httpGet);
            return response.parse(httpResponse.getEntity().getContent());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(PlateResponse result) {
        if (result != null) {
            for (PlateGroup group : result.getResult()) {
                mPlateLayout.addView(group.getView(mContext), new LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                Log.v(TAG, "group:" + group.name);
            }
        } else {
            Toast.makeText(mContext, "登陆失败,请检查网络！", Toast.LENGTH_SHORT).show();
        }
    }

}
