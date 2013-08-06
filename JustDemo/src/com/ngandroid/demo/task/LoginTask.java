package com.ngandroid.demo.task;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ngandroid.demo.R;
import com.ngandroid.demo.content.LoginEntry;
import com.ngandroid.demo.content.UserEntry;
import com.ngandroid.demo.util.HttpUtil;
import com.ngandroid.demo.util.XMLDomUtil;

public class LoginTask extends AsyncTask<LoginEntry, String, UserEntry>{

    private static final String TAG = "LoginTask";
    private Activity mContext;
    ProgressDialog pd;
    public LoginTask(Activity context){
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
    protected UserEntry doInBackground(LoginEntry... params) {
        //显示登陆的进度条
        this.publishProgress(mContext.getResources().getString(R.string.login_waiting));
        XMLDomUtil domUtil = new XMLDomUtil();
        UserEntry user = null;
        try {
            //发送登陆的POST请求
            user = domUtil.getUserEntry(new HttpUtil().post(
                    LoginEntry.uriAPI, params[0].getPostBody()));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
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
    protected void onPostExecute(UserEntry result) {
        super.onPostExecute(result);
        Log.v(TAG, result.nickname);
        pd.dismiss();
        Toast.makeText(mContext, result.nickname+"登陆成功！", Toast.LENGTH_SHORT).show();
    }
}
