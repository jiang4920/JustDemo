package com.ngandroid.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class MenuItemView extends ViewGroup {
	public static final int POSITION_LEFT_TOP = 1;
	public static final int POSITION_RIGHT_TOP = 2;
	public static final int POSITION_LEFT_BOTTOM = 3;
	public static final int POSITION_RIGHT_BOTTOM = 4;
	public final static int STATUS_CLOSE = 5;
	public final static int STATUS_OPEN = 6;
	private static final String TAG = "MenuItemView";

	private int top = 40;
	
	private int status;

	private Context context;

	public MenuItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MenuItemView(Context context) {
		super(context);
	}

	private void init(Context context) {
		this.context = context;
		this.status = STATUS_CLOSE;

	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		for (int index = 0; index < getChildCount(); index++) {
			final View child = getChildAt(index);
			// measure
			child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		if (changed) {
			int count = getChildCount();
			for (int i = 0; i < count; i++) {
				View childView = getChildAt(i);
				childView.setVisibility(View.GONE);
				int iTemWidth = getMeasuredWidth()/count;
				int width = iTemWidth;
//				int height = (int) MyAnimations.dip2px(context, 70);
				int height = iTemWidth;
				// the position of childview leftTop
				int x = (iTemWidth - width)/2 + i*iTemWidth;
				int y = 0 + top;
				Log.v(TAG, "item:"+width+":"+height);
				Log.v(TAG, getMeasuredWidth()+":"+getMeasuredHeight());
				int right = getWidth() - width;
				childView.layout(x, y, x+width,  y + height);
			}
		}
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
