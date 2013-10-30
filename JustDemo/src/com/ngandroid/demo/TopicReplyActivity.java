package com.ngandroid.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ngandroid.demo.adapter.ReplyListAdapter;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.topic.content.ReplyListData;
import com.ngandroid.demo.topic.content.TopicData;
import com.ngandroid.demo.topic.task.TopicReadTask;

public class TopicReplyActivity extends Activity {
	private static final String TAG = "JustDemo TopicReplyActivity.java";
	ListView mReplyListView;

	ProgressBar progressBar;
	ReplyListData mReplyListData = new ReplyListData();;
	TopicData mTopicData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_topic_reply);
		mReplyListView = (ListView)findViewById(R.id.topic_reply_list);
		progressBar = (ProgressBar)findViewById(R.id.topic_reply_progress);
        mReplyListAdapter = new ReplyListAdapter(TopicReplyActivity.this,
                mReplyListData);
        mReplyListView.setAdapter(mReplyListAdapter);
        mTopicData = (TopicData) this.getIntent().getSerializableExtra("topic");
		refresh();
	}
	
	
	private ReplyListAdapter mReplyListAdapter;
	private void refresh(){
		new TopicReadTask(mTopicData, this, new IDataLoadedListener() {


			@Override
			public void onPostFinished(Object obj) {
			    mReplyListData = (ReplyListData)obj;
			    mReplyListAdapter.setReplyListData(mReplyListData);
			    mReplyListAdapter.notifyDataSetChanged();
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onPostError(Integer status) {
			    progressBar.setVisibility(View.GONE);
			}
		}).execute("1");
	}
	
}
