package com.ngandroid.demo.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngandroid.demo.R;
import com.ngandroid.demo.content.SMSMessage;
import com.ngandroid.demo.content.SMSMessageList;
import com.ngandroid.demo.topic.content.TopicData;
import com.ngandroid.demo.util.Utils;

public class SMSListAdapter extends BaseAdapter {

    private static final String TAG = SMSListAdapter.class.getSimpleName();
    private Context mContext = null;
    private LayoutInflater mInflater = null;
    private SMSMessageList mSMSMessageList = null;

    public SMSListAdapter(Context context, SMSMessageList topicListData) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mSMSMessageList = topicListData;
    }

    public void refresh(SMSMessageList topicListData) {
        mSMSMessageList = topicListData;
        notifyDataSetChanged();
    }

    public SMSMessageList getSMSMessageList() {
        return mSMSMessageList;
    }

    @Override
    public int getCount() {
        return mSMSMessageList.size();
    }

    @Override
    public SMSMessage getItem(int position) {
        return mSMSMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.topic_item, null);
            holder = new ViewHolder();
            holder.tvReplyCount = (TextView) convertView
                    .findViewById(R.id.board_reply_count);
            holder.tvTopicTitle = (TextView) convertView
                    .findViewById(R.id.board_topic_title);
            holder.tvTopicAuthor = (TextView) convertView
                    .findViewById(R.id.board_topic_author);
            holder.llTopicTitleBg = (LinearLayout) convertView
                    .findViewById(R.id.board_title_bg);
            holder.tvTopicReplyTime = (TextView) convertView
                    .findViewById(R.id.board_topic_replytime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TextPaint tp = holder.tvTopicTitle.getPaint();
        SMSMessage smsData = mSMSMessageList.get(position);
        Log.v(TAG, "smsData:" + smsData.getSubject());
        tp.setFakeBoldText(false);
        holder.tvTopicTitle.setTextColor(mContext.getResources().getColor(
                R.color.topictile_normal));

        holder.tvTopicTitle.setText(Html.fromHtml(smsData.getSubject()));
        holder.tvTopicAuthor.setText(smsData.getFrom_username());
        holder.tvReplyCount.setText(smsData.getPosts() + "");
        holder.tvTopicReplyTime.setText(Utils.timeFormat(
                smsData.getLast_modify(), mSMSMessageList.getTime()));

        float fontSize = 30;
        holder.tvReplyCount.setTextSize(fontSize);
        if (position % 2 == 0) {
            holder.tvReplyCount.setBackgroundResource(R.color.shit1_2);
            holder.llTopicTitleBg.setBackgroundResource(R.color.shit1_1);
        } else {
            holder.tvReplyCount.setBackgroundResource(R.color.shit2_2);
            holder.llTopicTitleBg.setBackgroundResource(R.color.shit2_1);
        }
        holder.tvReplyCount.setTextColor(mContext.getResources().getColor(
                R.color.replycount_1024));
        return convertView;
    }

    private class ViewHolder {
        public TextView tvReplyCount;
        public TextView tvTopicTitle;
        public TextView tvTopicAuthor;
        public TextView tvTopicReplyTime;
        public LinearLayout llTopicTitleBg;
    }

}