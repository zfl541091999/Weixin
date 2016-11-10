package com.zfl.weixin.model.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zfl.weixin.entity.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/11.
 */
public class ArtListLocalDAOImpl implements ArtListLocalDAO {

    DBHelper mHelper;

    public ArtListLocalDAOImpl(Context context) {
        mHelper = new DBHelper(context);
    }

    @Override
    public List<Article> getArticles(String cid) {
        List<Article> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from article where cid = ?", new String[]{cid});
        while (cursor.moveToNext()) {
            String art_cid = cursor.getString(cursor.getColumnIndex("cid"));
            String art_id = cursor.getString(cursor.getColumnIndex("id"));
            String art_time = cursor.getString(cursor.getColumnIndex("pubtime"));
            String art_title = cursor.getString(cursor.getColumnIndex("title"));
            String art_subtitle = cursor.getString(cursor.getColumnIndex("subtitle"));
            String art_url = cursor.getString(cursor.getColumnIndex("url"));
            String art_thumbnail = cursor.getString(cursor.getColumnIndex("thumbnail"));
            Article article = new Article(art_cid, art_id, art_time, art_subtitle, art_url, art_thumbnail, art_title);
            list.add(article);
        }
        return list;
    }

    @Override
    public void cleanArticles(String cid) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("delete from article where cid = ?", new String[]{cid});
    }

    @Override
    public void saveArticles(List<Article> list) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (Article article : list) {
                db.execSQL(
                        "insert into article(cid,id,pubtime,url,thumbnail,subtitle,title) values(?,?,?,?,?,?,?)",
                        new String[]{article.getCid(),
                                article.getId(),
                                article.getPubTime(),
                                article.getSourceUrl(),
                                article.getThumbnails(),
                                article.getSubTitle(),
                                article.getTitle()});
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
}
