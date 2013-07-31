package com.ngandroid.demo;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;

import com.ngandroid.demo.content.LoginEntry;
import com.ngandroid.demo.util.HttpUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity {

	/**
	 * 登录按钮
	 */
	private Button mLoginBut;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		
		mLoginBut = (Button)findViewById(R.id.login_bt_login);
		mLoginBut.setOnClickListener(clickListener);
	}
	OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.login_bt_login:
				testPost();
				break;
			}
		}
	};

	private void testPost(){
		Thread th = new Thread(){

			@Override
			public void run() {
				LoginEntry entry = new LoginEntry();
				entry.email = "jiang4920@163.com";
				entry.password = "jiangyuchen";
				try {
					new HttpUtil().doPost(HttpUtil.uriAPI, entry.getMap());
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		th.start();
	}
	
}
