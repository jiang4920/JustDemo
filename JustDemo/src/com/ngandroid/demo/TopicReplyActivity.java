package com.ngandroid.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.topic.content.ReplyListAdapter;
import com.ngandroid.demo.topic.content.ReplyListData;
import com.ngandroid.demo.topic.task.TopicReadTask;

public class TopicReplyActivity extends Activity {
	private static final String TAG = "JustDemo TopicReplyActivity.java";
	ListView mReplyListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_topic_reply);
		mReplyListView = (ListView)findViewById(R.id.topic_reply_list);
		refresh();
	}
	
	
	private ReplyListAdapter mReplyListAdapter;
	private void refresh(){
		new TopicReadTask(this, new IDataLoadedListener() {


			@Override
			public void onPostFinished(Object obj) {
				mReplyListAdapter = new ReplyListAdapter(TopicReplyActivity.this,
						(ReplyListData) obj);
				mReplyListView.setAdapter(mReplyListAdapter);
			}

			@Override
			public void onPostError(Integer status) {
			}
		}).execute(this.getIntent().getIntExtra("tid", 0)+"", this.getIntent().getIntExtra("fid",0)+"",
				"1");
	}
	
}
