package com.ngandroid.demo.adapter;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngandroid.demo.PostActivity;
import com.ngandroid.demo.R;
import com.ngandroid.demo.TopicReplyActivity;
import com.ngandroid.demo.topic.content.ReplyData;
import com.ngandroid.demo.topic.content.ReplyListData;
import com.ngandroid.demo.topic.content.UserInfoData;
import com.ngandroid.demo.util.Configs;
import com.ngandroid.demo.util.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ReplyListAdapter extends BaseAdapter {

	private static final String TAG = "ReplyListAdapter";
    private TopicReplyActivity mContext = null;
	private LayoutInflater mInflater = null;
	private ReplyListData mReplyListData = null;
	private ImageLoader mImageLoader = ImageLoader.getInstance();
	private ImageLoadingListener mAnimateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options = new DisplayImageOptions.Builder()
			.cacheInMemory(true).cacheOnDisc(true)
			.showImageForEmptyUri(R.drawable.nga_bg)
			.displayer(new RoundedBitmapDisplayer(5)).build();

	private SparseArray<SoftReference<View>> mViewCache;

	public ReplyListAdapter(TopicReplyActivity context, ReplyListData replyListData) {
		Configs.initImageLoader(context);
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mReplyListData = replyListData;
		mViewCache = new SparseArray<SoftReference<View>>();
	}

	public void setReplyListData(ReplyListData replyListData) {
	    mViewCache.clear();
        this.mReplyListData = replyListData;
    }

    @Override
	public int getCount() {
		return mReplyListData.get__R__ROWS();
	}

	@Override
	public ReplyData getItem(int position) {
		return mReplyListData.get__R().get(""+position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		SoftReference<View> srf = mViewCache.get(position);
		ViewHolder holder = null;
//		View cacheView = null;
//		if (srf != null) {
//			cacheView = srf.get();
//		}
//		if (cacheView != null) {
//		    holder = (ViewHolder) cacheView.getTag();
//		    Log.v(TAG, "cacheView exist, position:"+position+" ["+holder.position+"]");
//			return cacheView;
//		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_replylist, null);
			holder.setViewHolder(convertView);
		} else {
			holder = (ViewHolder) convertView.getTag();
			holder.tvContent.stopLoading();
			if (holder.tvContent.getHeight() > 300) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item_replylist, null);
//				mViewCache.put(holder.position, new SoftReference<View>(
//		                convertView));
				Log.d(TAG, "put:"+position+" ["+holder.position+"]");
				holder.setViewHolder(convertView);
			}
		}
		
		holder.position = position;

		convertView.setBackgroundResource(position % 2 == 0 ? R.color.shit2_1
				: R.color.shit2_2);
		Map<String, ReplyData> replyList = mReplyListData.get__R();
		Map<String, UserInfoData> userInfoList = mReplyListData.get__U();
		Log.v(TAG, "position:"+position);
		ArrayList<Integer> keyList = new ArrayList<Integer>();
		for(String key : replyList.keySet()){
		    int k = Integer.parseInt(key);
		    keyList.add(k);
		}
		Collections.sort(keyList);
		ReplyData replyData = replyList.get(keyList.get(position) + "");
		int authorId = replyData.getAuthorid();
		UserInfoData userInfoData = userInfoList.get(authorId + "");
		replyData.setAuthor(userInfoData.getUsername());
		holder.tvUserName.setText(userInfoData.getUsername());
		holder.tvReplyDate.setText(Utils.timeFormat(replyData
                .getPostdatetimestamp(), mReplyListData.getTime()));


		String content = replyData.getHtmlContent();
		Log.v(TAG, "content:"+content);
		if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.FROYO) {
			holder.tvContent.setLongClickable(false);
		}
		holder.tvContent.setFocusableInTouchMode(false);
		holder.tvContent.setFocusable(false);
		holder.tvContent.setBackgroundColor(Color.parseColor("#00000000"));

		WebSettings setting = holder.tvContent.getSettings();
		// setting.setBlockNetworkImage(!false);
		setting.setDefaultFontSize(14);
		setting.setJavaScriptEnabled(false);
		holder.tvContent.loadDataWithBaseURL(null, content, "text/html",
				"utf-8", null);
//		 holder.tvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

		holder.tvFloor.setText("#" + replyData.getLou());

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		Boolean isLoadImage = prefs.getBoolean("is_load_avatar", false);
		if (Utils.getNetworkType(mContext) == Utils.NetworkType.MOBILE
				&& !isLoadImage) {
			mImageLoader.denyNetworkDownloads(true);
		} else {
			mImageLoader.denyNetworkDownloads(false);
		}
		
		holder.ivAvatar.setTag(userInfoData.getAvatar());
		mImageLoader.displayImage(userInfoData.getAvatar(), holder.ivAvatar,
				options, mAnimateFirstListener);
		return convertView;
	}

	private class ViewHolder {
		public TextView tvUserName;
		public TextView tvReplyDate;
		public TextView tvFloor;
		public WebView tvContent;
		public ImageView ivAvatar;
		public ImageView replyIcon;
		public int position;//根据position将replydata数据传递到回复界面

		public void setViewHolder(View convertView) {
			this.tvUserName = (TextView) convertView
					.findViewById(R.id.reply_user_name);
			this.tvReplyDate = (TextView) convertView
					.findViewById(R.id.reply_date);
			this.tvFloor = (TextView) convertView
					.findViewById(R.id.reply_floor);
			this.tvContent = (WebView) convertView
					.findViewById(R.id.reply_content);
			this.ivAvatar = (ImageView) convertView
					.findViewById(R.id.reply_user_avatar);
			this.replyIcon = (ImageView) convertView
			        .findViewById(R.id.reply_reply);
			this.replyIcon.setOnClickListener(new ReplyOnClickListener());
			convertView.setTag(this);
		}
		private class ReplyOnClickListener implements OnClickListener {
		    
		    
		    @Override
		    public void onClick(View v) {
		        Intent intent = new Intent();
		        intent.setClass(mContext, PostActivity.class);
		        intent.putExtra("action", "reply");
		        intent.putExtra("fid", ""+getItem(position).getFid());
		        intent.putExtra("pid", ""+getItem(position).getPid());
		        intent.putExtra("tid", ""+getItem(position).getTid());
		        intent.putExtra("ReplyData", (Serializable)getItem(position));
		        mContext.startActivityForResult(intent, 200);
		    }
		    
		}
	}


	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> mDisplayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !mDisplayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					mDisplayedImages.add(imageUri);
				}
			}
		}
	}

	public void addAndRefresh(Map<String, UserInfoData> userList,
			Map<String, ReplyData> replyList) {
		mReplyListData.get__U().putAll(userList);

		Map<String, ReplyData> __R = mReplyListData.get__R();
		int curRowCount = mReplyListData.get__R__ROWS();
		Set<String> index = replyList.keySet();
		for (String i : index) {
			__R.put(Integer.valueOf(i) + curRowCount + "", replyList.get(i));
		}

		mReplyListData.set__R__ROWS(mReplyListData.get__R__ROWS()
				+ replyList.size());

		notifyDataSetChanged();
	}

	public boolean isHaveMore() {
		return mReplyListData.get__R__ROWS() != mReplyListData.get__ROWS();
	}

	@Override
	public void notifyDataSetChanged() {
		mViewCache.clear();
		super.notifyDataSetChanged();
	}
}
