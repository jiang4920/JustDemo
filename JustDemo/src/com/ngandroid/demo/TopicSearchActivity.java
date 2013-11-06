/**
 * TopicSearchActivity.java[V 1.0.0]
 * classes : com.ngandroid.demo.TopicSearchActivity
 * jiangyuchen Create at 2013-11-5 下午3:33:54
 */
package com.ngandroid.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import com.ngandroid.demo.content.UserInfoEntity;
import com.ngandroid.demo.task.UserInfoTask;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.util.NGAURL;
import com.ngandroid.demo.widget.UserViewsManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

/**
 * com.ngandroid.demo.TopicSearchActivity
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-11-5 下午3:33:54
 */
public class TopicSearchActivity extends Activity implements OnClickListener {
    private static final String TAG = "JustDemo TopicSearchActivity";

    private EditText mTopicEt;
    private EditText mUserEt;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);
        findViewById(R.id.search_back).setOnClickListener(this);
        findViewById(R.id.search_confirm).setOnClickListener(this);
        findViewById(R.id.search_confirm_user).setOnClickListener(this);
        mTopicEt = (EditText)findViewById(R.id.search_key);
        mUserEt = (EditText)findViewById(R.id.search_key_user);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.search_back:
            this.finish();
            break;
        case R.id.search_confirm:
            String key = mTopicEt.getText().toString();
            if(!check(key, 1)){
                Toast.makeText(this, R.string.text_too_short, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                search(URLEncoder.encode(new String(key.getBytes(), "UTF-8"), "GBK"));
                this.finish();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            break;
        case R.id.search_confirm_user:
            String user = mUserEt.getText().toString();
            if(!check(user, 1)){
                Toast.makeText(this, R.string.text_too_short, Toast.LENGTH_SHORT).show();
                return;
            }
            showUserInfo();
            break;
        }
    }

    
    public void showUserInfo(){
        new UserInfoTask(this, new IDataLoadedListener() {
            
            @Override
            public void onPostFinished(Object obj) {
                UserInfoEntity result = (UserInfoEntity)obj;
            }
            
            @Override
            public void onPostError(Integer status) {
                // TODO Auto-generated method stub
                
            }
        }).execute("");
    }
    
    public boolean check(String msg, int minLength){
        if(msg!= null && msg.trim().length() > minLength){
            return true;
        }
        return false;
    }
    
    public void search(String key) {
        Intent intent = new Intent();
        intent.setClass(this, TopicActivity.class);
        String base = NGAURL.URL_BASE + "/thread.php?lite=js&noprefix&key="
                + key;
        intent.putExtra("base_url", base);
        intent.putExtra("type", "search_topic");
        intent.putExtra("plate", this.getString(R.string.plate_search));
        this.startActivity(intent);

    }

}
