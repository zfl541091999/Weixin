package com.zfl.weixin.model.rxmodel.favor_artlist;

import android.content.Context;

import com.zfl.weixin.entity.Article;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/7/11.<br>
 *     不仅能在WebActivity使用,貌似还能在随后开发的文章收藏页面使用？
 */
public class FavorArtListRxModel {
    //目前只有本地的Observable
    FavorArtListObservable mObservable;

    public FavorArtListRxModel(Context context){
        mObservable = new FavorArtListObservableImpl(context);
    }
    /**
     * 从本地数据库读取收藏文章
     */
    public Observable<List<Article>> local(){
        return mObservable.loadFavorArticles();
    }

    /**
     * 从网络服务器读取收藏文章(此功能无法实现)
     * @return
     */
    public Observable<List<Article>> net(){
        return null;
    }

    /**
     * 收藏文章
     * @param article
     */
    public Observable<Article> saveFavorArticle(Article article){
        return mObservable.saveFavorArticle(article);
    }

    /**
     * 取消收藏文章
     * @param article
     */
    public Observable<Article> removeFavorArticle(Article article){
        return mObservable.removeFavorArticle(article);
    }


    public Observable<String> isFavorArticle(Article article){
        return mObservable.isFavorArticle(article);
    }


}
