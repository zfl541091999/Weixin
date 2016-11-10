package com.zfl.weixin.model.rxmodel.artcategory.local;

import android.content.Context;

import com.zfl.weixin.entity.ArtCategory;
import com.zfl.weixin.model.db.ArtCatLocalDAO;
import com.zfl.weixin.model.db.ArtCatLocalDAOImpl;
import com.zfl.weixin.utils.LogUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/6/17.
 */
public class ArtCatLocalObservableImpl extends ArtCatLocalObservable{

    private static final String TAG = "ArtCatLocalObservableImpl";

    ArtCatLocalDAO mDAO;

    public ArtCatLocalObservableImpl(Context context) {
        mDAO = new ArtCatLocalDAOImpl(context);
    }

    @Override
    public Observable<List<ArtCategory>> getArtCategorys() {
        return Observable.create(new Observable.OnSubscribe<List<ArtCategory>>() {
            @Override
            public void call(Subscriber<? super List<ArtCategory>> subscriber) {
                List<ArtCategory> list = mDAO.getArticleCategorys();
                //判断SQLite数据库是否有数据存在
                if (list.size() == 0) {
                    subscriber.onError(new Throwable("SQLiteBase has nothing!"));
                } else {
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void saveArtCategorys(final List<ArtCategory> list) {
        Observable.create(new Observable.OnSubscribe<List<ArtCategory>>() {
            @Override
            public void call(Subscriber<? super List<ArtCategory>> subscriber) {
                LogUtils.i(TAG,"Now we cache it!");
                mDAO.cleanArticleCategorys();
                mDAO.saveArticleCategorys(list);
            }
        }).subscribeOn(Schedulers.io()).subscribe();

    }


}
