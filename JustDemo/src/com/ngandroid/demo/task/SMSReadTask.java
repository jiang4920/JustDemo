/**
 * SMSReadTask.java[V 1.0.0]
 * classes : com.ngandroid.demo.task.SMSReadTask
 * jiangyuchen Create at 2013-10-29 下午1:51:58
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ngandroid.demo.content.SMSMessage;
import com.ngandroid.demo.content.SMSMessageList;
import com.ngandroid.demo.content.SMSReadMessage;
import com.ngandroid.demo.content.SMSReadMessageList;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.util.Configs;
import com.ngandroid.demo.util.HttpUtil;
import com.ngandroid.demo.util.NGAURL;

/**
 * com.ngandroid.demo.task.SMSReadTask
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-10-29 下午1:51:58
 */
public class SMSReadTask extends AsyncTask<String, String, SMSReadMessageList> {
    private static final String TAG = "JustDemo SMSReadTask";

    public SMSReadTask(Context context, SMSMessage message, IDataLoadedListener dataLoadedListener) {
        mSMSMessage = message;
        mContext = context;
        mDataLoadedListener = dataLoadedListener;
    }

    private Context mContext;
    SMSMessage mSMSMessage;
    int curPage;
    private IDataLoadedListener mDataLoadedListener;
    @Override
    protected SMSReadMessageList doInBackground(String... params) {
        curPage = Integer.parseInt(params[0]);
        String url = NGAURL.URL_BASE
                + "/nuke.php?__lib=message&__act=message&act=read&lite=js&page="
                + curPage + "&mid=" + mSMSMessage.getMid();
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", HttpUtil.USER_AGENT);
            httpGet.setHeader("Accept-Charset", "GBK");
            httpGet.setHeader("Cookie", Configs.getCookie(mContext));
            HttpResponse response;
            response = new DefaultHttpClient().execute(httpGet);
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "GBK"));
            StringBuffer sb = new StringBuffer();
            String buffer;
            while ((buffer = br.readLine()) != null) {
                sb.append(buffer);
            }
            String result = sb.substring(sb.indexOf("=") + 1).toString();
            Log.d(TAG, result);
            SMSReadMessageList smsList = new SMSReadMessageList();
            JSONObject root = JSON.parseObject(result);;
            smsList.setTime(root.getLong("time"));
            JSONObject smsListObj = root.getJSONObject("data").getJSONObject("0");
            for(int i=0; i<Integer.MAX_VALUE; i++){
                SMSReadMessage smsReadMessage = JSON.toJavaObject(smsListObj.getJSONObject(""+i), SMSReadMessage.class);
                if(smsReadMessage == null){
                    break;
                }
                smsList.add(smsReadMessage);
            }
            smsList.setStarterUid(smsListObj.getInteger("starterUid"));
            smsList.setCurrentPage(smsListObj.getInteger("currentPage"));
            smsList.setAllUsers(smsListObj.getString("allUsers"));
            try{
                smsList.setNextPage(smsListObj.getInteger("nextPage"));
            }catch (Exception e) {
            }
            return smsList;
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(SMSReadMessageList result) {
        super.onPostExecute(result);
        mDataLoadedListener.onPostFinished(result);
    }
    
    
}
