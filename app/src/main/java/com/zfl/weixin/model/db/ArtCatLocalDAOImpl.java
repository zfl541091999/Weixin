package com.zfl.weixin.model.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zfl.weixin.entity.ArtCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/11.
 */
public class ArtCatLocalDAOImpl implements ArtCatLocalDAO{

    DBHelper mHelper;

    public ArtCatLocalDAOImpl(Context context) {
        mHelper = new DBHelper(context);
    }

    @Override
    public List<ArtCategory> getArticleCategorys() {
        List<ArtCategory> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from article_category", null);
        while (cursor.moveToNext()) {
            String cid = cursor.getString(cursor.getColumnIndex("cid"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            ArtCategory artCategory = new ArtCategory(cid, name);
            list.add(artCategory);
        }
        return list;
    }

    @Override
    public void cleanArticleCategorys() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("delete from article_category");
    }

    @Override
    public void saveArticleCategorys(List<ArtCategory> list) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ArtCategory artCategory : list) {
                db.execSQL(
                        "insert into article_category(cid,name) values(?,?)",
                        new String[]{artCategory.getCid(), artCategory.getName()});
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
}
