package com.ngandroid.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.topic.TopicListAdapter;
import com.ngandroid.demo.topic.TopicListData;
import com.ngandroid.demo.topic.TopicListTask;

public class TopicActivity extends Activity {
	private static final String TAG = "JustDemo TopicActivity.java";

	private ListView topicLv;
	private TopicListAdapter mTopicListAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_topic);
		topicLv = (ListView)findViewById(R.id.topic_list);
		refresh();
	}
	
	public void refresh(){
		int mCurPageIndex = 1;
		new TopicListTask(this, new IDataLoadedListener() {

			@Override
			public void onPostFinished(Object obj) {
				mTopicListAdapter = new TopicListAdapter(TopicActivity.this,
						(TopicListData) obj);
				topicLv.setAdapter(mTopicListAdapter);
			}

			@Override
			public void onPostError(Integer status) {
			}

		}).execute(this.getIntent().getStringExtra("fid"),
				mCurPageIndex + "");
	}
	
}
