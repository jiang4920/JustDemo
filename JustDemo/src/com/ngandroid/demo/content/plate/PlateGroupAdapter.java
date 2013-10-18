package com.ngandroid.demo.content.plate;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngandroid.demo.R;

public class PlateGroupAdapter extends BaseAdapter {
    private static final String TAG = "LogCatcher FileAdapter";
    PlateGroup mGroup;
    Context mContext;
    int itemHeight;
    public PlateGroupAdapter(PlateGroup group, Context context){
        mGroup = group;
        mContext = context;
    }
    
    public void setList(PlateGroup group){
        mGroup = group;
    }
    
    /**
     * <p>Title: getCount</p>
     * <p>Description: </p>
     * @return
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return mGroup.size();
    }

    /**
     * <p>Title: getItem</p>
     * <p>Description: </p>
     * @param arg0
     * @return
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Plate getItem(int arg0) {
        return mGroup.get(arg0);
    }

    /**
     * <p>Title: getItemId</p>
     * <p>Description: </p>
     * @param arg0
     * @return
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    public int getItemHeight(){
        return itemHeight;
    }
    
    /**
     * <p>Title: getView</p>
     * <p>Description: </p>
     * @param arg0
     * @param arg1
     * @param arg2
     * @return
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();  
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.plate_item,
                    null);
            holder.icon = (ImageView) convertView.findViewById(R.id.plate_item_icon);
            holder.name = (TextView) convertView.findViewById(R.id.plate_item_name);
            holder.info = (TextView) convertView.findViewById(R.id.plate_item_info);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Plate plate = mGroup.get(position);
        if(plate.icon != null){
            holder.icon.setBackgroundDrawable(new BitmapDrawable(plate.icon));
        }
        holder.name.setText(plate.name);
        holder.info.setText(plate.info);
        convertView.setLayoutParams(new GridView.LayoutParams(LayoutParams.MATCH_PARENT, 100));
        itemHeight = convertView.getMeasuredHeight();
        Log.v(TAG, "itemHeight:"+itemHeight);
        return convertView;
    }
    
    private class ViewHolder  
    {  
        ImageView icon;  
        TextView name;  
        TextView info;  
    }
}
