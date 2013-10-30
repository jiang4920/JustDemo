/**
 * SMSWriteActivity.java[V 1.0.0]
 * classes : com.ngandroid.demo.SMSWriteActivity
 * jiangyuchen Create at 2013-10-30 上午9:28:44
 */
package com.ngandroid.demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.ngandroid.demo.content.SMSSendEntity;
import com.ngandroid.demo.task.SMSSendTask;
import com.ngandroid.demo.topic.IDataLoadedListener;

/**
 * com.ngandroid.demo.SMSWriteActivity
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-10-30 上午9:28:44
 */
public class SMSWriteActivity extends Activity implements OnClickListener {
    private static final String TAG = "JustDemo SMSWriteActivity";

    /** 收件人输入框*/
    private EditText receiverEt;
    /** 标题输入框*/
    private EditText subjetEt;
    /** 内容输入框*/
    private EditText contentEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sms_write);
        receiverEt = (EditText) findViewById(R.id.sms_write_receiver);
        subjetEt = (EditText) findViewById(R.id.sms_write_subject);
        contentEt = (EditText) findViewById(R.id.sms_write_content);
        findViewById(R.id.sms_write_commit).setOnClickListener(this);
        findViewById(R.id.sms_write_back).setOnClickListener(this);
    }

    /** 返回到用户中心，返回值为RESULT_OK表示发送成功*/
    public static final int RESULT_OK = 200;
    
    @Override
    public void onClick(View v) {
        switch(v.getId()){
        case R.id.sms_write_commit:
            sendMsg();
            break;
        case R.id.sms_write_back:
            this.finish();
            break;
        }
    }

    private void sendMsg(){
        if(!isFormatOK()){
            Toast.makeText(this, R.string.toast_write_sms_error, Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog dialog = ProgressDialog.show(this, null, this.getString(R.string.progress_sending));
        SMSSendEntity entity = new SMSSendEntity();
        entity.setAct("new");
        entity.setContent(contentEt.getText().toString());
        entity.setSubject(subjetEt.getText().toString());
        entity.setTo(receiverEt.getText().toString());
        new SMSSendTask(this, entity, new IDataLoadedListener() {

            @Override
            public void onPostFinished(Object obj) {
                Toast.makeText(SMSWriteActivity.this, R.string.progress_success, Toast.LENGTH_SHORT).show();
                SMSWriteActivity.this.setResult(RESULT_OK);
                SMSWriteActivity.this.finish();
                dialog.dismiss();
            }

            @Override
            public void onPostError(Integer status) {
                Toast.makeText(SMSWriteActivity.this, R.string.progress_error, Toast.LENGTH_SHORT).show();
                SMSWriteActivity.this.finish();
                dialog.dismiss();
            }
        }).execute();
    }
    
    /**
     * <p>Title: isFormatOK</p>
     * <p>Description: 判断格式是不是正确</p>
     * @return
     */
    public boolean isFormatOK() {
        return receiverEt.getText().toString().trim().length() != 0
                && contentEt.getText().toString().trim().length() != 0
                && subjetEt.getText().toString().trim().length() != 0;
    }
}
