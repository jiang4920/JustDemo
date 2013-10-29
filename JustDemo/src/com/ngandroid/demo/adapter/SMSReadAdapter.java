/**
 * SMSReadAdapter.java[V 1.0.0]
 * classes : com.ngandroid.demo.adapter.SMSReadAdapter
 * jiangyuchen Create at 2013-10-29 下午2:30:39
 */
package com.ngandroid.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ngandroid.demo.R;
import com.ngandroid.demo.content.SMSReadMessage;
import com.ngandroid.demo.content.SMSReadMessageList;
import com.ngandroid.demo.util.Utils;

/**
 * com.ngandroid.demo.adapter.SMSReadAdapter
 * @author jiangyuchen
 *
 * create at 2013-10-29 下午2:30:39
 */
public class SMSReadAdapter extends BaseAdapter {
    private static final String TAG = "JustDemo SMSReadAdapter";

    private SMSReadMessageList mReadMessageList;
    private Context mContext;
    public SMSReadAdapter(Context context, SMSReadMessageList readMessageList){
        mContext = context;
        mReadMessageList = readMessageList;
    }
    
    public void setSMSReadMessageList(SMSReadMessageList msgList){
        mReadMessageList = msgList;
        this.notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        return mReadMessageList.size();
    }

    @Override
    public SMSReadMessage getItem(int position) {
        return mReadMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sms_read, null);
            holder = new ViewHolder();
            holder.subject = (TextView) convertView
                    .findViewById(R.id.item_sms_subject);
            holder.context = (TextView) convertView
                    .findViewById(R.id.item_sms_content);
            holder.auther = (TextView) convertView
                    .findViewById(R.id.item_sms_read_auther);
            holder.time = (TextView) convertView
                    .findViewById(R.id.item_sms_read_time);
            holder.bg = (RelativeLayout) convertView
                    .findViewById(R.id.item_sms_read);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SMSReadMessage readMsg = getItem(position);
        if(readMsg.getFrom() == mReadMessageList.getStarterUid()){
            holder.bg.setBackgroundResource(R.drawable.msgbox_right);
        }else{
            holder.bg.setBackgroundResource(R.drawable.msgbox_left);
        }
        if(readMsg.getSubject()== null || readMsg.getSubject().length()==0){
            holder.subject.setVisibility(View.GONE);
        }else{
            holder.subject.setVisibility(View.VISIBLE);
            holder.subject.setText(readMsg.getSubject());
        }
        holder.context.setText(readMsg.getContent());
        holder.auther.setText(""+mReadMessageList.getAuther(readMsg.getFrom()));
        holder.time.setText(Utils.timeFormat(readMsg.getTime(), mReadMessageList.getTime()));
        return convertView;
    }
    
    private class ViewHolder{
        TextView subject;
        TextView context;
        TextView auther;
        TextView time;
        RelativeLayout bg;
    }
}
