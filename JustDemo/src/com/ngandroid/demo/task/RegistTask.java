package com.ngandroid.demo.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ngandroid.demo.R;
import com.ngandroid.demo.content.ErrorResponse;
import com.ngandroid.demo.content.LoginEntry;
import com.ngandroid.demo.content.Response;
import com.ngandroid.demo.content.UserResponse;
import com.ngandroid.demo.util.HttpUtil;
import com.ngandroid.demo.util.XMLDomUtil;

public class RegistTask extends AsyncTask<LoginEntry, String, Response>{

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
     * @return
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected Response doInBackground(LoginEntry... params) {
        //显示登陆的进度条
        this.publishProgress(mContext.getResources().getString(R.string.login_waiting));
        XMLDomUtil domUtil = new XMLDomUtil();
        Response response = null;
        //发送登陆的POST请求
        response = domUtil.parseUserXml(new HttpUtil().post(
                LoginEntry.uriAPI, params[0].getPostBody()));
        Log.v(TAG, "parseUserXml");
        return response;
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
        	Toast.makeText(mContext, reason+"登陆失败！", Toast.LENGTH_SHORT).show();
        }else{
        	UserResponse userRsp = (UserResponse)result;
	        Log.v(TAG, "uid:"+userRsp.uid + " email:" + userRsp.email+" expiretime:" + userRsp.expiretime+ " nickname:"+userRsp.nickname);
	        Toast.makeText(mContext, userRsp.nickname+"登陆成功！", Toast.LENGTH_SHORT).show();
        }
    }
}
