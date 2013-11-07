/**
 * UserViewsManager.java[V 1.0.0]
 * classes : com.ngandroid.demo.widget.UserViewsManager
 * jiangyuchen Create at 2013-11-6 下午5:09:46
 */
package com.ngandroid.demo.widget;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngandroid.demo.R;
import com.ngandroid.demo.content.UserInfoEntity;
import com.ngandroid.demo.util.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * com.ngandroid.demo.widget.UserViewsManager
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-11-6 下午5:09:46
 */
public class UserViewsManager {
    private static final String TAG = "JustDemo UserViewsManager";

    ImageView headPic;
    TextView name;
    TextView userId;
    TextView group;
    TextView money;
    TextView regdate;
    WebView sign;
    TextView rvrc;
    /*
     * 初始化图片加载器
     */
    private ImageLoader mImageLoader = ImageLoader.getInstance();
    private ImageLoadingListener mAnimateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true).cacheOnDisc(true)
            .showImageForEmptyUri(R.drawable.nga_bg)
            .displayer(new RoundedBitmapDisplayer(5)).build();
    
    public UserViewsManager(View view) {
        headPic = (ImageView) view.findViewById(R.id.userinfo_pic);
        name = (TextView) view.findViewById(R.id.userinfo_name);
        userId = (TextView) view.findViewById(R.id.userinfo_userid);
        group = (TextView) view.findViewById(R.id.userinfo_group);
        money = (TextView) view.findViewById(R.id.userinfo_money);
        regdate = (TextView) view.findViewById(R.id.userinfo_regdate);
        sign = (WebView) view.findViewById(R.id.userinfo_sign);

        rvrc = (TextView) view.findViewById(R.id.userinfo_shengwang);
    }

    public void setUserInfo(UserInfoEntity result) {
        mImageLoader.displayImage(result.getAvatar(), headPic,
                options, mAnimateFirstListener);
        name.setText("用户名:" + result.username);
        userId.setText("用户ID:" + result.uid);
        group.setText("用户组:" + result.group);
        money.setText("金钱:" + result.money);
        regdate.setText("注册日期:"
                + Utils.dateFormat(Long.parseLong(result.regdate)));
        String content = Utils.decodeForumTag(result.sign, true);
        Log.v(TAG, "" + content);
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.FROYO) {
            sign.setLongClickable(false);
        }
        sign.setFocusableInTouchMode(false);
        sign.setFocusable(false);
        sign.setBackgroundColor(Color.parseColor("#00000000"));
        WebSettings setting = sign.getSettings();
        setting.setDefaultFontSize(14);
        setting.setJavaScriptEnabled(false);
        sign.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
        rvrc.setText("用户声望:" + result.rvrc);
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
}
