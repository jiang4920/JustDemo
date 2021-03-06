package com.ngandroid.demo;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import com.ngandroid.demo.content.LoginEntry;
import com.ngandroid.demo.content.UserResponse;
import com.ngandroid.demo.task.LoginTask;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.util.SQLiteUtil;

public class LoginActivity extends Activity {

    /**
     * 登录按钮
     */
    private Button mLoginBut;

    /** 用户名输入框 */
    private EditText usernameEt;

    /** 密码 */
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

        usernameEt = (EditText) findViewById(R.id.login_et_username);
        passwdEt = (EditText) findViewById(R.id.login_et_password);

        registLinkBut = (ImageView) findViewById(R.id.login_regist_link);
        registLinkBut.setOnClickListener(clickListener);

        keepLoginCb = (CheckBox) findViewById(R.id.login_cb_keep_online);
        tryLogin();
    }

    public void tryLogin() {
        db = SQLiteUtil.getInstance(this);
        Cursor c = db.query(SQLiteUtil.TABLE_USER, null,
                " keepLogin = '1' ", null, null, null, "loginTime desc");
        if (c != null && c.getCount() >= 1) {
            c.moveToFirst();
            Log.d(TAG,
                    "try uid:" + c.getString(c.getColumnIndex("uid"))
                            + " username:"
                            + c.getString(c.getColumnIndex("username"))
                            + " email:"
                            + c.getString(c.getColumnIndex("email"))
                            + " password:"
                            + c.getString(c.getColumnIndex("password"))
                            + " expiretime"
                            + c.getString(c.getColumnIndex("expiretime"))
                            + " nickname:"
                            + c.getString(c.getColumnIndex("nickname"))
                            + " loginTime"
                            + c.getString(c.getColumnIndex("loginTime"))
                            + "keepLogin"
                            + c.getString(c.getColumnIndex("keepLogin")));
            usernameEt.setText(c.getString(c.getColumnIndex("username")));
            passwdEt.setText(c.getString(c.getColumnIndex("password")));
            keepLoginCb.setChecked(true);
            c.close();
        }
    }

    public void startPlateActivity() {
        Intent intent = new Intent();
        intent.setClass(this, PlateActivity.class);
        this.startActivity(intent);
    }

    public int isKeepLogin() {
        if (keepLoginCb.isChecked()) {
            return 1;
        }
        return 0;
    }

    private void login() {
        LoginEntry entry = new LoginEntry();
        entry.setEmail(usernameEt.getText().toString());
        entry.setPassword(passwdEt.getText().toString());
        entry.setKeepLogin(isKeepLogin());
        new LoginTask(LoginActivity.this, new IDataLoadedListener() {

            @Override
            public void onPostFinished(Object obj) {
                LoginActivity.this.finish();
                startPlateActivity(LoginActivity.this);
                UserResponse userRsp = (UserResponse) obj;
                Toast.makeText(LoginActivity.this, userRsp.nickname + "登陆成功！",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPostError(Integer status) {

            }
        }).execute(entry);
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

    public void startPlateActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, GuideActivity.class);
        context.startActivity(intent);
    }
}
