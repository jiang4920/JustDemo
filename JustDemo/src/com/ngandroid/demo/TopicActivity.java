package com.ngandroid.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.topic.TopicListAdapter;
import com.ngandroid.demo.topic.TopicListData;
import com.ngandroid.demo.topic.TopicListTask;

public class TopicActivity extends Activity implements OnClickListener {
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
	
	TopicListData data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_topic);
		topicLv = (ListView)findViewById(R.id.topic_list);
		preBt = (Button)findViewById(R.id.topic_arrow_left);
		nextBt = (Button)findViewById(R.id.topic_arrow_right);
		preBt.setOnClickListener(this);
		nextBt.setOnClickListener(this);
		pageTv = (TextView)findViewById(R.id.topic_page);
		
		refresh();
	}
	int mCurPageIndex = 1;
	
	public void refresh(){
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
			}

			@Override
			public void onPostError(Integer status) {
			}

		}).execute(this.getIntent().getStringExtra("fid"),
				mCurPageIndex + "");
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.topic_arrow_left:
			if(mCurPageIndex >1){
				mCurPageIndex--;
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
	
}
