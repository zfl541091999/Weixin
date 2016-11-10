package com.zfl.weixin.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/6/11.
 */
public class DBHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "weixin.db";

    static final int VERSION = 2;
    //创建文章类别数据库
    static final String SQL_CREATE_ART_CAT_TABLE =
            "create table article_category(_id integer primary key autoincrement," +
                    "cid text,name text)";
    //创建文章数据库
    static final String SQL_CREATE_ART_TABLE =
            "create table article(_id integer primary key autoincrement," +
                    "cid text,id text,pubtime text,url text,thumbnail text,subtitle text,title text)";
    //创建喜爱文章数据库
    static final String SQL_CREATE_FAVOR_ART_TABLE =
            "create table favor_article(_id integer primary key autoincrement," +
                    "cid text,id text,pubtime text,url text,thumbnail text,subtitle text,title text)";

    static final String SQL_DROP_ART_CAT_TABLE =
            "drop table if exists article_category";
    static final String SQL_DROP_ART_TABLE =
            "drop table if exists article";
    static final String SQL_DROP_FAVOR_ART_TABLE =
            "drop table if exists favor_article";
    //单例数据库导致mContext无法释放,内存泄漏，否决
    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ART_CAT_TABLE);
        db.execSQL(SQL_CREATE_ART_TABLE);
        db.execSQL(SQL_CREATE_FAVOR_ART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_ART_CAT_TABLE);
        db.execSQL(SQL_DROP_ART_TABLE);
        db.execSQL(SQL_DROP_FAVOR_ART_TABLE);
        db.execSQL(SQL_CREATE_ART_CAT_TABLE);
        db.execSQL(SQL_CREATE_ART_TABLE);
        db.execSQL(SQL_CREATE_FAVOR_ART_TABLE);
    }
}
