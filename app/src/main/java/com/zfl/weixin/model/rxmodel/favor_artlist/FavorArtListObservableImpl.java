package com.zfl.weixin.model.rxmodel.favor_artlist;

import android.content.Context;

import com.zfl.weixin.entity.Article;
import com.zfl.weixin.model.db.FavorArticleDAO;
import com.zfl.weixin.model.db.FavorArticleDAOImpl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/7/11.
 */
public class FavorArtListObservableImpl implements FavorArtListObservable {

    FavorArticleDAO mDAO;

    public FavorArtListObservableImpl(Context context) {
        mDAO = new FavorArticleDAOImpl(context);
    }

    @Override
    public Observable<List<Article>> loadFavorArticles() {
        return Observable.create(new Observable.OnSubscribe<List<Article>>() {
            @Override
            public void call(Subscriber<? super List<Article>> subscriber) {
                List<Article> list = mDAO.getFavorArticles();
                if (list.size() > 0) {
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Throwable("NULL"));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Article> saveFavorArticle(final Article article) {
        //延时一秒
        return Observable.timer(1, TimeUnit.SECONDS).map(new Func1<Long, Article>() {
            @Override
            public Article call(Long aLong) {
                mDAO.saveFavorArticle(article);
                return null;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Observable<Article> removeFavorArticle(final Article article) {
        //延时一秒
        return Observable.timer(1, TimeUnit.SECONDS).map(new Func1<Long, Article>() {
            @Override
            public Article call(Long aLong) {
                mDAO.removeFavorArticle(article);
                return article;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 用了非常土的办法....返回字符串让Presenter判断
     * 如果是"yes"，Presenter返回true
     * @param article
     * @return
     */
    public Observable<String> isFavorArticle(final Article article) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                boolean isFavor = mDAO.isFavorArticle(article);
                if (isFavor) {
                    subscriber.onNext("yes");
                } else {
                    subscriber.onNext("no");
                }
                subscriber.onCompleted();
            }
        }).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


}
