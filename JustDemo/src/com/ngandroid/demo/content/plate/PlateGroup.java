/**
 * PlateGroup.java[V 1.0.0]
 * classes : com.ngandroid.demo.content.plate.PlateGroup
 * jiangyuchen Create at 2013-10-18 上午10:06:07
 */
package com.ngandroid.demo.content.plate;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngandroid.demo.R;
import com.ngandroid.demo.TopicActivity;

/**
 * com.ngandroid.demo.content.plate.PlateGroup
 * @author jiangyuchen
 *
 * create at 2013-10-18 上午10:06:07
 */
public class PlateGroup extends ArrayList<Plate> implements OnItemClickListener {
    private static final String TAG = "JustDemo PlateGroup";
    
    
    /** 板块分组名称*/
    public String name;
    public GridView groupGv;
    public TextView header;
    PlateGroupAdapter adapter;
    View layout;
    private Context mContext;
    public View getView(Context context){
    	mContext = context;
        if(layout == null){
            layout = LayoutInflater.from(context).inflate(R.layout.layout_plate_group, null);
            groupGv = (GridView)layout.findViewById(R.id.plate_group_grid);
            groupGv.setOnItemClickListener(this);
            header = (TextView)layout.findViewById(R.id.plate_group_name);
            adapter = new PlateGroupAdapter(this, context);
            header.setText("：："+name+"：：");
            groupGv.setAdapter(adapter);
            int height = (adapter.getCount()/2+(adapter.getCount()%2==0?0:1))*100;
            groupGv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, height));
        }
        new PlateIconTask().execute(this);
        return layout;
    }
    
    public void notifyRefresh(){
        adapter.notifyDataSetChanged();
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Log.v(TAG, "name:"+adapter.getItem(arg2).name);
		Intent intent = new Intent();
		intent.setClass(mContext, TopicActivity.class);
		intent.putExtra("fid", ""+adapter.getItem(arg2).fid);
		mContext.startActivity(intent);
	}
}
