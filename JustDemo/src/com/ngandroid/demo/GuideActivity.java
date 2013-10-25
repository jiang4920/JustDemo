package com.ngandroid.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class GuideActivity extends Activity implements OnClickListener {
	private static final String TAG = "JustDemo GuideActivity.java";

	private ImageButton plate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_guide);
		plate = (ImageButton)findViewById(R.id.guide_plate);
		findViewById(R.id.guide_user_center).setOnClickListener(this);
		plate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch(v.getId()){
		case R.id.guide_plate:
			intent.setClass(this, PlateActivity.class);
			break;
		case R.id.guide_user_center:
		    intent.setClass(this, UserCenterActivity.class);
		    break;
		}
		this.startActivity(intent);
	}
	
}
