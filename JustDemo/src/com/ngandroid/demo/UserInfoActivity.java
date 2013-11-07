/**
 * UserInfoActivity.java[V 1.0.0]
 * classes : com.ngandroid.demo.UserInfoActivity
 * jiangyuchen Create at 2013-11-6 下午4:44:50
 */
package com.ngandroid.demo;

import com.ngandroid.demo.content.UserInfoEntity;
import com.ngandroid.demo.task.UserInfoTask;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.widget.UserViewsManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * com.ngandroid.demo.UserInfoActivity
 * @author jiangyuchen
 *
 * create at 2013-11-6 下午4:44:50
 */
public class UserInfoActivity extends Activity {
    private static final String TAG = "JustDemo UserInfoActivity";
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.layout_userinfo);
        showUserInfo();
    }
    
    
    public void showUserInfo(){
        new UserInfoTask(this, new IDataLoadedListener() {
            
            @Override
            public void onPostFinished(Object obj) {
                View view = UserInfoActivity.this.findViewById(R.id.userinfo_layout);
                UserViewsManager views = new UserViewsManager(view);
                views.setUserInfo((UserInfoEntity)obj);
            }
            
            @Override
            public void onPostError(Integer status) {
                
            }
        }).execute(this.getIntent().getStringExtra("uid"), this.getIntent().getStringExtra("username"));
    }
    
}