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
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ngandroid.demo.adapter.SMSListAdapter;
import com.ngandroid.demo.adapter.TopicListAdapter;
import com.ngandroid.demo.content.LoginEntry;
import com.ngandroid.demo.content.SMSMessageList;
import com.ngandroid.demo.content.UserResponse;
import com.ngandroid.demo.task.LoginTask;
import com.ngandroid.demo.task.MessageListTask;
import com.ngandroid.demo.task.TopicHistoryTask;
import com.ngandroid.demo.task.UserInfoTask;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.topic.content.TopicData;
import com.ngandroid.demo.topic.content.TopicListData;
import com.ngandroid.demo.topic.task.TopicListTask;
import com.ngandroid.demo.util.NGAURL;
import com.ngandroid.demo.util.SQLiteUtil;
import com.ngandroid.demo.widget.TopicFavorDelDialog;

/**
 * com.ngandroid.demo.UserCenterActivity
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-10-25 下午1:18:04
 */
public class UserCenterActivity extends Activity implements OnClickListener,
        OnItemClickListener {
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

    private String mUid;
    ProgressBar mProgressBar;

    /** 收藏列表 */
    private ListView mTopicFovLv;
    /** 历史记录列表 */
    private ListView mTopicHistoryLv;
    /** 短消息列表 */
    private ListView mSMSLv;

    /** 收藏布局 */
    private View mFavLayout;
    /** 历史记录布局 */
    private View mHistoryLayout;
    /** 短消息布局 */
    private View mMessageLayout;

    /** 注销布局 */
    private View mAccountLayout;
    private ListView mAccountLv;

    /** 标题 */
    private TextView mTitleTv;

    /** 历史记录数据 */
    private TopicListData mTopicListData;

    private SMSMessageList mSMSMessageList;

    /** header上右边的按钮，按钮功能会根据当前选中的menu项来复用点击事件 */
    private Button mRightBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_usercenter);

        mLayout = (FrameLayout) findViewById(R.id.usercenter_layout);
        mProgressBar = (ProgressBar) findViewById(R.id.usercenter_progress);
        mFavLayout = findViewById(R.id.usercenter_favorite);
        mHistoryLayout = findViewById(R.id.usercenter_history);
        mMessageLayout = findViewById(R.id.usercenter_message);
        mAccountLayout = findViewById(R.id.usercenter_account);
        mAccountLv = (ListView) findViewById(R.id.usercenter_account_list);
        mAccountLv.setOnItemClickListener(accountItemClickListener);

        mTitleTv = (TextView) findViewById(R.id.usercenter_head_title);
        itemList = new ArrayList<ImageView>();
        itemList.add((ImageView) findViewById(R.id.usercenter_menu_info));
        itemList.add((ImageView) findViewById(R.id.usercenter_menu_fav));
        itemList.add((ImageView) findViewById(R.id.usercenter_menu_history));
        itemList.add((ImageView) findViewById(R.id.usercenter_menu_msg));
        itemList.add((ImageView) findViewById(R.id.usercenter_menu_quite));
        menuCursorImg = (ImageView) findViewById(R.id.usercenter_menu_cursor);

        mTopicFovLv = (ListView) findViewById(R.id.fav_topic_list);
        mTopicHistoryLv = (ListView) findViewById(R.id.history_topic_list);
        
        mTopicHistoryLv.setOnItemClickListener(historyListener);
        
        mSMSLv = (ListView) findViewById(R.id.message_list);
        mSMSLv.setOnItemClickListener(smsItemClickListener);
        mTopicFovLv.setOnItemClickListener(this);

        mTopicFovLv.setOnItemLongClickListener(topicFovLongClickListener);

        mRightBt = (Button) findViewById(R.id.usercenter_right_but);
        mRightBt.setOnClickListener(rightOnClick);

        findViewById(R.id.usercenter_back).setOnClickListener(backListener);

        for (ImageView img : itemList) {
            img.setOnClickListener(this);
        }
        mUid = "" + UserResponse.getUser(this).uid;
        Log.v(TAG, "uid:" + mUid);
        new UserInfoTask(this).execute(mUid);
    }

    Cursor mCursor;

    private void showUserAccount() {

        mCursor = SQLiteUtil.getInstance(this).query(SQLiteUtil.TABLE_USER,
                null, " keepLogin = '1' ", null, null, null, "loginTime desc");
        mAccountLv.setAdapter(new SimpleCursorAdapter(this,
                R.layout.item_account, mCursor, new String[] { "nickname" },
                new int[] { R.id.item_account_username }));
        bringToFront(mAccountLayout);
    }

    OnItemClickListener accountItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            Cursor cursor = SQLiteUtil.getInstance(UserCenterActivity.this)
                    .query(SQLiteUtil.TABLE_USER, null, " _id = " + arg3, null,
                            null, null, "loginTime desc");
            cursor.moveToFirst();
            if (cursor != null && cursor.getCount() == 1) {
                LoginEntry entry = new LoginEntry();
                entry.setEmail(mCursor.getString(mCursor.getColumnIndex("email")));
                entry.setPassword(mCursor.getString(mCursor.getColumnIndex("password")));
                entry.setKeepLogin(1);
                new LoginTask(UserCenterActivity.this,
                        new IDataLoadedListener() {

                            @Override
                            public void onPostFinished(Object obj) {
                                UserCenterActivity.this.finish();
                                UserResponse userRsp = (UserResponse) obj;
                                Toast.makeText(UserCenterActivity.this,
                                        userRsp.nickname + "登陆成功！",
                                        Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPostError(Integer status) {

                            }
                        }).execute(entry);
            }

        }
    };

    OnClickListener backListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            UserCenterActivity.this.finish();
        }
    };
    TopicFavorDelDialog mFavorDelDialog;
    OnItemLongClickListener topicFovLongClickListener = new OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                int arg2, long arg3) {
            if (mFavorDelDialog == null) {
                mFavorDelDialog = new TopicFavorDelDialog(
                        UserCenterActivity.this, new IDataLoadedListener() {

                            @Override
                            public void onPostFinished(Object obj) {
                                showFavList();
                            }

                            @Override
                            public void onPostError(Integer status) {

                            }
                        });
            }
            mFavorDelDialog.show("" + mTopicListAdapter.getItem(arg2).getTid());
            return false;
        }
    };

    private OnClickListener rightOnClick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (currentMenuId) {
            case R.id.usercenter_menu_info:
                break;
            case R.id.usercenter_menu_fav:
                break;
            case R.id.usercenter_menu_history:
                break;
            case R.id.usercenter_menu_msg:
                Intent intent = new Intent();
                intent.setClass(UserCenterActivity.this, SMSWriteActivity.class);
                UserCenterActivity.this.startActivityForResult(intent, 100);
                break;
            case R.id.usercenter_menu_quite:

                break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == SMSWriteActivity.RESULT_OK) {
            showMessageList();
        }
    }

    OnItemClickListener smsItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            Intent intent = new Intent();
            intent.putExtra("sms", mSMSListAdapter.getItem(arg2));
            intent.setClass(UserCenterActivity.this, SMSReadActivity.class);
            UserCenterActivity.this.startActivity(intent);
        }
    };

    private int currentMenuId;

    public void onMenuClick(ImageView view, int postion) {
        if (MARGINTOP == 0) {
            MARGINTOP = itemList.get(0).getTop();// 获取Y轴偏移值
        }
        moveAnimation(mFrom, (view.getHeight() + MARGINTOP) * postion);
        currentMenuId = view.getId();
        switch (currentMenuId) {
        case R.id.usercenter_menu_info:
            new UserInfoTask(this).execute(mUid);
            mTitleTv.setText(this.getResources().getString(R.string.userinfo));
            mRightBt.setVisibility(View.GONE);
            break;
        case R.id.usercenter_menu_fav:
            showFavList();
            mTitleTv.setText(this.getResources().getString(R.string.favor));
            mRightBt.setVisibility(View.GONE);
            break;
        case R.id.usercenter_menu_history:
            showHistoryList();
            mTitleTv.setText(this.getResources().getString(R.string.history));
            mRightBt.setVisibility(View.GONE);
            break;
        case R.id.usercenter_menu_msg:
            showMessageList();
            mTitleTv.setText(this.getResources().getString(R.string.message));
            mRightBt.setVisibility(View.VISIBLE);
            mRightBt.setText(this.getString(R.string.write_sms));
            break;
        case R.id.usercenter_menu_quite:
            showUserAccount();
            mTitleTv.setText(this.getResources().getString(R.string.quite));
            mRightBt.setVisibility(View.GONE);
            break;

        }
    }

    public void showMessageList() {
        new MessageListTask(this, new IDataLoadedListener() {

            @Override
            public void onPostFinished(Object obj) {
                mSMSMessageList = (SMSMessageList) obj;
                Log.v(TAG, "mSMSMessageList size:" + mSMSMessageList.size());

                if (mSMSListAdapter == null) {
                    mSMSListAdapter = new SMSListAdapter(
                            UserCenterActivity.this, mSMSMessageList);
                    mSMSLv.setAdapter(mSMSListAdapter);
                } else {
                    mSMSListAdapter.refresh(mSMSMessageList);
                }
                bringToFront(mMessageLayout);
            }

            @Override
            public void onPostError(Integer status) {

            }
        }).execute("1");
    }

    
    OnItemClickListener historyListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            
        }
    };

    
    public void showHistoryList() {
        new TopicHistoryTask(this, new IDataLoadedListener() {

            @Override
            public void onPostFinished(Object obj) {
                mTopicListData = (TopicListData) obj;
                Log.v(TAG, "history count:"
                        + mTopicListData.getTopicList().size());
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

    /** 短消息的列表adapter */
    SMSListAdapter mSMSListAdapter;
    /** 历史记录的帖子列表adapter */
    TopicListAdapter mTopicHistoryAdapter;
    /** 收藏的帖子列表adapter */
    TopicListAdapter mTopicListAdapter;

    public void showFavList() {
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
                bringToFront(mFavLayout);
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
     * <p>
     * Title: onItemClick
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView,
     *      android.view.View, int, long)
     */
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Log.v(TAG, "pos:" + arg2);
        if (mTopicListAdapter.getItem(arg2) == null) {
            Log.v(TAG, "topic item is NULL, i do not know why!");
        }
        startReplyActivity(mTopicListAdapter.getItem(arg2));
    }

    private void startReplyActivity(TopicData topic) {
        Intent intent = new Intent();
        intent.setClass(this, TopicReplyActivity.class);
        intent.putExtra("topic", topic);
        this.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCursor.close();
    }

}
