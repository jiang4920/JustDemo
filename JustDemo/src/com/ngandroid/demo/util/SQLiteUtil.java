/**
 * SQLiteUtil.java[V 1.0.0]
 * classes : com.ngandroid.demo.util.SQLiteUtil
 * jiangyuchen Create at 2013-8-8 下午1:14:41
 */
package com.ngandroid.demo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * com.ngandroid.demo.util.SQLiteUtil
 * @author jiangyuchen
 *
 * create at 2013-8-8 下午1:14:41
 */
public class SQLiteUtil extends SQLiteOpenHelper {
    private static int VERSION = 1;
    private static String NAME = "nga.db";
    private static final String TAG = "JustDemo SQLiteUtil";
    
    private String SQL_CREATE_TABLE_USER = "create table if not exists user (uid INTEGER primary key not null, nickname TEXT not null, email TEXT not null)";
    
    private static SQLiteUtil mSQLiteUtil;
    
    /**
     * @param context
     * @param name 数据库名称
     * @param factory 
     * @param version 版本号
     */
    public SQLiteUtil(Context context, String name, CursorFactory factory,
            int version) {
        super(context, name, factory, version);
    }

    public SQLiteUtil(Context context){
        this(context, NAME, null, VERSION );
    }
    
    public SQLiteUtil getInstance(Context context){
        if(mSQLiteUtil == null){
            mSQLiteUtil = new SQLiteUtil(context);
        }
        return mSQLiteUtil;
    }
    
    public void insert(){
        this.getWritableDatabase();
    }
    
    /**
     * <p>Title: onCreate</p>
     * <p>Description: </p>
     * @param db
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
    }

    /**
     * <p>Title: onUpgrade</p>
     * <p>Description: </p>
     * @param db
     * @param oldVersion
     * @param newVersion
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        
    }
}
