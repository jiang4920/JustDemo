package com.ngandroid.demo.task;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
    ProgressDialog pd;

    public LoginTask(LoginActivity context) {
        mContext = context;
        pd = new ProgressDialog(mContext);
    }

    /**
     * <p>
     * Title: doInBackground
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param params
     * @return
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected Response doInBackground(LoginEntry... params) {
        // 显示登陆的进度条
        this.publishProgress(mContext.getResources().getString(
                R.string.login_waiting));
        XMLDomUtil domUtil = new XMLDomUtil();
        // 发送登陆的POST请求
        return domUtil
                .parseXml(
                        new UserResponse(),
                        new HttpUtil().post(LoginEntry.uriAPI,
                                params[0].getPostBody()));
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
            Log.v(TAG, "uid:" + userRsp.uid + " email:" + userRsp.email
                    + " expiretime:" + userRsp.expiretime + " nickname:"
                    + userRsp.nickname);
            Toast.makeText(mContext, userRsp.nickname + "登陆成功！",
                    Toast.LENGTH_SHORT).show();
            userRsp.addNewUser(SQLiteUtil.getInstance(mContext));
        }
    }
    
}
