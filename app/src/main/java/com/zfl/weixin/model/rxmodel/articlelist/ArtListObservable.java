package com.zfl.weixin.model.rxmodel.articlelist;

import com.zfl.weixin.entity.Article;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/6/17.
 */
public interface ArtListObservable {
    /**
     * 加载文章！
     * @param cid 文章类别
     * @param page 文章页数
     * @return
     */
    Observable<List<Article>> loadArticleList(String cid,int page);

    void saveArticleList(List<Article> list);

}
