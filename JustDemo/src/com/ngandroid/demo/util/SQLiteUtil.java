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
    private static int VERSION = 9;
    private static String NAME = "nga.db";
    private static final String TAG = "JustDemo SQLiteUtil";

    public static final String TABLE_USER = "USER";
    public static final String TABLE_TOPIC_HISTORY = "TOPIC_HISTORY";

    private String SQL_CREATE_TABLE_USER = "create table if not exists "
            + TABLE_USER
            + " (uid INTEGER primary key not null, nickname TEXT not null, email TEXT not null, password TEXT,expiretime INTEGER, loginTime INTEGER not null, keepLogin INTEGER default(0), cookie TEXT)";

    /*
     * private int tid; //主题id private int fid; //主题所在版面id private int
     * quote_from;//引用自主题 private String quote_to;//此主题引用到主题 private int
     * icon;//图标 private String titlefont;//标题样式 private String author;//作者
     * private int authorid;//作者uid private String subject;//标题 private int
     * ifmark; private int type;//主题类型bit private int type_2; private long
     * postdate;//发帖时间 private long lastpost;//最后回复时间 private String
     * lastposter;//最后回复人 private int replies;//回复数量 (回复页数=回复数量/__R__ROWS_PAGE
     * private int locked; private int digest; private int ifupload; private
     * long lastmodify;//最后修改时间 private int recommend; private int
     * admin_ui;//用户是否对此主题有权限bit private String tpcurl;//主题地址 private String
     * ispage;
     */
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

    /**
     * <p>
     * Title: onCreate
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param db
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_TOPIC);
    }

    /**
     * <p>
     * Title: onUpgrade
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param db
     * @param oldVersion
     * @param newVersion
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase,
     *      int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL(SQL_DELETE_TABLE + TABLE_USER);
            db.execSQL(SQL_CREATE_TABLE_USER);
            db.execSQL(SQL_DELETE_TABLE + TABLE_TOPIC_HISTORY);
            db.execSQL(SQL_CREATE_TABLE_TOPIC);
        }
    }
}
