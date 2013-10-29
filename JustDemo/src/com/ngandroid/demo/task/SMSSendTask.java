/**
 * SMSSendTask.java[V 1.0.0]
 * classes : com.ngandroid.demo.task.SMSSendTask
 * jiangyuchen Create at 2013-10-29 下午4:11:18
 */
package com.ngandroid.demo.task;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ngandroid.demo.content.SMSSendEntity;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.util.Configs;
import com.ngandroid.demo.util.HttpUtil;
import com.ngandroid.demo.util.NGAURL;

/**
 * com.ngandroid.demo.task.SMSSendTask
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-10-29 下午4:11:18
 */
public class SMSSendTask extends AsyncTask<String, String, Integer> {
    private static final String TAG = "JustDemo SMSSendTask";

    private SMSSendEntity mEntity;

    private Context mContext;

    private IDataLoadedListener mDataLoadedListener;
    
    public SMSSendTask(Context context, SMSSendEntity entity, IDataLoadedListener iDataLoadedListener) {
        mEntity = entity;
        mContext = context;
        mDataLoadedListener = iDataLoadedListener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        try {
            HttpPost httpPost = new HttpPost(NGAURL.URL_BASE + "/nuke.php");
            httpPost.setHeader("User-Agent", HttpUtil.USER_AGENT);
            httpPost.setHeader("Accept-Charset", "GBK");
            httpPost.setHeader("Cookie", Configs.getCookie(mContext));
            httpPost.setEntity(mEntity.getEntiry());
            HttpResponse response;
            response = new DefaultHttpClient().execute(httpPost);
            return response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if(result == HttpStatus.SC_OK){
            mDataLoadedListener.onPostFinished(null);
        }else{
            mDataLoadedListener.onPostError(0);
        }
    }

}
