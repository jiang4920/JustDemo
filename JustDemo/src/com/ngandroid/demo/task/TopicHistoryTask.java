/**
 * TopicHistoryTask.java[V 1.0.0]
 * classes : com.ngandroid.demo.task.TopicHistoryTask
 * jiangyuchen Create at 2013-10-28 下午3:52:55
 */
package com.ngandroid.demo.task;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.topic.content.TopicData;
import com.ngandroid.demo.topic.content.TopicListData;
import com.ngandroid.demo.util.SQLiteUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

/**
 * com.ngandroid.demo.task.TopicHistoryTask
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-10-28 下午3:52:55
 */
public class TopicHistoryTask extends AsyncTask<String, String, TopicListData> {
    private static final String TAG = "JustDemo TopicHistoryTask";

    private Context mContext;
    private IDataLoadedListener mDataListener;

    public TopicHistoryTask(Context context, IDataLoadedListener dataListener) {
        mContext = context;
        mDataListener = dataListener;
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
    protected TopicListData doInBackground(String... params) {
        TopicListData topicListData = new TopicListData();
        SQLiteDatabase db = SQLiteUtil.getInstance(mContext);
        Cursor c = db.query(SQLiteUtil.TABLE_TOPIC_HISTORY, null, "page = 1",
                null, null, null, "readtime");
        List<TopicData> topicDataList = new ArrayList<TopicData>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            TopicData dataTmp = (TopicData) JSONObject.toJavaObject(
                    JSON.parseObject(c.getString(c.getColumnIndex("topic"))),
                    TopicData.class);
            Log.v(TAG, "getSubject:" + dataTmp.getSubject());
            topicDataList.add(dataTmp);
        }
        c.close();
        topicListData.setTopicList(topicDataList);
        return topicListData;
    }

    @Override
    protected void onPostExecute(TopicListData result) {
        super.onPostExecute(result);
        mDataListener.onPostFinished(result);
    }

}
