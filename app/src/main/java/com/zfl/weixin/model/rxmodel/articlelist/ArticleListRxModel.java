package com.zfl.weixin.model.rxmodel.articlelist;

import android.content.Context;

import com.zfl.weixin.entity.Article;
import com.zfl.weixin.model.rxmodel.articlelist.local.ArtListLocalObservableImpl;
import com.zfl.weixin.model.rxmodel.articlelist.network.ArtListNetObservableImpl;
import com.zfl.weixin.utils.LogUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/6/17.
 */
public class ArticleListRxModel {
    private static final String TAG = "ArticleListRxModel";
    //做出单例模式咯！
    static ArticleListRxModel sRxModel;
    public static ArticleListRxModel getInstance(Context context){
        if (sRxModel == null) sRxModel = new ArticleListRxModel(context);
        return sRxModel;
    }

    ArtListObservable mArtListNetObservable;

    ArtListObservable mArtListLocalObservable;

    private ArticleListRxModel(Context context) {
        mArtListNetObservable = new ArtListNetObservableImpl();
        mArtListLocalObservable = new ArtListLocalObservableImpl(context);
    }

    public Observable<List<Article>> local(String cid){
        //page 无意义
        return mArtListLocalObservable.loadArticleList(cid,0)
                .compose(logSource("SQLiteDataBase"));
    }

    /**
     * 带超时机制（4S）
     * @param cid
     * @param page
     * @return
     */
    public Observable<List<Article>> net(String cid,int page){
        return mArtListNetObservable.loadArticleList(cid,page).doOnNext(new Action1<List<Article>>() {
            @Override
            public void call(List<Article> list) {
                mArtListLocalObservable.saveArticleList(list);
            }
        }).compose(logSource("Net")).timeout(4, TimeUnit.SECONDS);
    }

    Observable.Transformer<List<Article>, List<Article>> logSource(final String source) {
        return new Observable.Transformer<List<Article>, List<Article>>() {
            @Override
            public Observable<List<Article>> call(Observable<List<Article>> listObservable) {
                return listObservable.doOnNext(new Action1<List<Article>>() {
                    @Override
                    public void call(List<Article> list) {
                        LogUtils.i(TAG, source + " has the data you are looking for!");
                    }
                });
            }
        };
    }

    /**
     * 发射一个只带有异常的Observable
     * @param reason
     * @return
     */
    public Observable<List<Article>> error(final String reason){
        return Observable.create(new Observable.OnSubscribe<List<Article>>() {
            @Override
            public void call(Subscriber<? super List<Article>> subscriber) {
                subscriber.onError(new Throwable(reason));
            }
        });
    }

}
