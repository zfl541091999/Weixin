package com.zfl.weixin.model.rxmodel.articlelist.local;

import com.zfl.weixin.entity.Article;
import com.zfl.weixin.model.rxmodel.articlelist.ArtListObservable;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/6/18.
 */
public abstract class ArtListLocalObservable implements ArtListObservable {
    @Override
    public abstract Observable<List<Article>> loadArticleList(String cid, int page);

    @Override
    public abstract void saveArticleList(List<Article> list);
}
