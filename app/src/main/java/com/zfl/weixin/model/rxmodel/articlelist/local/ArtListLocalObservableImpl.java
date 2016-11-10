package com.zfl.weixin.model.rxmodel.articlelist.local;

import android.content.Context;

import com.zfl.weixin.entity.Article;
import com.zfl.weixin.model.db.ArtListLocalDAO;
import com.zfl.weixin.model.db.ArtListLocalDAOImpl;
import com.zfl.weixin.utils.LogUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/6/18.
 */
public class ArtListLocalObservableImpl extends ArtListLocalObservable {

    private static final String TAG = "ArtListLocalObservableImpl";
    
    ArtListLocalDAO mDAO;

    public ArtListLocalObservableImpl(Context context) {
        mDAO = new ArtListLocalDAOImpl(context);
    }

    @Override
    public Observable<List<Article>> loadArticleList(final String cid, int page) {
        return Observable.create(new Observable.OnSubscribe<List<Article>>() {
            @Override
            public void call(Subscriber<? super List<Article>> subscriber) {
                LogUtils.i(TAG,"Now we load from Local!");
                List<Article> list = mDAO.getArticles(cid);
                if (list.size() == 0) {
                    subscriber.onError(new Throwable("The Data is null!"));
                } else {
                    subscriber.onNext(mDAO.getArticles(cid));
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void saveArticleList(final List<Article> list) {
        Observable.create(new Observable.OnSubscribe<List<Article>>() {
            @Override
            public void call(Subscriber<? super List<Article>> subscriber) {
                LogUtils.i(TAG,"Now we save the artlist to SQLite!");
                String cid = list.get(0).getCid();
                mDAO.cleanArticles(cid);
                mDAO.saveArticles(list);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }
}
