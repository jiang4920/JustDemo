/**
 * MessageListTask.java[V 1.0.0]
 * classes : com.ngandroid.demo.task.MessageListTask
 * jiangyuchen Create at 2013-10-29 上午10:01:56
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
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.util.Configs;
import com.ngandroid.demo.util.HttpUtil;
import com.ngandroid.demo.util.NGAURL;

/**
 * com.ngandroid.demo.task.MessageListTask
 * @author jiangyuchen
 *
 * create at 2013-10-29 上午10:01:56
 */
public class MessageListTask extends AsyncTask<String, String, SMSMessageList> {
    private static final String TAG = "JustDemo MessageListTask";

    Context mContext;
    IDataLoadedListener mDataLoadedListener;
    public MessageListTask(Context context, IDataLoadedListener iDataLoadedListener){
        mContext = context;
        mDataLoadedListener = iDataLoadedListener;
    }
    
    @Override
    protected SMSMessageList doInBackground(String... params) {
        HttpGet httpGet = new HttpGet(NGAURL.URL_BASE+"/nuke.php?__lib=message&__act=message&act=list&lite=js&page="+params[0]);
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
            String result = sb.substring(sb.indexOf("=")+1).toString();
            Log.d(TAG, result);
            JSONObject obj = JSON.parseObject(result);
            JSONObject data = obj.getJSONObject("data").getJSONObject("0");
            SMSMessageList msgList = new SMSMessageList();
            msgList.setTime(obj.getLong("time"));
            msgList.setCurrentPage(data.getInteger("currentPage"));
            try{
                msgList.setNextPage(data.getInteger("nextPage"));
            }catch (Exception e) {
                Log.v(TAG, "Can not find [nextPage]!");
                msgList.setNextPage(0);
            }
            msgList.setRowsPerPage(data.getInteger("rowsPerPage"));
            for(int i=0; i <Integer.MAX_VALUE; i++){
                SMSMessage messageItem = JSON.toJavaObject(data.getJSONObject(""+i), SMSMessage.class);
                if(messageItem == null){
                    break;
                }
                msgList.add(messageItem);
            }
            Log.v(TAG, "msg size:"+msgList.size());
            return msgList;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        return null;
    }

    @Override
    protected void onPostExecute(SMSMessageList result) {
        super.onPostExecute(result);
        mDataLoadedListener.onPostFinished(result);
    }
    
    
}
