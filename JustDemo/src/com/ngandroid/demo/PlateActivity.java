package com.ngandroid.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ngandroid.demo.content.UserResponse;
import com.ngandroid.demo.task.PlateTask;
import com.ngandroid.demo.topic.IDataLoadedListener;
import com.ngandroid.demo.widget.MenuItemView;
import com.ngandroid.demo.widget.MyAnimations;
import com.ngandroid.demo.widget.OnItemClickListener;

public class PlateActivity extends Activity implements OnClickListener, OnItemClickListener {
	private static final String TAG = "JustDemo PlateActivity.java";

	private MenuItemView mMenuGroup;
	
	private ImageView mMenuHandle;
	
	UserResponse user;
	
	LinearLayout plateLayout;
	
	private ProgressBar progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_plate);
		plateLayout = (LinearLayout)findViewById(R.id.plate_parent);
		progressBar = (ProgressBar)findViewById(R.id.plate_progress);
		findViewById(R.id.plate_search).setOnClickListener(this);
		initMenuGroup();
		new PlateTask(this, plateLayout, new IDataLoadedListener() {
            
            @Override
            public void onPostFinished(Object obj) {
                progressBar.setVisibility(View.GONE);
            }
            
            @Override
            public void onPostError(Integer status) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PlateActivity.this, "登陆失败,请检查网络！", Toast.LENGTH_SHORT).show();
            }
        }).execute();
	}

	public void initMenuGroup(){
		mMenuGroup = (MenuItemView)findViewById(R.id.plate_menu_group);
		mMenuHandle = (ImageView)findViewById(R.id.plate_menu_group_handle);
		mMenuHandle.setOnClickListener(this);
		ImageView img1 = new ImageView(this);
		img1.setBackgroundResource(R.drawable.icon_menu_home);
		ImageButton imgBtn2 = new ImageButton(this);
		imgBtn2.setBackgroundResource(R.drawable.icon_menu_search);
		ImageButton imgBtn3 = new ImageButton(this);
		imgBtn3.setBackgroundResource(R.drawable.icon_menu_favourite);
		ImageButton imgBtn4 = new ImageButton(this);
		imgBtn4.setBackgroundResource(R.drawable.icon_menu_user);
		ImageButton imgBtn5 = new ImageButton(this);
		imgBtn5.setBackgroundResource(R.drawable.icon_menu_more);
		mMenuGroup.addView(img1);
		mMenuGroup.addView(imgBtn2);
		mMenuGroup.addView(imgBtn3);
		mMenuGroup.addView(imgBtn4);
		mMenuGroup.addView(imgBtn5);
		user = UserResponse.getUser(this);
		Log.v(TAG, "user:"+user.uid+"+"+user.nickname);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.plate_menu_group_handle:
			MyAnimations.getRotateAnimation(mMenuHandle, 0f, 270f, 300);
			MyAnimations.startAnimations(this, mMenuGroup, 300);
			break;
		case R.id.plate_search:
		    
		    break;
		}
	}

	@Override
	public void onItemClick(int item) {
		
	}
	
}
