/**
 * UserCenterActivity.java[V 1.0.0]
 * classes : com.ngandroid.demo.UserCenterActivity
 * jiangyuchen Create at 2013-10-25 下午1:18:04
 */
package com.ngandroid.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ngandroid.demo.task.TopicHistoryTask;
import com.ngandroid.demo.task.UserInfoTask;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.topic.content.TopicData;
import com.ngandroid.demo.topic.content.TopicListAdapter;
import com.ngandroid.demo.topic.content.TopicListData;
import com.ngandroid.demo.topic.task.TopicListTask;
import com.ngandroid.demo.util.NGAURL;

/**
 * com.ngandroid.demo.UserCenterActivity
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-10-25 下午1:18:04
 */
public class UserCenterActivity extends Activity implements OnClickListener, OnItemClickListener {
    private static final String TAG = "JustDemo UserCenterActivity";

    /** 右侧菜单的集合 */
    List<ImageView> itemList;

    /** 菜单中的cursor */
    private ImageView menuCursorImg;

    /** 记录cursor的位置 */
    private int mFrom;
    /** 菜单项的间隔高度 */
    private int MARGINTOP;
    private FrameLayout mLayout;

    private String mFid;
    ProgressBar mProgressBar;

    private ListView mTopicFovLv;
    private ListView mTopicHistoryLv;
    
    /** 收藏布局*/
    private View mFavLayout;
    /** 历史记录布局*/
    private View mHistoryLayout;
    
    /** 标题*/
    private TextView mTitleTv;
    
    /** 历史记录数据*/
    private TopicListData mTopicListData;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_usercenter);

        mLayout = (FrameLayout) findViewById(R.id.usercenter_layout);
        mProgressBar = (ProgressBar) findViewById(R.id.usercenter_progress);
        mFavLayout = findViewById(R.id.usercenter_favorite);
        mHistoryLayout = findViewById(R.id.usercenter_history);
        
        mTitleTv = (TextView)findViewById(R.id.usercenter_head_title);
        itemList = new ArrayList<ImageView>();
        itemList.add((ImageView) findViewById(R.id.usercenter_menu_info));
        itemList.add((ImageView) findViewById(R.id.usercenter_menu_fav));
        itemList.add((ImageView) findViewById(R.id.usercenter_menu_history));
        itemList.add((ImageView) findViewById(R.id.usercenter_menu_msg));
        itemList.add((ImageView) findViewById(R.id.usercenter_menu_quite));
        menuCursorImg = (ImageView) findViewById(R.id.usercenter_menu_cursor);
        
        mTopicFovLv = (ListView)findViewById(R.id.fav_topic_list);
        mTopicHistoryLv = (ListView)findViewById(R.id.history_topic_list);
        mTopicFovLv.setOnItemClickListener(this);
        for (ImageView img : itemList) {
            img.setOnClickListener(this);
        }
        mFid = "24545785";
        new UserInfoTask(this).execute(mFid);
    }

    /**
     * <p>
     * Title: onMenuClick
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param viewId
     * @param postion
     * @see com.ngandroid.demo.widget.UserMenuClickListener#onMenuClick(int,
     *      int)
     */
    public void onMenuClick(ImageView view, int postion) {
        if (MARGINTOP == 0) {
            MARGINTOP = itemList.get(0).getTop();// 获取Y轴偏移值
        }
        moveAnimation(mFrom, (view.getHeight() + MARGINTOP) * postion);
        switch (view.getId()) {
        case R.id.usercenter_menu_info:
            new UserInfoTask(this).execute(mFid);
            mTitleTv.setText(this.getResources().getString(R.string.userinfo));
            break;
        case R.id.usercenter_menu_fav:
            showFavList();
            mTitleTv.setText(this.getResources().getString(R.string.favor));
            break;
        case R.id.usercenter_menu_history:
            showHistoryList();
            mTitleTv.setText(this.getResources().getString(R.string.history));
            break;
        case R.id.usercenter_menu_msg:

            mTitleTv.setText(this.getResources().getString(R.string.message));
            break;
        case R.id.usercenter_menu_quite:

            mTitleTv.setText(this.getResources().getString(R.string.quite));
            break;

        }
    }
    
    
    public void showHistoryList(){
        new TopicHistoryTask(this, new IDataLoadedListener() {
            
            @Override
            public void onPostFinished(Object obj) {
                mTopicListData = (TopicListData)obj;
                Log.v(TAG, "history count:"+mTopicListData.getTopicList().size());
                if (mTopicHistoryAdapter == null) {
                    mTopicHistoryAdapter = new TopicListAdapter(
                            UserCenterActivity.this, mTopicListData);
                    mTopicHistoryLv.setAdapter(mTopicHistoryAdapter);
                } else {
                    mTopicHistoryAdapter.refresh(mTopicListData);
                }
                bringToFront(mHistoryLayout);
            }
            
            @Override
            public void onPostError(Integer status) {
                
            }
        }).execute();
    }

    /** 历史记录的帖子列表adapter*/
    TopicListAdapter mTopicHistoryAdapter;
    /** 收藏的帖子列表adapter*/
    TopicListAdapter mTopicListAdapter;
    
    public void showFavList(){
        String url = NGAURL.URL_BASE + "/thread.php?lite=js&noprefix&favor=1";
        new TopicListTask(this, new IDataLoadedListener() {

            @Override
            public void onPostFinished(Object obj) {
                TopicListData data = (TopicListData) obj;
                if (mTopicListAdapter == null) {
                    mTopicListAdapter = new TopicListAdapter(
                            UserCenterActivity.this, data);
                    mTopicFovLv.setAdapter(mTopicListAdapter);
                } else {
                    mTopicListAdapter.refresh(data);
                }
                bringToFront(mFavLayout);
            }

            @Override
            public void onPostError(Integer status) {
            }

        }).execute(url);
    }
    
    /**
     * <p>
     * Title: bringToFront
     * </p>
     * <p>
     * Description: 切换布局至最前面
     * </p>
     * 
     * @param view
     *            将view布局切换至最上层
     */
    public void bringToFront(View view) {
        mLayout.bringChildToFront(view);
        mLayout.postInvalidate();
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * <p>
     * Title: onClick
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param v
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        mProgressBar.setVisibility(View.VISIBLE);
        for (int pos = 0; pos < itemList.size(); pos++) {
            if (v == itemList.get(pos)) {
                onMenuClick((ImageView) v, pos);
                break;
            }
        }
    }

    public void moveAnimation(int from, int to) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, from, to);
        animation.setFillAfter(true);
        animation.setDuration(300);
        menuCursorImg.startAnimation(animation);
        mFrom = to;
    }

    /**
     * <p>Title: onItemClick</p>
     * <p>Description: </p>
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
     */
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
