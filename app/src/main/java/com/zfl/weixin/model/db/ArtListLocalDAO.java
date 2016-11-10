package com.zfl.weixin.model.db;

import com.zfl.weixin.entity.Article;

import java.util.List;

/**
 * Created by Administrator on 2016/6/11.
 */
public interface ArtListLocalDAO {
    /**
     * 获取本地缓存的Articles
     * @return
     */
    List<Article> getArticles(String cid);
    /**
     * 清除本地缓存的Articles
     */
    void cleanArticles(String cid);

    /**
     * 缓存网络得来的Articles
     * @param list
     */
    void saveArticles(List<Article> list);

}
