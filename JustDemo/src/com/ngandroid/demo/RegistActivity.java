package com.ngandroid.demo;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ngandroid.demo.content.RegistEntry;
import com.ngandroid.demo.task.RegistTask;

public class RegistActivity extends Activity implements OnClickListener {
    private static final String TAG = "JustDemo RegistActivity.java";

    private EditText usernameTv;
    private EditText emailTv;
    private EditText passwordTv;
    private EditText passwordConfirmTv;

    private Button commitBut;

    
    private CheckBox agreeCb;

    private TextView noticeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_regist);

        usernameTv = (EditText) findViewById(R.id.regist_et_username);
        emailTv = (EditText) findViewById(R.id.regist_et_email);
        passwordTv = (EditText) findViewById(R.id.regist_et_password);
        passwordConfirmTv = (EditText) findViewById(R.id.regist_et_password_confirm);
        noticeTv = (TextView) findViewById(R.id.login_forgot_password);
        commitBut = (Button) findViewById(R.id.regist_bt_commit);
        commitBut.setOnClickListener(this);
        initNoticeText();
    }

    private void initNoticeText() {
        noticeTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * <p>
     * Title: onClick
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param v
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.regist_bt_commit:
            RegistEntry rEntry = new RegistEntry();
            // rEntry.setNickname("爱不持3久死焦点2框");
            // rEntry.setEmail("jiang78543332@qq.com");
            // rEntry.setPassword("123567");
            // rEntry.setPassword2("123567");
            rEntry.setNickname(usernameTv.getText().toString());
            rEntry.setEmail(emailTv.getText().toString());
            rEntry.setPassword(passwordTv.getText().toString());
            rEntry.setPassword2(passwordConfirmTv.getText().toString());
            new RegistTask(this).execute(rEntry);
            break;
        }
    }

}
