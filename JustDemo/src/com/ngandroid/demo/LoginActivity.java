package com.ngandroid.demo;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ngandroid.demo.content.LoginEntry;
import com.ngandroid.demo.content.RegistEntry;
import com.ngandroid.demo.content.UserResponse;
import com.ngandroid.demo.task.LoginTask;
import com.ngandroid.demo.util.HttpUtil;
import com.ngandroid.demo.util.XMLDomUtil;

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

    OnClickListener clickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.login_bt_login:
                LoginEntry entry = new LoginEntry();
                entry.setEmail("jiang4920@163.com");
                entry.setPassword("jiangychen");
//                entry.setEmail(usernameEt.getText().toString());
//                entry.setPassword(passwdEt.getText().toString());
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

    private void testPost() {
        Thread th = new Thread() {

            @Override
            public void run() {
                LoginEntry entry = new LoginEntry();
                entry.setEmail("jiang125@6qq.com");
                entry.setPassword("jiangyuchen");
                XMLDomUtil domUtil = new XMLDomUtil();
                UserResponse user = null;
                try {
                    user = domUtil.getUserEntry(new HttpUtil().post(
                            LoginEntry.uriAPI, entry.getPostBody()));
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                RegistEntry rEntry = new RegistEntry();
                rEntry.setEmail("jian234g125@16qq.com");
                rEntry.setNickname("火1592753柴");
                rEntry.setPassword("jiangyuchen");
                rEntry.setPassword2("jiangyuchen");
                new HttpUtil().post(RegistEntry.uriAPI, rEntry.getPostBody());
            }
        };
        th.start();
    }
    
}
