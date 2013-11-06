/**
 * UserViewsManager.java[V 1.0.0]
 * classes : com.ngandroid.demo.widget.UserViewsManager
 * jiangyuchen Create at 2013-11-6 下午5:09:46
 */
package com.ngandroid.demo.widget;

import android.view.View;
import android.widget.TextView;

import com.ngandroid.demo.R;
import com.ngandroid.demo.content.UserInfoEntity;
import com.ngandroid.demo.util.Utils;

/**
 * com.ngandroid.demo.widget.UserViewsManager
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-11-6 下午5:09:46
 */
public class UserViewsManager {
    private static final String TAG = "JustDemo UserViewsManager";

    TextView name;
    TextView userId;
    TextView group;
    TextView money;
    TextView regdate;
    TextView sign;
    TextView rvrc;

    public UserViewsManager(View view) {
        name = (TextView) view.findViewById(R.id.userinfo_name);
        userId = (TextView) view.findViewById(R.id.userinfo_userid);
        group = (TextView) view.findViewById(R.id.userinfo_group);
        money = (TextView) view.findViewById(R.id.userinfo_money);
        regdate = (TextView) view.findViewById(R.id.userinfo_regdate);
        sign = (TextView) view.findViewById(R.id.userinfo_sign);
        rvrc = (TextView) view.findViewById(R.id.userinfo_shengwang);
    }

    public void setUserInfo(UserInfoEntity result) {
        name.setText("用户名:" + result.username);
        userId.setText("用户ID:" + result.uid);
        group.setText("用户组:" + result.group);
        money.setText("金钱:" + result.money);
        regdate.setText("注册日期:"
                + Utils.dateFormat(Long.parseLong(result.regdate)));
        sign.setText(result.sign);
        rvrc.setText("用户声望:" + result.rvrc);
    }
}
