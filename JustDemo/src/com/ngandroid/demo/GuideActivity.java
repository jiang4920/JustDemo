package com.ngandroid.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.ngandroid.demo.topic.task.UpdateCookieTask;
import com.ngandroid.demo.util.Configs;

public class GuideActivity extends Activity implements OnClickListener {
    private static final String TAG = "JustDemo GuideActivity.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configs.initImageLoader(this);//初始化图片加载器
        setContentView(R.layout.layout_guide);
        findViewById(R.id.guide_plate).setOnClickListener(this);
        findViewById(R.id.guide_user_center).setOnClickListener(this);
        findViewById(R.id.guide_settings).setOnClickListener(this);
        new UpdateCookieTask().execute();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
        case R.id.guide_plate:
            intent.setClass(this, PlateActivity.class);
            break;
        case R.id.guide_user_center:
            intent.setClass(this, UserCenterActivity.class);
            break;
        case R.id.guide_settings:
            intent.setClass(this, SettingsActivity.class);
            break;

        }
        this.startActivity(intent);
    }

}
