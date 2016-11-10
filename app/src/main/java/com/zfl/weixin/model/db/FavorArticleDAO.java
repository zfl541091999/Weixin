package com.zfl.weixin.model.db;

import com.zfl.weixin.entity.Article;

import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */
public interface FavorArticleDAO {
    /**
     * 获取收藏的文章
     * @return
     */
    List<Article> getFavorArticles();

    /**
     * 收藏文章
     * @param article
     */
    void saveFavorArticle(Article article);

    /**
     * 取消收藏文章
     * @param article
     */
    void removeFavorArticle(Article article);

    /**
     * 判断当前文章是否是收藏文章
     * @param article
     * @return
     */
    boolean isFavorArticle(Article article);

}
