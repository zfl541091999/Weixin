package com.zfl.weixin.model.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zfl.weixin.entity.Article;
import com.zfl.weixin.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */
public class FavorArticleDAOImpl implements FavorArticleDAO {

    private static final String TAG = "FavorArticleDAOImpl";

    DBHelper mHelper;

    public FavorArticleDAOImpl(Context context) {
        mHelper = new DBHelper(context);
    }

    @Override
    public List<Article> getFavorArticles() {
        List<Article> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from favor_article",null);
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
        cursor.close();
        return list;
    }

    @Override
    public void saveFavorArticle(Article article) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL(
                "insert into favor_article(cid,id,pubtime,url,thumbnail,subtitle,title) values(?,?,?,?,?,?,?)",
                new String[]{article.getCid(),
                        article.getId(),
                        article.getPubTime(),
                        article.getSourceUrl(),
                        article.getThumbnails(),
                        article.getSubTitle(),
                        article.getTitle()});
    }

    @Override
    public void removeFavorArticle(Article article) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String article_id = article.getId();
        db.execSQL("delete from favor_article where id = ?",new String[]{article_id});

    }

    @Override
    public boolean isFavorArticle(Article article) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String article_id = article.getId();
        Cursor cursor = db.rawQuery("select * from favor_article where id = ?",new String[]{article_id});
        boolean isFavor = cursor.moveToNext();
        LogUtils.i(TAG,"is Favor? "+isFavor);
        cursor.close();
        return isFavor;
    }


}
