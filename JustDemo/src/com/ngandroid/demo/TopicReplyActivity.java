package com.ngandroid.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ngandroid.demo.adapter.ReplyListAdapter;
import com.ngandroid.demo.content.TopicFavorEntity;
import com.ngandroid.demo.task.TopicFavorTask;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.topic.content.ReplyListData;
import com.ngandroid.demo.topic.content.TopicData;
import com.ngandroid.demo.topic.task.TopicReadTask;
import com.ngandroid.demo.util.PageUtil;
import com.ngandroid.demo.widget.PageSelectDialog;
import com.ngandroid.demo.widget.PageSelectDialog.OnSelectedListener;

public class TopicReplyActivity extends Activity implements OnClickListener {
	private static final String TAG = "JustDemo TopicReplyActivity.java";
	ListView mReplyListView;

	ProgressBar progressBar;
	ReplyListData mReplyListData;
	TopicData mTopicData;
	
	TextView mPageTv;
	
	LinearLayout mMoreLayout;
	
	/** 当前页数，初始第一页*/
	private int mCurPage = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_topic_reply);
		mReplyListView = (ListView)findViewById(R.id.topic_reply_list);
		progressBar = (ProgressBar)findViewById(R.id.topic_reply_progress);
		mMoreLayout = (LinearLayout)findViewById(R.id.topic_reply_more_layout);
        mPageTv = (TextView)findViewById(R.id.topic_reply_page);
        
		
        mTopicData = (TopicData) this.getIntent().getSerializableExtra("topic");
        
        findViewById(R.id.topic_reply_back).setOnClickListener(this);
        findViewById(R.id.topic_reply_more).setOnClickListener(this);
        findViewById(R.id.topic_reply_post).setOnClickListener(this);
        findViewById(R.id.topic_reply_favor).setOnClickListener(this);
        findViewById(R.id.topic_reply_arrow_left).setOnClickListener(this);
        findViewById(R.id.topic_reply_arrow_right).setOnClickListener(this);
        mPageTv.setOnClickListener(this);
		refresh(mCurPage);
	}
	
	
	private ReplyListAdapter mReplyListAdapter;
	private void refresh(int page){
	    progressBar.setVisibility(View.VISIBLE);
		new TopicReadTask(mTopicData, this, new IDataLoadedListener() {


			@Override
			public void onPostFinished(Object obj) {
			    mReplyListData = (ReplyListData)obj;
			    if(mReplyListAdapter== null){
    			    mReplyListAdapter = new ReplyListAdapter(TopicReplyActivity.this,
    		                mReplyListData);
    		        mReplyListView.setAdapter(mReplyListAdapter);
			    }
			    mReplyListAdapter.setReplyListData(mReplyListData);
			    mReplyListAdapter.notifyDataSetChanged();
			    mReplyListView.smoothScrollToPosition(0);
				progressBar.setVisibility(View.GONE);
				Log.v(TAG, "onPostFinished");
				mPageTv.setText(""+mCurPage);
			}

			@Override
			public void onPostError(Integer status) {
			    Log.v(TAG, "error:"+status);
			    progressBar.setVisibility(View.GONE);
			}
		}).execute(""+page);
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
            intent.putExtra("fid", ""+mTopicData.getFid());
            this.startActivityForResult(intent, 200);
            mMoreLayout.setVisibility(View.GONE);
            break;
        case R.id.topic_reply_arrow_left:
            refresh(mCurPage = PageUtil.prePage(mCurPage));
            break;
        case R.id.topic_reply_arrow_right:
            refresh(mCurPage = PageUtil.nextPage(mCurPage, mReplyListData.get__R__ROWS()));
            break;
        case R.id.topic_reply_more:
            mMoreLayout.setVisibility(mMoreLayout.getVisibility()==View.GONE?View.VISIBLE:View.GONE);
            break;
        case R.id.topic_reply_favor:
            mMoreLayout.setVisibility(View.GONE);
            TopicFavorEntity entity = new TopicFavorEntity();
            entity.setAction("add");
            entity.setTid(""+mTopicData.getTid());
            new TopicFavorTask(this, entity, new IDataLoadedListener() {
                
                @Override
                public void onPostFinished(Object obj) {
                    Toast.makeText(TopicReplyActivity.this, R.string.progress_success, Toast.LENGTH_SHORT).show();
                }
                
                @Override
                public void onPostError(Integer status) {
                    Toast.makeText(TopicReplyActivity.this, R.string.progress_error, Toast.LENGTH_SHORT).show();
                }
            }).execute();
            break;
        case R.id.topic_reply_page:
            if(mReplyListData!= null){
                PageSelectDialog.create(this, mReplyListData.getTopicPageCount(), new OnSelectedListener() {
                    
                    @Override
                    public void onSelected(boolean isSelected, int page) {
                        if(isSelected){
                            refresh(mCurPage = page);
                        }
                    }
                }).show();
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200&& resultCode ==200){
            refresh(mCurPage);
            Log.v(TAG, "topicreply refresh");
        }
    }
	
}
