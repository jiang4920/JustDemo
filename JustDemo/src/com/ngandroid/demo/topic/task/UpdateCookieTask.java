/**
 * UpdateCookieTask.java[V 1.0.0]
 * classes : com.ngandroid.demo.topic.task.UpdateCookieTask
 * jiangyuchen Create at 2013-10-30 下午2:58:45
 */
package com.ngandroid.demo.topic.task;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import com.ngandroid.demo.util.HttpUtil;

/**
 * com.ngandroid.demo.topic.task.UpdateCookieTask
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-10-30 下午2:58:45
 */
public class UpdateCookieTask extends AsyncTask<String, String, String> {
    private static final String TAG = "JustDemo UpdateCookieTask";

    
    
    @Override
    protected String doInBackground(String... params) {

        try {
            HttpGet httpGet = new HttpGet(
                    "http://bbs.ngacn.cc/nuke.php?func=login&uid=24545785&cid=76f6f90a46c9c51fa61f15a1ede38ff7b194d774");
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
            Log.v(TAG, "cookie:"+cookie.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
