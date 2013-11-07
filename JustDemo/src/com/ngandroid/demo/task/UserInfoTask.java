/**
 * UserInfoTask.java[V 1.0.0]
 * classes : com.ngandroid.demo.task.UserInfoTask
 * jiangyuchen Create at 2013-10-25 下午2:55:39
 */
package com.ngandroid.demo.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ngandroid.demo.UserCenterActivity;
import com.ngandroid.demo.content.ErrorEntity;
import com.ngandroid.demo.content.UserInfoEntity;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.util.Configs;
import com.ngandroid.demo.util.HttpUtil;
import com.ngandroid.demo.util.NGAURL;

/**
 * com.ngandroid.demo.task.UserInfoTask
 * @author jiangyuchen
 *
 * create at 2013-10-25 下午2:55:39
 */
public class UserInfoTask extends AsyncTask<String, String, Object> {
    private static final String TAG = "JustDemo UserInfoTask";
    Context mContext;
    IDataLoadedListener mListener;
    public UserInfoTask(Context context, IDataLoadedListener listener){
        mContext = context;
        mListener = listener;
    }
    
    /**
     * <p>Title: doInBackground</p>
     * <p>Description: </p>
     * @param params
     * @return
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected Object doInBackground(String... params) {
        String uid = params[0];
        String username = null;
        if(params.length>1){
            username = params[1];
        }
        HttpGet httpGet = new HttpGet(NGAURL.URL_BASE+"/nuke.php?__lib=ucp&__act=get&lite=js&uid="+uid+"&username="+username);
        httpGet.setHeader("User-Agent", HttpUtil.USER_AGENT);
        httpGet.setHeader("Accept-Charset", "GBK");
        httpGet.setHeader("Cookie",  Configs.getCookie(mContext));
        try {
            HttpResponse response = new DefaultHttpClient().execute(httpGet);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "GBK"));
            StringBuffer sb = new StringBuffer();
            String buffer;
            while((buffer = br.readLine()) != null){
                sb.append(buffer);
            }
            Log.d(TAG, sb.substring(sb.indexOf("=")+1).toString());
            JSONObject obj = JSON.parseObject(sb.substring(sb.indexOf("=")+1).toString());
            JSONObject data = obj.getJSONObject("data");
            if(data != null){
                UserInfoEntity userInfo = (UserInfoEntity)data.getObject("0", UserInfoEntity.class);
                Log.d(TAG, "username:"+userInfo.username);
                return userInfo;
            }
            JSONObject error = obj.getJSONObject("error");
            if(error != null){
                ErrorEntity entity = new ErrorEntity(error.getString("0"));
                return entity;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  

        return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        if(result instanceof ErrorEntity){
            Toast.makeText(mContext, ((ErrorEntity)result).getReason(), Toast.LENGTH_SHORT).show();
            mListener.onPostError(0);
            return;
        }else if(result instanceof UserInfoEntity){
            mListener.onPostFinished(result);
        }
    }
    
}
