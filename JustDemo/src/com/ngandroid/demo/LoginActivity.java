package com.ngandroid.demo;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ngandroid.demo.content.LoginEntry;
import com.ngandroid.demo.task.LoginTask;
import com.ngandroid.demo.util.SQLiteUtil;

public class LoginActivity extends Activity {

    /**
     * 登录按钮
     */
    private Button mLoginBut;

    /** 用户名输入框*/
    private EditText usernameEt;
    
    /** 密码*/
    private EditText passwdEt;
    
    private static final String TAG = "LoginActivity";
    
    private SQLiteDatabase db;
    
    /**
     * 注册按钮
     */
    private ImageView registLinkBut;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        mLoginBut = (Button) findViewById(R.id.login_bt_login);
        mLoginBut.setOnClickListener(clickListener);
        
        usernameEt = (EditText)findViewById(R.id.login_et_username);
        passwdEt = (EditText)findViewById(R.id.login_et_password);
        
        registLinkBut = (ImageView)findViewById(R.id.login_regist_link);
        registLinkBut.setOnClickListener(clickListener);
        
    }

    private void initDb(){
        db = SQLiteUtil.getInstance(this);
//        Log.v(TAG, db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy))
    }
    
    OnClickListener clickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.login_bt_login:
                LoginEntry entry = new LoginEntry();
//                entry.setEmail("jiangzxc@qq.com");
//                entry.setPassword("123@123");
                entry.setEmail(usernameEt.getText().toString());
                entry.setPassword(passwdEt.getText().toString());
                new LoginTask(LoginActivity.this).execute(entry);
                Log.v(TAG, "onclick");
                break;
            case R.id.login_regist_link:
            	Intent intent = new Intent();
            	intent.setClass(LoginActivity.this, RegistActivity.class);
            	LoginActivity.this.startActivity(intent);
            	break;
            }
        }
    };
    
}
