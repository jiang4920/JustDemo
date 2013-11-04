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
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-8-8 下午1:14:41
 */
public class SQLiteUtil extends SQLiteOpenHelper {
    private static int VERSION = 11;
    private static String NAME = "nga.db";
    private static final String TAG = "JustDemo SQLiteUtil";

    public static final String TABLE_USER = "USER";
    public static final String TABLE_TOPIC_HISTORY = "TOPIC_HISTORY";

    private String SQL_CREATE_TABLE_USER = "create table if not exists "
            + TABLE_USER
            + " (_id INTEGER primary key autoincrement ,uid INTEGER, username TEXT not null,nickname TEXT not null, email TEXT not null, password TEXT,expiretime INTEGER, loginTime INTEGER not null, keepLogin INTEGER default(0), cookie TEXT)";

    private String SQL_CREATE_TABLE_TOPIC = "create table if not exists "
            + TABLE_TOPIC_HISTORY
            + " (tid INTEGER primary key not null, topic TEXT, content TEXT, page INTEGER, readtime INTEGER)";

    private String SQL_DELETE_TABLE = "drop table ";

    private static SQLiteUtil mSQLiteUtil;

    private static SQLiteDatabase mDb;

    /**
     * @param context
     * @param name
     *            数据库名称
     * @param factory
     * @param version
     *            版本号
     */
    public SQLiteUtil(Context context, String name, CursorFactory factory,
            int version) {
        super(context, name, factory, version);
    }

    public SQLiteUtil(Context context) {
        this(context, NAME, null, VERSION);
    }

    public static SQLiteDatabase getInstance(Context context) {
        if (mSQLiteUtil == null) {
            mSQLiteUtil = new SQLiteUtil(context);
        }
        if (mDb == null) {
            mDb = mSQLiteUtil.getWritableDatabase();
        }
        return mDb;
    }

    public void insert() {
        this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_TOPIC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL(SQL_DELETE_TABLE + TABLE_USER);
            db.execSQL(SQL_CREATE_TABLE_USER);
//            db.execSQL(SQL_DELETE_TABLE + TABLE_TOPIC_HISTORY);
//            db.execSQL(SQL_CREATE_TABLE_TOPIC);
        }
    }
}
