package com.ngandroid.demo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
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
    
    private static final String TAG = "JustDemo LoginActivity";
    
    private SQLiteDatabase db;
    
    private CheckBox keepLoginCb;
    
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
        
        keepLoginCb = (CheckBox)findViewById(R.id.login_cb_keep_online);
        tryLogin();
//        startPlateActivity();
    }

    public void tryLogin(){
    	db = SQLiteUtil.getInstance(this);
    	Cursor c = db.query(SQLiteUtil.TABLE_USER, null, null, null, null, null, "loginTime");
    	if(c!= null && c.getCount()>=1){
    		c.moveToFirst();
    		Log.v(TAG, "try uid:"+c.getString(c.getColumnIndex("uid")) + " email:"+c.getString(c.getColumnIndex("email"))+ " password:"+c.getString(c.getColumnIndex("password"))+" expiretime"+ c.getString(c.getColumnIndex("expiretime"))
        			+" nickname:"+c.getString(c.getColumnIndex("nickname"))+" loginTime"+ c.getString(c.getColumnIndex("loginTime"))
        			+ "keepLogin"+c.getString(c.getColumnIndex("keepLogin"))
        			);
		   usernameEt.setText(c.getString(c.getColumnIndex("email")));
		   passwdEt.setText(c.getString(c.getColumnIndex("password")));
		   c.close(); 
		   login();
    	}
    }
    
    public void startPlateActivity(){
		Intent intent = new Intent();
    	intent.setClass(this, PlateActivity.class);
    	this.startActivity(intent);
    }
    
    public int isKeepLogin(){
    	if(keepLoginCb.isChecked()){
    		return 1;
    	}
    	return 0;
    }
    
    private void login(){
    	LoginEntry entry = new LoginEntry();
//      entry.setEmail("jiangzxc@qq.com");
//      entry.setPassword("123@123");
      entry.setEmail(usernameEt.getText().toString());
      entry.setPassword(passwdEt.getText().toString());
      new LoginTask(LoginActivity.this).execute(entry);
    }
    
    OnClickListener clickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.login_bt_login:
            	login();
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
