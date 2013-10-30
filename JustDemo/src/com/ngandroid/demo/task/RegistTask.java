package com.ngandroid.demo.task;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ngandroid.demo.R;
import com.ngandroid.demo.content.ErrorResponse;
import com.ngandroid.demo.content.LoginEntry;
import com.ngandroid.demo.content.RegistEntry;
import com.ngandroid.demo.content.Response;
import com.ngandroid.demo.content.UserResponse;
import com.ngandroid.demo.util.Configs;
import com.ngandroid.demo.util.HttpUtil;
import com.ngandroid.demo.util.XMLDomUtil;

public class RegistTask extends AsyncTask<RegistEntry, String, Response>{

    private static final String TAG = "RegistTask";
    private Activity mContext;
    ProgressDialog pd;
    public RegistTask(Activity context){
        mContext = context;
        pd = new ProgressDialog(mContext);
    }

    /**
     * <p>Title: doInBackground</p>
     * <p>Description: </p>
     * @param params
     * @returnnew HttpUtil().post(RegistEntry.uriAPI, rEntry.getPostBody());
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected Response doInBackground(RegistEntry... params) {
        //显示进度条
        this.publishProgress(mContext.getResources().getString(R.string.login_waiting));
        XMLDomUtil domUtil = new XMLDomUtil();
        //发送登陆的POST请求
        try {
            HttpPost httpPost = new HttpPost(RegistEntry.uriAPI);
            httpPost.setHeader("Content-Type",
                    "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent", HttpUtil.USER_AGENT);
            httpPost.setHeader("Accept-Charset", "UTF-8");
            httpPost.setEntity(params[0].getEntiry());
            HttpResponse httpResponse;
            httpResponse = new DefaultHttpClient().execute(httpPost);
            Response resp = domUtil.parseXml(new UserResponse(),
                    httpResponse.getEntity().getContent());
            return resp;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        if(!pd.isShowing()){
            pd.show();
        }
        pd.setMessage(values[0]);
    }

    @Override       
    protected void onPostExecute(Response result) {
        super.onPostExecute(result);
        pd.dismiss();
        if(result instanceof ErrorResponse){
        	String reason = ((ErrorResponse) result).getReason();
        	Toast.makeText(mContext, reason+"注册失败！", Toast.LENGTH_SHORT).show();
        }else{
        	UserResponse userRsp = (UserResponse)result;
	        Log.v(TAG, "uid:"+userRsp.uid + " email:" + userRsp.email+" expiretime:" + userRsp.expiretime+ " nickname:"+userRsp.nickname);
	        Toast.makeText(mContext, userRsp.uid+"注册成功！", Toast.LENGTH_SHORT).show();
	        mContext.finish();
        }
    }
}
