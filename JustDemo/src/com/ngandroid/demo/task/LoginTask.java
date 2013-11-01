package com.ngandroid.demo.task;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ngandroid.demo.GuideActivity;
import com.ngandroid.demo.LoginActivity;
import com.ngandroid.demo.R;
import com.ngandroid.demo.content.ErrorResponse;
import com.ngandroid.demo.content.LoginEntry;
import com.ngandroid.demo.content.Response;
import com.ngandroid.demo.content.UserResponse;
import com.ngandroid.demo.util.HttpUtil;
import com.ngandroid.demo.util.SQLiteUtil;
import com.ngandroid.demo.util.XMLDomUtil;

public class LoginTask extends AsyncTask<LoginEntry, String, Response> {

    private static final String TAG = "LoginTask";
    private LoginActivity mContext;
    private String mCookie;
    ProgressDialog pd;

    public LoginTask(LoginActivity context) {
        mContext = context;
        pd = new ProgressDialog(mContext);
    }

    String mPasswd;
    String mUsername;
    String mCid;
    @Override
    protected Response doInBackground(LoginEntry... params) {
        mPasswd = params[0].getPassword();
        mUsername = params[0].getEmail();
        // 显示登陆的进度条
        this.publishProgress(mContext.getResources().getString(
                R.string.login_waiting));
        XMLDomUtil domUtil = new XMLDomUtil();
        // 发送登陆的POST请求
        try {
            HttpPost httpPost = new HttpPost(LoginEntry.uriAPI);
            httpPost.setHeader("Content-Type",
                    "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent", HttpUtil.USER_AGENT);
            httpPost.setHeader("Accept-Charset", "UTF-8");
            httpPost.setEntity(params[0].getEntiry());
            HttpResponse httpResponse;
            httpResponse = new DefaultHttpClient().execute(httpPost);
            for(Header header:httpResponse.getAllHeaders()){
                if(header.getName().equals("Set-Cookie")){
                    String value = header.getValue();
                    if(value.startsWith("_sid=")){
                        Log.v(TAG, "sid:"+value);
                        mCid = value.substring(value.indexOf("_sid=")+5, value.indexOf(";"));
                        Log.v(TAG, mCid);
                    }
                }
            }
            Response resp = domUtil.parseXml(new UserResponse(),
                    httpResponse.getEntity().getContent());
            if(resp instanceof UserResponse){
                UserResponse userRsp = (UserResponse) resp;
                mCookie = getCookie(userRsp.uid);
            }
            return resp;
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String getCookie(int uid) throws ClientProtocolException, IOException{
        HttpGet httpGet = new HttpGet(
                "http://bbs.ngacn.cc/nuke.php?func=login&uid="+uid+"&cid="+mCid);
        httpGet.setHeader("Content-Type",
                "application/x-www-form-urlencoded");
        httpGet.setHeader("User-Agent", HttpUtil.USER_AGENT);
        httpGet.setHeader("Accept-Charset", "GBK");
        HttpResponse httpResponse = new DefaultHttpClient().execute(httpGet);
        Header[] headers = httpResponse.getAllHeaders();
        StringBuffer cookie = new StringBuffer();
        for(Header head : headers){
            if(head.getName().equals("Set-Cookie")){
                cookie.append(head.getValue().split(";")[0]+";");
            }
        }
        return cookie.toString();
    }
    
    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        if (!pd.isShowing()) {
            pd.show();
        }
        pd.setMessage(values[0]);
    }

    @Override
    protected void onPostExecute(Response result) {
        super.onPostExecute(result);
        pd.dismiss();
        if (result == null) {
            Toast.makeText(mContext, "登陆失败,请检查网络！", Toast.LENGTH_SHORT).show();
        } else if (result instanceof ErrorResponse) {
            String reason = ((ErrorResponse) result).getReason();
            Toast.makeText(mContext, reason + "登陆失败！", Toast.LENGTH_SHORT)
                    .show();
        } else {
            UserResponse userRsp = (UserResponse) result;
            userRsp.keepLogin = mContext.isKeepLogin();
            userRsp.password = mPasswd;
            userRsp.cookie = mCookie;
            Log.v(TAG, "uid:" + userRsp.uid + " email:" + userRsp.email
                    + " expiretime:" + userRsp.expiretime + " nickname:"
                    + userRsp.nickname + " cookie:" + userRsp.cookie);
            mContext.finish();
            startPlateActivity(mContext);
            Toast.makeText(mContext, userRsp.nickname + "登陆成功！",
                    Toast.LENGTH_SHORT).show();
            userRsp.addNewUser(SQLiteUtil.getInstance(mContext), mUsername);
        }
    }

    public void startPlateActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, GuideActivity.class);
        context.startActivity(intent);
    }
}
