package com.ngandroid.demo.topic.task;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ngandroid.demo.R;
import com.ngandroid.demo.content.PostEntry;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.topic.content.AttachsData;
import com.ngandroid.demo.topic.content.PostAttachmentEntry;
import com.ngandroid.demo.topic.content.PostData;
import com.ngandroid.demo.topic.content.TopicListData;
import com.ngandroid.demo.util.Configs;
import com.ngandroid.demo.util.HttpUtil;

public class PostTask extends AsyncTask<PostData, Integer, Integer> {

    public final static String TAG = "TopicListTask";
    private Context mContext = null;
    private IDataLoadedListener mDataListener = null;
    private TopicListData mTopicListData = null;

    private final static Integer SUCCESS = 0;
    private final static Integer TIMEOUT = 1;
    private final static Integer DATAERROR = 2;
    private final static Integer NETERROR = 3;
    private final static Integer SERVERERROR = 4;
    private final static Integer FORBIDDEN = 5;
    private final static Integer OTHERERROR = 6;

    public PostTask(Context context, IDataLoadedListener dataListener) {
        mContext = context;
        mDataListener = dataListener;
    }

    public static final String USER_AGENT = "AndroidNga/460";

    @Override
    protected Integer doInBackground(PostData... params) {
    	PostData postData = params[0];
        SAXReader reader = new SAXReader();
        try {
//            Document doc = reader.read(new InputStreamReader(postTopic(params[0].getEntry()), "GBK"));
        	Document doc2 = reader.read(new InputStreamReader(postAttachment(postData.getAttachment().getEntry(), postData.getAttachment().getAttachementFile()), "GBK"));
            Log.v(TAG, doc2.asXML());
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer status) {
        if (status == SUCCESS) {
            mDataListener.onPostFinished(mTopicListData);
        } else {
            mDataListener.onPostError(status);
            // Toast.makeText(
            // mContext.getApplicationContext(),
            // "获取数据失败，请检测网络", Toast.LENGTH_SHORT)
            // .show();
            if (status == TIMEOUT) {
                Toast.makeText(
                        mContext.getApplicationContext(),
                        mContext.getResources().getString(
                                R.string.request_timeout), Toast.LENGTH_SHORT)
                        .show();
            } else if (status == DATAERROR) {
                Toast.makeText(
                        mContext.getApplicationContext(),
                        mContext.getResources().getString(
                                R.string.request_dataerror), Toast.LENGTH_SHORT)
                        .show();
            } else if (status == NETERROR) {
                Toast.makeText(
                        mContext.getApplicationContext(),
                        mContext.getResources().getString(
                                R.string.request_neterror), Toast.LENGTH_SHORT)
                        .show();
            } else if (status == SERVERERROR) {
                Toast.makeText(
                        mContext.getApplicationContext(),
                        mContext.getResources().getString(
                                R.string.request_servererror),
                        Toast.LENGTH_SHORT).show();
            } else if (status == FORBIDDEN) {
                Toast.makeText(
                        mContext.getApplicationContext(),
                        mContext.getResources().getString(
                                R.string.request_forbiddenerror),
                        Toast.LENGTH_SHORT).show();
            } else if (status == OTHERERROR) {
                Toast.makeText(
                        mContext.getApplicationContext(),
                        mContext.getResources().getString(
                                R.string.request_othererror),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public InputStream postAttachment(List<NameValuePair> params, File file) {
    	HttpPost httpPost = new HttpPost(PostAttachmentEntry.URL);
    	// 设置HTTP POST请求参数必须用NameValuePair对象
    	// 设置HTTP POST请求参数
    	try {
    		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
    		httpPost.setHeader("User-Agent", HttpUtil.USER_AGENT);
    		httpPost.setHeader("Accept-Charset", "GBK");
    		httpPost.setHeader("Cookie",  Configs.getCookie(mContext));
    		Log.v(TAG, params.toString());
    		
    		FileBody fileBody = new FileBody(file, "multipart/form-data");   
            StringBody stringBody = new StringBody("文件的描述");   
            MultipartEntity entity = new MultipartEntity();   
            for(NameValuePair item:params){
            	entity.addPart(item.getName(), new StringBody(item.getValue(), Charset.forName("GBK")));   
            }
            entity.addPart("file", fileBody);   
            entity.addPart("desc", stringBody);   
//            httpPost.setEntity(new UrlEncodedFormEntity(params, "GBK"));
            httpPost.setEntity(entity);  
    		
    		HttpResponse httpResponse = new DefaultHttpClient().
    				execute(httpPost);  
    		return httpResponse.getEntity().getContent();
    	} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
    	} catch (ParseException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    public InputStream postTopic(List<NameValuePair> params) {
        HttpPost httpPost = new HttpPost(PostEntry.URL);
        // 设置HTTP POST请求参数必须用NameValuePair对象
        // 设置HTTP POST请求参数
        try {
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent", HttpUtil.USER_AGENT);
            httpPost.setHeader("Accept-Charset", "GBK");
            httpPost.setHeader("Cookie",  Configs.getCookie(mContext));
            httpPost.setEntity(new UrlEncodedFormEntity(params, "GBK"));
            HttpResponse httpResponse = new DefaultHttpClient().
                    execute(httpPost);  
            return httpResponse.getEntity().getContent();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
