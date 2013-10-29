/**
 * SMSReadActivity.java[V 1.0.0]
 * classes : com.ngandroid.demo.SMSReadActivity
 * jiangyuchen Create at 2013-10-29 下午1:24:25
 */
package com.ngandroid.demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.ngandroid.demo.adapter.SMSReadAdapter;
import com.ngandroid.demo.content.SMSMessage;
import com.ngandroid.demo.content.SMSReadMessageList;
import com.ngandroid.demo.content.SMSSendEntity;
import com.ngandroid.demo.task.SMSReadTask;
import com.ngandroid.demo.task.SMSSendTask;
import com.ngandroid.demo.topic.IDataLoadedListener;

/**
 * com.ngandroid.demo.SMSReadActivity
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-10-29 下午1:24:25
 */
public class SMSReadActivity extends Activity implements OnClickListener {
    private static final String TAG = "JustDemo SMSReadActivity";

    private ListView smsReadLv;
    SMSMessage mSMS;
    SMSReadAdapter mAdapter;
    SMSReadMessageList mMsgList;

    private EditText contentEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sms_read);
        smsReadLv = (ListView) findViewById(R.id.sms_read_list);
        contentEt = (EditText) findViewById(R.id.sms_read_content);
        contentEt.setText("中文OK吗？");
        findViewById(R.id.sms_read_send).setOnClickListener(this);
        findViewById(R.id.sms_read_back).setOnClickListener(this);
        mSMS = (SMSMessage) this.getIntent().getSerializableExtra("sms");
        Log.d(TAG, "mid:" + mSMS.getMid() + " subject:" + mSMS.getSubject());
        refreshMsg();
    }

    private void refreshMsg() {
        final ProgressDialog dialog = ProgressDialog.show(this, null,this.getString(R.string.progress_sending));
        new SMSReadTask(this, mSMS, new IDataLoadedListener() {

            @Override
            public void onPostFinished(Object obj) {
                mMsgList = (SMSReadMessageList) obj;
                if (mAdapter == null) {
                    mAdapter = new SMSReadAdapter(SMSReadActivity.this,
                            mMsgList);
                    smsReadLv.setAdapter(mAdapter);
                } else {
                    mAdapter.setSMSReadMessageList(mMsgList);
                }
                smsReadLv.setSelection(mAdapter.getCount());
                dialog.dismiss();
            }

            @Override
            public void onPostError(Integer status) {
                dialog.dismiss();
            }
        }).execute("1");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.sms_read_send:
            /** 点击发送按钮后回复短消息 */
            sendMsg();
            break;
        case R.id.sms_read_back:
            this.finish();
            break;
        }
    }

    private void sendMsg() {
        final ProgressDialog dialog = ProgressDialog.show(this, null,this.getString(R.string.progress_sending));
        SMSSendEntity entity = new SMSSendEntity();
        entity.setAct("reply");
        entity.setContent(contentEt.getText().toString());
        entity.setMid(mSMS.getMid());
        new SMSSendTask(this, entity, new IDataLoadedListener() {

            @Override
            public void onPostFinished(Object obj) {
                contentEt.setText("");
                refreshMsg();
                Log.v(TAG, "post ok!");
                dialog.dismiss();
            }

            @Override
            public void onPostError(Integer status) {
                Log.v(TAG, "post not ok!");
                dialog.dismiss();
            }
        }).execute();
    }

}
