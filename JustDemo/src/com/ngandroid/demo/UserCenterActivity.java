/**
 * UserCenterActivity.java[V 1.0.0]
 * classes : com.ngandroid.demo.UserCenterActivity
 * jiangyuchen Create at 2013-10-25 下午1:18:04
 */
package com.ngandroid.demo;

import java.util.ArrayList;
import java.util.List;

import com.ngandroid.demo.task.UserInfoTask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * com.ngandroid.demo.UserCenterActivity
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-10-25 下午1:18:04
 */
public class UserCenterActivity extends Activity implements OnClickListener {
    private static final String TAG = "JustDemo UserCenterActivity";

    /** 右侧菜单的集合 */
    List<ImageView> itemList;

    /** 菜单中的cursor */
    private ImageView menuCursorImg;

    /** 记录cursor的位置*/
    private int mFrom;
    /** 菜单项的间隔高度*/
    private int MARGINTOP;
    private FrameLayout mLayout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_usercenter);
        
        mLayout = (FrameLayout)findViewById(R.id.usercenter_layout);
        
        itemList = new ArrayList<ImageView>();
        itemList.add((ImageView) findViewById(R.id.usercenter_menu_info));
        itemList.add((ImageView) findViewById(R.id.usercenter_menu_fav));
        itemList.add((ImageView) findViewById(R.id.usercenter_menu_history));
        itemList.add((ImageView) findViewById(R.id.usercenter_menu_msg));
        itemList.add((ImageView) findViewById(R.id.usercenter_menu_quite));
        menuCursorImg = (ImageView) findViewById(R.id.usercenter_menu_cursor);
        for (ImageView img : itemList) {
            img.setOnClickListener(this);
        }
    }

    /**
     * <p>
     * Title: onMenuClick
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param viewId
     * @param postion
     * @see com.ngandroid.demo.widget.UserMenuClickListener#onMenuClick(int,
     *      int)
     */
    public void onMenuClick(ImageView view, int postion) {
        if(MARGINTOP==0){
            MARGINTOP = itemList.get(0).getTop();//获取Y轴偏移值
        }
        moveAnimation(view, mFrom, (view.getHeight()+MARGINTOP)*postion);
        new UserInfoTask(this).execute(""+24545785);
    }

    public void bringToFront(View view){
        mLayout.bringChildToFront(view);
    }
    
    /**
     * <p>
     * Title: onClick
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param v
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        for (int pos = 0; pos < itemList.size(); pos++) {
            if (v == itemList.get(pos)) {
                onMenuClick((ImageView) v, pos);
                break;
            }
        }
    }

    public void moveAnimation(View view, int from, int to) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, from, to);
        animation.setFillAfter(true);
        animation.setDuration(300);
        menuCursorImg.startAnimation(animation);
        mFrom = to;
    }

}
