/**
 * PostActivity.java[V 1.0.0]
 * classes : com.ngandroid.demo.PostActivity
 * jiangyuchen Create at 2013-10-23 上午9:35:12
 */
package com.ngandroid.demo;

import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.ngandroid.demo.topic.content.ReplyData;
import com.ngandroid.demo.topic.task.PostTask;

/**
 * com.ngandroid.demo.PostActivity
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-10-23 上午9:35:12
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
        subjectEt = (EditText) findViewById(R.id.post_title);
        contentEt = (EditText) findViewById(R.id.post_content);
        sendBt = (Button) findViewById(R.id.post_sender);
        attachmentBt = (Button) findViewById(R.id.post_attachment);
        sendBt.setOnClickListener(this);
        attachmentBt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.post_sender:
            post();
            break;
        case R.id.post_attachment:
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
            break;
        }
    }

    private void post() {
        if (!isTopicFormatOk()) {
            Toast.makeText(this,
                    this.getResources().getString(R.string.post_text_not_ok),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog dialog = ProgressDialog.show(this, null,
                this.getString(R.string.progress_sending));
        if(attachment!= null){
            attachment.setFid(this.getIntent().getStringExtra("fid"));
            postData.setAttachment(attachment);
        }
        postData.setAction(this.getIntent().getStringExtra("action"));
        postData.setFid(this.getIntent().getStringExtra("fid"));
        postData.setTid(this.getIntent().getStringExtra("tid"));
        postData.setPid(this.getIntent().getStringExtra("pid"));
        postData.setPost_subject(subjectEt.getText().toString().trim());
        String content = contentEt.getText().toString().trim();
        postData.setPost_content(content);
        String pid = this.getIntent().getStringExtra("pid");
        if(pid!=null || pid.length()>0){
            ReplyData replyData = (ReplyData)this.getIntent().getSerializableExtra("ReplyData");
            if(replyData != null){
                postData.setPost_content(formatReplyContent(replyData, content));
            }
        }
        PostTask task = new PostTask(this, new IDataLoadedListener() {

            @Override
            public void onPostFinished(Object obj) {
                dialog.dismiss();
                Toast.makeText(PostActivity.this,
                        PostActivity.this.getString(R.string.progress_success),
                        Toast.LENGTH_SHORT).show();
                PostActivity.this.finishActivity(200);
            }

            @Override
            public void onPostError(Integer status) {
                dialog.dismiss();
                Toast.makeText(PostActivity.this,
                        PostActivity.this.getString(R.string.progress_error),
                        Toast.LENGTH_SHORT).show();
            }
        });

        task.execute(postData);
    }
    
    public String formatReplyContent(ReplyData replyData, String content){
        String FORMAT = "[b]Reply to [pid=PID,TID,LOU]Reply[/pid] Post by POSTER_NAME (POST_TIME)[/b]";
        String REPLACE_PID = ""+replyData.getPid();
        String REPLACE_TID = ""+replyData.getTid();
        String REPLACE_LOU = ""+replyData.getLou();
        String REPLACE_POSTER_NAME = ""+replyData.getAuthorid();
        String REPLACE_POST_TIME = ""+replyData.getPostdate();
        FORMAT = FORMAT.replace("PID", REPLACE_PID);
        FORMAT = FORMAT.replace("TID", REPLACE_TID);
        FORMAT = FORMAT.replace("LOU", REPLACE_LOU);
        FORMAT = FORMAT.replace("POSTER_NAME", REPLACE_POSTER_NAME);
        FORMAT = FORMAT.replace("POST_TIME", REPLACE_POST_TIME);
        return FORMAT+"\n"+content;
    }

    /**
     * 判断格式是否正确
     * 
     * @return
     */
    private boolean isTopicFormatOk() {
        return subjectEt.getText().toString().trim().length() >= 4
                && contentEt.getText().toString().trim().length() >= 4;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Log.v(TAG, "picturePath:" + picturePath);
            File file = new File(picturePath);
            if (file.exists()) {
                attachment = new PostAttachmentEntry();
                attachment.setAttachementFile(file);
            }
        }
    }

}
