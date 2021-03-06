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
import com.ngandroid.demo.topic.content.TopicData;
import com.ngandroid.demo.topic.content.TopicListData;
import com.ngandroid.demo.util.Utils;

public class TopicListAdapter extends BaseAdapter {

	private static final String TAG = TopicListAdapter.class.getSimpleName();
	private Context mContext = null;
	private LayoutInflater mInflater = null;
	private TopicListData mTopicListData = null;

	public TopicListAdapter(Context context, TopicListData topicListData) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mTopicListData = topicListData;
	}

	public void refresh(TopicListData topicListData) {
		mTopicListData = topicListData;
		notifyDataSetChanged();
	}

	public void addTopicAndRefresh(List<TopicData> toplicList) {
		mTopicListData.getTopicList().addAll(toplicList);
		mTopicListData.set__T__ROWS(mTopicListData.get__T__ROWS()
				+ toplicList.size());
		notifyDataSetChanged();
	}

	public TopicListData getTopicListData() {
		return mTopicListData;
	}

	@Override
	public int getCount() {
		return mTopicListData.getTopicList().size();
	}

	@Override
	public TopicData getItem(int position) {
		return mTopicListData.getTopicList().get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.topic_item, null);
			holder = new ViewHolder();
			holder.tvReplyCount = (TextView) convertView
					.findViewById(R.id.board_reply_count);
			holder.tvTopicTitle = (TextView) convertView
					.findViewById(R.id.board_topic_title);
			holder.tvTopicAuthor = (TextView) convertView
					.findViewById(R.id.board_topic_author);
//			holder.tvTopicPoster = (TextView) convertView
//					.findViewById(R.id.board_topic_poster);
			holder.llTopicTitleBg = (LinearLayout) convertView
					.findViewById(R.id.board_title_bg);
			holder.tvTopicReplyTime = (TextView) convertView
					.findViewById(R.id.board_topic_replytime);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		TextPaint tp = holder.tvTopicTitle.getPaint();
		TopicData topicData = mTopicListData.getTopicList().get(position);
		Log.v(TAG, "topicData:"+topicData.getSubject());
		tp.setFakeBoldText(false);
		if (topicData != null) {
			String titleFont = topicData.getTitlefont();
			if (titleFont != null && !titleFont.equals("")) {
				if (titleFont.contains("~1")) {
					tp.setFakeBoldText(true);
				}
				if (titleFont.startsWith("green")) {
					holder.tvTopicTitle.setTextColor(mContext.getResources()
							.getColor(R.color.topictile_green));
				} else if (titleFont.startsWith("blue")) {
					holder.tvTopicTitle.setTextColor(mContext.getResources()
							.getColor(R.color.topictile_blue));
				} else if (titleFont.startsWith("red")) {
					holder.tvTopicTitle.setTextColor(mContext.getResources()
							.getColor(R.color.topictile_red));
				} else if (titleFont.startsWith("orange")) {
					holder.tvTopicTitle.setTextColor(mContext.getResources()
							.getColor(R.color.topictile_orange));
				}
			} else {
				holder.tvTopicTitle.setTextColor(mContext.getResources()
						.getColor(R.color.topictile_normal));
			}

			holder.tvTopicTitle.setText(Html.fromHtml(topicData.getSubject()));
			holder.tvTopicAuthor.setText(topicData.getAuthor());
//			holder.tvTopicPoster.setText(topicData.getLastposter());
			holder.tvReplyCount.setText(topicData.getReplies() + "");
			holder.tvTopicReplyTime.setText(Utils.timeFormat(
					topicData.getLastpost(), mTopicListData.getTime()));

			float fontSize = 30;
			if (topicData.getReplies() > 9) {
				if (topicData.getReplies() > 99) {
					if (topicData.getReplies() > 999) {
						if (topicData.getReplies() > 9999) {
							fontSize = 14;
						} else {
							fontSize = 17;
						}
					} else {
						fontSize = 20;
					}
				} else {
					fontSize = 25;
				}
			}
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
		}else{
			Log.v(TAG, " position:"+position+" itemdata null");
		}
		return convertView;
	}

	private class ViewHolder {
		public TextView tvReplyCount;
		public TextView tvTopicTitle;
		public TextView tvTopicAuthor;
//		public TextView tvTopicPoster;
		public TextView tvTopicReplyTime;
		public LinearLayout llTopicTitleBg;
	}

}