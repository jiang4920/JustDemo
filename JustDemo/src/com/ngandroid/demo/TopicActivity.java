package com.ngandroid.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.topic.content.TopicListAdapter;
import com.ngandroid.demo.topic.content.TopicListData;
import com.ngandroid.demo.topic.task.TopicListTask;

public class TopicActivity extends Activity implements OnClickListener, OnCheckedChangeListener, OnItemClickListener {
	private static final String TAG = "JustDemo TopicActivity.java";

	private ListView topicLv;
	private TopicListAdapter mTopicListAdapter;
	
	/**
	 * 上一页
	 */
	public Button preBt;
	/**
	 * 下一页
	 */
	public Button nextBt;
	private TextView pageTv;
	
	private RadioGroup tabGroup;
	//http://bbs.ngacn.cc/thread.php?lite=js&order_by=postdatedesc&fid=320&page=1
	protected static final String POST = "order_by=postdatedesc";
	protected static final String REPLY = "order_by=lastpostdesc";
	protected static final String HEADER = "order_by=postdatedesc";
	protected static final String RECOMMAND = "recommend=1";
	
	private String topicParam = POST;
	
	private ProgressBar progressBar;
	
	TopicListData data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_topic);
		topicLv = (ListView)findViewById(R.id.topic_list);
		topicLv.setOnItemClickListener(this);
		preBt = (Button)findViewById(R.id.topic_arrow_left);
		nextBt = (Button)findViewById(R.id.topic_arrow_right);
		preBt.setOnClickListener(this);
		nextBt.setOnClickListener(this);
		pageTv = (TextView)findViewById(R.id.topic_page);
		tabGroup = (RadioGroup)findViewById(R.id.topic_tabs);
		progressBar = (ProgressBar)findViewById(R.id.topic_progress);
		tabGroup.setOnCheckedChangeListener(this);
		refresh();
	}
	int mCurPageIndex = 1;
	
	public void refresh(){
		progressBar.setVisibility(View.VISIBLE);
		new TopicListTask(this, new IDataLoadedListener() {

			@Override
			public void onPostFinished(Object obj) {
				data = (TopicListData) obj;
				if (mTopicListAdapter == null) {
					mTopicListAdapter = new TopicListAdapter(
							TopicActivity.this, data);
					topicLv.setAdapter(mTopicListAdapter);
				} else {
					mTopicListAdapter.refresh(data);
				}
				Log.v(TAG, "mCurPageIndex:"+mCurPageIndex);
				pageTv.setText(""+mCurPageIndex);
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onPostError(Integer status) {
			}

		}).execute(this.getIntent().getStringExtra("fid"),
				mCurPageIndex + "", topicParam);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.topic_arrow_left:
			if(mCurPageIndex >1){
				mCurPageIndex--;
			}else{
				Toast.makeText(this, this.getString(R.string.topic_refresh), Toast.LENGTH_SHORT).show();
			}
			refresh();
			break;
		case R.id.topic_arrow_right:
			Log.v(TAG, "data.getTopicPageCount()："+data.getTopicPageCount());
			if(mCurPageIndex < data.getTopicPageCount()){
				mCurPageIndex++;
			}
			refresh();
			break;
		}
	}

    /**
     * <p>Title: onCheckedChanged</p>
     * <p>Description: </p>
     * @param group
     * @param checkedId
     * @see android.widget.RadioGroup.OnCheckedChangeListener#onCheckedChanged(android.widget.RadioGroup, int)
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId){
        case R.id.topic_new_topic:
            topicParam = POST;
            break;
        case R.id.topic_new_reply:
            topicParam = REPLY;
            break;
        case R.id.topic_header:
            topicParam = HEADER;
            break;
        case R.id.topic_recommand:
            topicParam = RECOMMAND;
            break;
        }
        Log.v(TAG, "onCheckedChanged:"+topicParam);
        mCurPageIndex = 1;
        refresh();
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Log.v(TAG, "pos:"+arg2);
		startReplyActivity(mTopicListAdapter.getItem(arg2).getTid(), mTopicListAdapter.getItem(arg2).getFid());
	}
	
	private void startReplyActivity(int tid, int fid){
		Intent intent = new Intent();
		intent.setClass(this, TopicReplyActivity.class);
		intent.putExtra("tid", tid);
		intent.putExtra("fid", fid);
		this.startActivity(intent);
	}
	
}