package com.ngandroid.demo.topic.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ngandroid.demo.R;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.topic.content.ReplyData;
import com.ngandroid.demo.topic.content.ReplyListData;
import com.ngandroid.demo.topic.content.TopicData;
import com.ngandroid.demo.topic.content.UserInfoData;
import com.ngandroid.demo.util.Configs;
import com.ngandroid.demo.util.HttpUtil;
import com.ngandroid.demo.util.NGAURL;
import com.ngandroid.demo.util.SQLiteUtil;
import com.ngandroid.demo.util.Utils;

public class TopicReadTask extends AsyncTask<String, Integer, Integer> {

    public static final String TAG = TopicReadTask.class.getSimpleName();
    private Context mContext = null;
    private IDataLoadedListener mDataListener = null;
    private ReplyListData mReplyListData = null;

    private final static Integer SUCCESS = 0;
    private final static Integer TIMEOUT = 1;
    private final static Integer DATAERROR = 2;
    private final static Integer NETERROR = 3;
    private final static Integer SERVERERROR = 4;
    private final static Integer FORBIDDEN = 5;
    private final static Integer OTHERERROR = 6;

    /** 帖子*/
    private TopicData mTopicData;
    
    public TopicReadTask(TopicData topicData,Context context, IDataLoadedListener listener) {
        mContext = context;
        mDataListener = listener;
        mTopicData = topicData;
    }


    @Override
    protected Integer doInBackground(String... params) {
        String page = params[0];
        String url = NGAURL.URL_BASE + "/read.php?lite=js&noprefix&tid=" + mTopicData.getTid()
                + "&_ff=" + mTopicData.getFid() + "&page=" + page;
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent", HttpUtil.USER_AGENT);
        httpGet.addHeader("Content-Type", "application/x-www-formurlencoded");
        httpGet.addHeader("Accept-Charset", "GBK");
        httpGet.addHeader("Accept-Encoding", "gzip,deflate");
        httpGet.addHeader("Cookie", Configs.getCookie(mContext));

        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
        HttpConnectionParams.setSoTimeout(httpParams, 15000);

        DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);

        // 使不自动重定向
        httpclient.setRedirectHandler(new RedirectHandler() {

            @Override
            public URI getLocationURI(HttpResponse response, HttpContext context)
                    throws ProtocolException {
                return null;
            }

            @Override
            public boolean isRedirectRequested(HttpResponse response,
                    HttpContext context) {
                return false;
            }

        });
        Log.d(TAG, url);
        try {
            HttpResponse response = httpclient.execute(httpGet);
            response.getStatusLine();
            if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {

                InputStream is = response.getEntity().getContent();

                Header[] headers = response.getHeaders("Content-Encoding");
                String contentEncoding = "";
                if (headers.length > 0) {
                    contentEncoding = headers[0].getValue();
                }
                if ("gzip".equals(contentEncoding)) {
                    is = new GZIPInputStream(is);
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        is, "GBK"));
                String data = "";
                StringBuffer sb = new StringBuffer();
                while ((data = br.readLine()) != null) {
                    sb.append(data);
                }
                String strResult = sb.toString();

                Log.d(TAG, strResult);
                saveToDb(SQLiteUtil.getInstance(mContext), mTopicData.getTid(), strResult, Integer.parseInt(page));
                formatData(strResult);
                return SUCCESS;
            } else if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                return SERVERERROR;
            } else if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_FORBIDDEN) {
                return FORBIDDEN;
            }
            return OTHERERROR;
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            return TIMEOUT;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return TIMEOUT;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return NETERROR;
        } catch (IOException e) {
            e.printStackTrace();
            return DATAERROR;
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

    public void saveToDb(SQLiteDatabase db, int tid, String result, int page){
        ContentValues cv = new ContentValues();
        cv.put("tid", tid);
        cv.put("topic", JSON.toJSONString(mTopicData));
        cv.put("content", result);
        cv.put("page", page);
        cv.put("readtime", System.currentTimeMillis());//阅读时的本地时间,以便阅读历史记录排序
        if(check(db, SQLiteUtil.TABLE_TOPIC_HISTORY, "tid = "+tid+" and page = "+page)){
            db.update(SQLiteUtil.TABLE_TOPIC_HISTORY, cv, "tid = "+tid+" and page = "+page, null);
        }else{
            db.insert(SQLiteUtil.TABLE_TOPIC_HISTORY, null, cv);
        }
    }
    
    private boolean check(SQLiteDatabase db, String table,String where){
        Cursor c = db.query(table, null, where, null, null, null, null);
        if(c != null && c.getCount() > 0){
            return true;
        }
        c.close();
        return false;
    }
    
    /**
     * <p>
     * Title: formatData
     * </p>
     * <p>
     * Description: 将json数据转化为对象集合
     * </p>
     * 
     * @param strResult
     */
    public void formatData(String strResult) {
        JSONObject jsonRoot = JSON.parseObject(strResult);
        JSONObject dataObject = jsonRoot.getJSONObject("data");

        JSONObject __U = dataObject.getJSONObject("__U");

        mReplyListData = new ReplyListData();
        Map<String, UserInfoData> userMap = new HashMap<String, UserInfoData>();
        for (String key : __U.keySet()) {
            UserInfoData userInfoData = __U.getObject(key, UserInfoData.class);
            userMap.put(key, userInfoData);
        }
        mReplyListData.set__U(userMap);

        JSONObject __R = dataObject.getJSONObject("__R");
        Map<String, ReplyData> replyDataMap = new HashMap<String, ReplyData>();
        for (String key : __R.keySet()) {
            ReplyData replyData = __R.getObject(key, ReplyData.class);
            int type = replyData.getType();
            if (replyData.getType() == 2) {
                replyData
                        .setHtmlContent("<span style='color:#D00;white-space:nowrap'>[隐藏]</span>");

            } else if (type == 1024) {
                replyData
                        .setHtmlContent("<span style='color:#D00;white-space:nowrap'>[锁定]</span>");

            } else {
                boolean loadImg = true;
                SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(mContext);
                Boolean isLoadImage = prefs.getBoolean("is_load_image", false);
                if (Utils.getNetworkType(mContext) == Utils.NetworkType.MOBILE
                        && !isLoadImage) {
                    loadImg = false;
                }
                if(loadImg){
                    
                }
                if(replyData.getAttachs()!= null){
                    for(String k:replyData.getAttachs().keySet()){
                        Log.v(TAG, "attach:"+replyData.getAttachs().get(k).getAttachurl());
                    }
                }
                if(replyData.getAttachHtml() == null){
                    replyData.setHtmlContent(Utils.decodeForumTag(
                            replyData.getContent(), loadImg));
                }else{
                    replyData.setHtmlContent(Utils.decodeForumTag(
                            replyData.getContent(), loadImg)+replyData.getAttachHtml());
                }
                Log.v(TAG, "html:"+replyData.getHtmlContent());
            }
            replyDataMap.put(key, replyData);
        }
        
        mReplyListData.set__R(replyDataMap);

        mReplyListData.set__R__ROWS(dataObject.getIntValue("__R__ROWS"));
        mReplyListData.set__R__ROWS_PAGE(dataObject
                .getIntValue("__R__ROWS_PAGE"));
        mReplyListData.set__ROWS(dataObject.getIntValue("__ROWS"));
        mReplyListData.setTime(jsonRoot.getLongValue("time"));
    }

    @Override
    protected void onPostExecute(Integer status) {
        if (status == SUCCESS) {
            mDataListener.onPostFinished(mReplyListData);
        } else {
            mDataListener.onPostError(status);
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

}
