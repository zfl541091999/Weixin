package com.zfl.weixin.model.rxmodel.favor_artlist;

import com.zfl.weixin.entity.Article;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/7/11.
 */
public interface FavorArtListObservable {
    /**
     * 这个接口是给收藏页面使用的
     * @return
     */
    Observable<List<Article>> loadFavorArticles();

    /**
     * 这个接口是给WebActivity使用的
     * @param article
     */
    Observable<Article> saveFavorArticle(Article article);

    /**
     * 这个接口收藏页面和WebActivity均可使用
     * @param article
     */
    Observable<Article> removeFavorArticle(Article article);

    /**
     * 这个接口是WebActivity使用，查询当前Article是否属于收藏文章
     * @param article
     * @return
     */
    Observable<String> isFavorArticle(Article article);



}
