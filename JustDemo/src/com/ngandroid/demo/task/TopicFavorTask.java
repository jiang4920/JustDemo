/**
 * TopicFavorTask.java[V 1.0.0]
 * classes : com.ngandroid.demo.task.TopicFavorTask
 * jiangyuchen Create at 2013-11-1 下午5:06:59
 */
package com.ngandroid.demo.task;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ngandroid.demo.R;
import com.ngandroid.demo.content.TopicFavorEntity;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.util.Configs;
import com.ngandroid.demo.util.HttpUtil;
import com.ngandroid.demo.util.NGAURL;

/**
 * com.ngandroid.demo.task.TopicFavorTask
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-11-1 下午5:06:59
 */
public class TopicFavorTask extends AsyncTask<String, String, Integer> {
    private static final String TAG = "JustDemo TopicFavorTask";
    private Context mContext;
    private IDataLoadedListener mDataLoadedListener;
    TopicFavorEntity mEntity;
    public TopicFavorTask(Context context, TopicFavorEntity entity, IDataLoadedListener dataLoadedListener){
        mContext = context;
        mEntity = entity;
        mDataLoadedListener = dataLoadedListener;
    }
    @Override
    protected Integer doInBackground(String... params) {
        try {
            HttpPost httpPost = new HttpPost(NGAURL.URL_BASE + "/nuke.php?");
            httpPost.setHeader("User-Agent", HttpUtil.USER_AGENT);
            httpPost.setHeader("Accept-Charset", "GBK");
            httpPost.setHeader("Cookie", Configs.getCookie(mContext));
            httpPost.setEntity(mEntity.getEntiry());
            HttpResponse response;
            response = new DefaultHttpClient().execute(httpPost);
            Log.v(TAG,
                    new SAXReader().read(
                            new InputStreamReader(response.getEntity()
                                    .getContent(), "GBK")).asXML());
            return response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result == 200) {
            Toast.makeText(mContext, R.string.progress_success,
                    Toast.LENGTH_SHORT).show();
            mDataLoadedListener.onPostFinished(null);
        } else {
            mDataLoadedListener.onPostError(0);
            Toast.makeText(mContext, R.string.progress_error,
                    Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(result);
    }

}
