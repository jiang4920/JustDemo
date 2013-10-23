package com.ngandroid.demo.topic.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ngandroid.demo.R;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.topic.content.SubForumData;
import com.ngandroid.demo.topic.content.SubForumListData;
import com.ngandroid.demo.topic.content.TopicData;
import com.ngandroid.demo.topic.content.TopicListData;
import com.ngandroid.demo.util.Configs;
import com.ngandroid.demo.util.HttpUtil;
import com.ngandroid.demo.util.NGAURL;

public class TopicListTask extends AsyncTask<String, Integer, Integer> {

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

	public TopicListTask(Context context, IDataLoadedListener dataListener) {
		mContext = context;
		mDataListener = dataListener;
	}
	public static final String USER_AGENT = "AndroidNga/460";
	@Override
	protected Integer doInBackground(String... params) {
		String fid = params[0];
		String page = params[1];
		String param = params[2];
		String url = NGAURL.URL_BASE + "/thread.php?lite=js&noprefix&"+param+"&fid=" + fid
				+ "&page=" + page;

		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("User-Agent", USER_AGENT);
		httpGet.addHeader("Content-Type", "application/x-www-formurlencoded");
		httpGet.addHeader("Accept-Charset", "GBK");
		httpGet.addHeader("Accept-Encoding", "gzip,deflate");
		httpGet.addHeader("Cookie",  Configs.getCookie(mContext));
		Log.v(TAG, "Cookie:"+ Configs.getCookie(mContext));
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
			Log.d(TAG, "" + response.getStatusLine().getStatusCode());
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

				JSONObject jsonRoot = JSON.parseObject(strResult);
				JSONObject jsonObject = jsonRoot.getJSONObject("data");

				SubForumListData subForumListData = new SubForumListData();

				JSONObject __F = jsonObject.getJSONObject("__F");

				subForumListData.set__SELECTED_FORUM(__F
						.getString("__SELECTED_FORUM"));
				subForumListData.set__UNION_FORUM(__F
						.getString("__UNION_FORUM"));
				subForumListData.set__UNION_FORUM_DEFAULT(__F
						.getString("__UNION_FORUM_DEFAULT"));
				subForumListData.setTopped_topic(__F.getString("topped_topic"));
				try{
					subForumListData.setFid(__F.getInteger("fid"));
				}catch (Exception e) {
				}
				String __UNION_FORUM = subForumListData.get__UNION_FORUM();

				if (__UNION_FORUM != null && !"".equals(__UNION_FORUM)) {
					String[] subForumIDs = __UNION_FORUM.split(",");
					int subForumListCount = subForumIDs.length;
					List<SubForumData> subForumList = new ArrayList<SubForumData>();
					Object subForumObj = __F.get("sub_forums");
					if (subForumObj instanceof JSONObject) {
						JSONObject subForumListJson = (JSONObject) subForumObj;
						for (int i = 0; i < subForumListCount; i++) {
							subForumList.add((SubForumData) JSONObject
									.toJavaObject(
											subForumListJson.getJSONObject(i
													+ ""), SubForumData.class));
						}
						subForumListData.setSub_forums(subForumList);
					}

				}
				TopicListData topicListData = new TopicListData();
                topicListData.set__F(subForumListData);
				topicListData.set__R__ROWS_PAGE(jsonObject
						.getInteger("__R__ROWS_PAGE"));
				topicListData.set__T__ROWS_PAGE(jsonObject
						.getInteger("__T__ROWS_PAGE"));
				topicListData.set__T__ROWS(jsonObject.getInteger("__T__ROWS"));
				topicListData.set__ROWS(jsonObject.getInteger("__ROWS"));
				topicListData.setTime(jsonRoot.getLong("time"));
				List<TopicData> topicDataList = new ArrayList<TopicData>();
				for (int i = 0; i < topicListData.get__T__ROWS(); i++) {
				    TopicData dataTmp = (TopicData) JSONObject.toJavaObject(
                            jsonObject.getJSONObject("__T").getJSONObject(
                                    i + ""), TopicData.class);
				    if(dataTmp != null){
				        topicDataList.add(dataTmp);
				    }else{
				        Log.v(TAG, "TopicData item ["+i+"] is NULL");
				    }
				}
				topicListData.setTopicList(topicDataList);
				mTopicListData = topicListData;
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
			// 关闭连接,释放资源
			httpclient.getConnectionManager().shutdown();
		}
	}

	@Override
	protected void onPostExecute(Integer status) {
		if (status == SUCCESS) {
			mDataListener.onPostFinished(mTopicListData);
		} else {
			mDataListener.onPostError(status);
//				Toast.makeText(
//						mContext.getApplicationContext(),
//								"获取数据失败，请检测网络", Toast.LENGTH_SHORT)
//						.show();
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
