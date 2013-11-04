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

import com.ngandroid.demo.adapter.TopicListAdapter;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.topic.content.TopicData;
import com.ngandroid.demo.topic.content.TopicListData;
import com.ngandroid.demo.topic.task.TopicListTask;
import com.ngandroid.demo.util.NGAURL;
import com.ngandroid.demo.util.PageUtil;
import com.ngandroid.demo.widget.PageSelectDialog;
import com.ngandroid.demo.widget.PageSelectDialog.OnSelectedListener;

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
	
	/**
	 * 发帖按钮
	 */
	private Button postBt;
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
	String mFid;
	TextView mTitleTv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_topic);
		topicLv = (ListView)findViewById(R.id.topic_list);
		topicLv.setOnItemClickListener(this);
		preBt = (Button)findViewById(R.id.topic_arrow_left);
		nextBt = (Button)findViewById(R.id.topic_arrow_right);
		postBt = (Button)findViewById(R.id.topic_post);
		
		findViewById(R.id.topic_page).setOnClickListener(this);
		mTitleTv = (TextView)findViewById(R.id.guide_head_title);
		postBt.setOnClickListener(this);
		preBt.setOnClickListener(this);
		nextBt.setOnClickListener(this);
		pageTv = (TextView)findViewById(R.id.topic_page);
		tabGroup = (RadioGroup)findViewById(R.id.topic_tabs);
		progressBar = (ProgressBar)findViewById(R.id.topic_progress);
		tabGroup.setOnCheckedChangeListener(this);
		mFid = this.getIntent().getStringExtra("fid");
		mTitleTv.setText(this.getIntent().getStringExtra("plate"));
		refresh();
	}
	int mCurPageIndex = 1;
	
	public void refresh(){
		progressBar.setVisibility(View.VISIBLE);
		String fid = mFid;
        String page = mCurPageIndex+"";
        String param = topicParam;
		String url = NGAURL.URL_BASE + "/thread.php?lite=js&noprefix&"+param+"&fid=" + fid
        + "&page=" + page;
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

		}).execute(url);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.topic_arrow_left:
		    mCurPageIndex = PageUtil.prePage(mCurPageIndex);
			refresh();
			break;
		case R.id.topic_arrow_right:
		    if(data != null){
    			Log.v(TAG, "data.getTopicPageCount()："+data.getTopicPageCount());
    			mCurPageIndex = PageUtil.nextPage(mCurPageIndex, data.getTopicPageCount());
    			refresh();
		    }
			break;
		case R.id.topic_post:
		    Intent intent = new Intent();
	        intent.setClass(this, PostActivity.class);
	        intent.putExtra("action", "new");
	        intent.putExtra("fid", mFid);
	        this.startActivity(intent);
			break;
		case R.id.topic_page:
		    if(data!= null){
		        PageSelectDialog.create(this, data.getTopicPageCount(), listener).show();
		    }
		    break;
		}
	}

	OnSelectedListener listener = new OnSelectedListener() {
        
        @Override
        public void onSelected(boolean isSelected, int page) {
            if(isSelected){
                mCurPageIndex = page;
                refresh();
            }
        }
    };

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
		if(mTopicListAdapter.getItem(arg2) == null){
		    Log.v(TAG, "topic item is NULL, i do not know why!");
		}
		startReplyActivity(mTopicListAdapter.getItem(arg2));
	}
	
	private void startReplyActivity(TopicData topic){
		Intent intent = new Intent();
		intent.setClass(this, TopicReplyActivity.class);
		intent.putExtra("topic", topic);
		this.startActivity(intent);
	}
	
}
