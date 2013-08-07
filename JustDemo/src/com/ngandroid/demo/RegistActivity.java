package com.ngandroid.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class RegistActivity extends Activity {
	private static final String TAG = "JustDemo RegistActivity.java";

	private EditText usernameTv;
	private EditText emailTv;
	private EditText passwordTv;
	private EditText passwordConfirmTv;
	
	private Button commitBut;
	
	private CheckBox agreeCb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_regist);
		
		usernameTv = (EditText)findViewById(R.id.regist_et_username);
		emailTv = (EditText)findViewById(R.id.regist_et_email);
		passwordTv = (EditText)findViewById(R.id.regist_et_password);
		passwordConfirmTv = (EditText)findViewById(R.id.regist_et_password_confirm);
		
	}
	
	
	
}
