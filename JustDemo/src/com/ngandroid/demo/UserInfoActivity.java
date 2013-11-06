/**
 * UserInfoActivity.java[V 1.0.0]
 * classes : com.ngandroid.demo.UserInfoActivity
 * jiangyuchen Create at 2013-11-6 下午4:44:50
 */
package com.ngandroid.demo;

import android.app.Activity;
import android.os.Bundle;

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
        this.setContentView(R.layout.usercenter_userinfo);
    }
}
