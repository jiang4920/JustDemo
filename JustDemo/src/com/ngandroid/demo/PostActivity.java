/**
 * PostActivity.java[V 1.0.0]
 * classes : com.ngandroid.demo.PostActivity
 * jiangyuchen Create at 2013-10-23 上午9:35:12
 */
package com.ngandroid.demo;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.topic.content.PostAttachmentEntry;
import com.ngandroid.demo.topic.content.PostData;
import com.ngandroid.demo.topic.task.PostTask;

/**
 * com.ngandroid.demo.PostActivity
 * @author jiangyuchen
 *
 * create at 2013-10-23 上午9:35:12
 */
public class PostActivity extends Activity implements OnClickListener {
    private static final String TAG = "JustDemo PostActivity";

	private static final int RESULT_LOAD_IMAGE = 100;

    /**
     * 发送按钮
     */
    private Button sendBt;
    /**
     * 帖子标题
     */
    private EditText subjectEt;
    /**
     * 帖子内容
     */
    private EditText contentEt;
    
    /**
     * 选择附件的按钮
     */
    private Button attachmentBt;
    
    private PostAttachmentEntry attachment;
    PostData postData;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post);
        postData = new PostData();
        subjectEt = (EditText)findViewById(R.id.post_title);
        contentEt = (EditText)findViewById(R.id.post_content);
        sendBt = (Button)findViewById(R.id.post_sender);
        attachmentBt = (Button)findViewById(R.id.post_attachment);
        sendBt.setOnClickListener(this);
        attachmentBt.setOnClickListener(this);
        
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.post_sender:
			post();
			break;
		case R.id.post_attachment:
			Intent i = new Intent(
					Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(i, RESULT_LOAD_IMAGE);
			break;
		}
	}
    
	private void post(){
		if(!isTopicFormatOk()){
			Toast.makeText(this, this.getResources().getString(R.string.post_text_not_ok), Toast.LENGTH_SHORT).show();
			return;
		}
		attachment.setFid(this.getIntent().getStringExtra("fid"));
		postData.setAttachment(attachment);
        postData.setAction(this.getIntent().getStringExtra("action"));
        postData.setFid(this.getIntent().getStringExtra("fid"));
        postData.setTid(this.getIntent().getStringExtra("Tid"));
        postData.setPid(this.getIntent().getStringExtra("Pid"));
        postData.setPost_subject(subjectEt.getText().toString().trim());
        postData.setPost_content(contentEt.getText().toString().trim());
        PostTask task = new PostTask(this, new IDataLoadedListener() {
            
            @Override
            public void onPostFinished(Object obj) {
                
            }
            
            @Override
            public void onPostError(Integer status) {
                
            }
        });
        
        task.execute(postData);
	}
    
	/**
	 * 判断格式是否正确
	 * @return
	 */
	private boolean isTopicFormatOk(){
		return subjectEt.getText().toString().trim().length()>=4 && contentEt.getText().toString().trim().length()>=4;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
	        Uri selectedImage = data.getData();
	        String[] filePathColumn = { MediaStore.Images.Media.DATA };
	        Cursor cursor = getContentResolver().query(selectedImage,
	                filePathColumn, null, null, null);
	        cursor.moveToFirst();
	        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	        String picturePath = cursor.getString(columnIndex);
	        cursor.close();
	        Log.v(TAG, "picturePath:"+picturePath);
	        File file = new File(picturePath);
	        if(file.exists()){
	        	attachment = new PostAttachmentEntry();
	        	attachment.setAttachementFile(file);
	        }
	    }
	}	
	
	
}
