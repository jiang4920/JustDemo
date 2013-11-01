/**
 * TopicFavorDelDialog.java[V 1.0.0]
 * classes : com.ngandroid.demo.widget.TopicFavorDelDialog
 * jiangyuchen Create at 2013-11-1 下午4:15:26
 */
package com.ngandroid.demo.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import com.ngandroid.demo.R;
import com.ngandroid.demo.content.TopicFavorEntity;
import com.ngandroid.demo.task.TopicFavorTask;
import com.ngandroid.demo.topic.IDataLoadedListener;

/**
 * com.ngandroid.demo.widget.TopicFavorDelDialog
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-11-1 下午4:15:26
 */
public class TopicFavorDelDialog {
    private static final String TAG = "JustDemo TopicFavorDelDialog";

    AlertDialog mDialog;
    String mTid;
    private TopicFavorEntity mEntity;
    IDataLoadedListener mDataLoadedListener;

    public TopicFavorDelDialog(Context context, IDataLoadedListener listener) {
        mDataLoadedListener = listener;
        mDialog = create(context, listener);
        mContext = context;
        mEntity = new TopicFavorEntity();
    }

    public AlertDialog create(Context context, IDataLoadedListener listener) {
        TextView text = new TextView(context);
        text.setText(context.getString(R.string.del_topic_favor));
        return new AlertDialog.Builder(context)
                .setView(text)
                .setPositiveButton(R.string.confirm,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                    int which) {
                                TopicFavorEntity entity = new TopicFavorEntity();
                                entity.setAction("del");
                                entity.setTidarray(mTid);
                                entity.setPage("1");
                                new TopicFavorTask(mContext, entity, new IDataLoadedListener() {
                                    
                                    @Override
                                    public void onPostFinished(Object obj) {
                                        
                                    }
                                    
                                    @Override
                                    public void onPostError(Integer status) {
                                        
                                    }
                                }).execute();
                            }
                        }).setNegativeButton(R.string.cancel, null).create();
    }

    public void show(String tid){
        setTid(tid);
        mDialog.show();
    }
    
    public void setTid(String tid) {
        mTid = tid;
        mEntity.setTidarray(mTid);
    }

    private Context mContext;


}
