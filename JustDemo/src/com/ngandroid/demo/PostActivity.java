/**
 * PostActivity.java[V 1.0.0]
 * classes : com.ngandroid.demo.PostActivity
 * jiangyuchen Create at 2013-10-23 上午9:35:12
 */
package com.ngandroid.demo;

import android.app.Activity;
import android.os.Bundle;

import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.topic.content.PostData;
import com.ngandroid.demo.topic.task.PostTask;

/**
 * com.ngandroid.demo.PostActivity
 * @author jiangyuchen
 *
 * create at 2013-10-23 上午9:35:12
 */
public class PostActivity extends Activity {
    private static final String TAG = "JustDemo PostActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post);
        PostData postData = new PostData();
        postData.setAction(this.getIntent().getStringExtra("action"));
        postData.setFid(this.getIntent().getStringExtra("fid"));
        postData.setTid(this.getIntent().getStringExtra("Tid"));
        postData.setPid(this.getIntent().getStringExtra("Pid"));
        postData.setPost_subject("找不到话题但是想发帖");
        postData.setPost_content("找不到话题但是想发帖......发帖成功!");
        new PostTask(this, new IDataLoadedListener() {
            
            @Override
            public void onPostFinished(Object obj) {
                
            }
            
            @Override
            public void onPostError(Integer status) {
                
            }
        }).execute(postData);
    }
    
    
}
