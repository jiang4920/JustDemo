package com.ngandroid.demo.content;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.ngandroid.demo.util.SQLiteUtil;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserResponse extends Response {
    private static final String TAG = "JustDemo UserEntry.java";

    public int uid;
    public String email;
    public String nickname;
    public int expiretime;
    public boolean hasResult;
    public int keepLogin;

    /**
     * <p>
     * Title: parse
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param doc
     * @see com.ngandroid.demo.content.Response#parse(org.dom4j.Document)
     */
    @Override
    public Response parse(Document doc) {
        Node root = doc.selectSingleNode("/data/data");//
        Log.v(TAG, "child count:" + root.getText());
        Element el = (Element) root;
        if (el.elementTextTrim("expiretime")==null) { // 返回只有一个数据节点即当前是“注册”操作则只返回uid
            Log.v(TAG, "" + (uid = Integer.parseInt(el.getTextTrim())));
        } else {

            Log.v(TAG, "" + (uid = Integer.parseInt(el.elementText("uid"))));
            Log.v(TAG, "" + (email = el.elementText("email")));
            Log.v(TAG,
                    ""
                            + (expiretime = Integer.parseInt(el
                                    .elementText("expiretime"))));
            Log.v(TAG, "" + (nickname = el.elementText("nickname")));
        }
        return this;
    }
    
    
    
    public void addNewUser(SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("uid", uid);
        cv.put("email", email);
        cv.put("expiretime", expiretime);
        cv.put("nickname", nickname);
        cv.put("loginTime", System.currentTimeMillis());
        cv.put("keepLogin", keepLogin);
        if(check(db, SQLiteUtil.TABLE_USER, uid)){
        	db.update(SQLiteUtil.TABLE_USER, cv, "uid = "+uid,null);
        }else{
        	db.insert(SQLiteUtil.TABLE_USER, null, cv);
        }
        
        Cursor c = db.query(SQLiteUtil.TABLE_USER, null, null, null, null, null, null);
        for(c.moveToFirst(); !c.isAfterLast();c.moveToNext()){
        	Log.v(TAG, "uid:"+c.getString(c.getColumnIndex("uid")) + " email:"+c.getString(c.getColumnIndex("email"))+" expiretime"+ c.getString(c.getColumnIndex("expiretime"))
        			+" nickname:"+c.getString(c.getColumnIndex("nickname"))+" loginTime"+ c.getString(c.getColumnIndex("loginTime"))
        			+ "keepLogin"+c.getString(c.getColumnIndex("keepLogin"))
        			);
        }
        c.close();
    }
    
    private boolean check(SQLiteDatabase db, String table,int uid){
    	Cursor c = db.query(table, null, " uid = " +uid, null, null, null, null);
    	if(c != null && c.getCount() > 0){
    		return true;
    	}
    	c.close();
    	return false;
    }
    
}
