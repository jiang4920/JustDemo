package com.ngandroid.demo;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ngandroid.demo.adapter.ReplyListAdapter;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.topic.content.ReplyListData;
import com.ngandroid.demo.topic.content.TopicData;
import com.ngandroid.demo.topic.task.TopicReadTask;

public class TopicReplyActivity extends Activity implements OnClickListener {
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
        
        findViewById(R.id.topic_reply_back).setOnClickListener(this);
        findViewById(R.id.topic_reply_post).setOnClickListener(this);
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
			    mReplyListView.setSelection(0);
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onPostError(Integer status) {
			    progressBar.setVisibility(View.GONE);
			}
		}).execute("1");
	}
	
    @Override
    public void onClick(View v) {
        switch(v.getId()){
        case R.id.topic_reply_back:
            this.finish();
            break;
        case R.id.topic_reply_post:
            Intent intent = new Intent();
            intent.setClass(this, PostActivity.class);
            intent.putExtra("action", "reply");
            intent.putExtra("tid", ""+mTopicData.getTid());
            this.startActivityForResult(intent, 200);
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200&& resultCode ==200){
            refresh();
        }
    }
	
}
