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

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ngandroid.demo.R;
import com.ngandroid.demo.UserCenterActivity;
import com.ngandroid.demo.content.UserInfoEntity;
import com.ngandroid.demo.util.Configs;
import com.ngandroid.demo.util.HttpUtil;
import com.ngandroid.demo.util.NGAURL;
import com.ngandroid.demo.util.Utils;

/**
 * com.ngandroid.demo.task.UserInfoTask
 * @author jiangyuchen
 *
 * create at 2013-10-25 下午2:55:39
 */
public class UserInfoTask extends AsyncTask<String, String, UserInfoEntity> {
    private static final String TAG = "JustDemo UserInfoTask";
    UserCenterActivity mAactivity;
    
    public UserInfoTask(UserCenterActivity context){
        mAactivity = context;
    }
    
    /**
     * <p>Title: doInBackground</p>
     * <p>Description: </p>
     * @param params
     * @return
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected UserInfoEntity doInBackground(String... params) {
        HttpGet httpGet = new HttpGet(NGAURL.URL_BASE+"/nuke.php?__lib=ucp&__act=get&lite=js&uid="+params[0]);
        httpGet.setHeader("User-Agent", HttpUtil.USER_AGENT);
        httpGet.setHeader("Accept-Charset", "GBK");
        httpGet.setHeader("Cookie",  Configs.getCookie(mAactivity));
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
            UserInfoEntity userInfo = (UserInfoEntity)data.getObject("0", UserInfoEntity.class);
            Log.d(TAG, "username:"+userInfo.username);
            return userInfo;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  

        return null;
    }

    @Override
    protected void onPostExecute(UserInfoEntity result) {
        super.onPostExecute(result);
        View view = mAactivity.findViewById(R.id.usercenter_userinfo);
        mAactivity.bringToFront(view);
        if(result == null){
            return;
        }
        UserViews views = new UserViews(view);
        views.name.setText("用户名:"+result.username);
        views.userId.setText("用户ID:"+result.uid);
        views.group.setText("用户组:"+result.group);
        views.money.setText("金钱:"+result.money);
        views.regdate.setText("注册日期:"+Utils.dateFormat(Long.parseLong(result.regdate)));
        views.sign.setText(result.sign);
        views.rvrc.setText("用户声望:"+result.rvrc);
    }
    
    private class UserViews{
        TextView name;
        TextView userId;
        TextView group;
        TextView money;
        TextView regdate;
        TextView sign;
        TextView rvrc;
        public UserViews(View view){
            name = (TextView)view.findViewById(R.id.userinfo_name);
            userId = (TextView)view.findViewById(R.id.userinfo_userid);
            group = (TextView)view.findViewById(R.id.userinfo_group);
            money = (TextView)view.findViewById(R.id.userinfo_money);
            regdate = (TextView)view.findViewById(R.id.userinfo_regdate);
            sign = (TextView)view.findViewById(R.id.userinfo_sign);
            rvrc = (TextView)view.findViewById(R.id.userinfo_shengwang);
        }
    }
    
}
