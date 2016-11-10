package com.zfl.weixin.model.rxmodel.artcategory;

import android.content.Context;

import com.zfl.weixin.entity.ArtCategory;
import com.zfl.weixin.model.rxmodel.artcategory.local.ArtCatLocalObservableImpl;
import com.zfl.weixin.model.rxmodel.artcategory.network.ArtCatNetObservableImpl;
import com.zfl.weixin.utils.LogUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/6/17.
 */
public class ArtCategoryRxModel {
    private static final String TAG = "ArtCategoryRxModel";
    //本地模块
    ArtCatObservable mArtCatLocalObservable;
    //网络模块
    ArtCatObservable mArtCatNetObservable;

    public ArtCategoryRxModel(Context context) {
        mArtCatNetObservable = new ArtCatNetObservableImpl();
        mArtCatLocalObservable = new ArtCatLocalObservableImpl(context);
    }

    public Observable<List<ArtCategory>> local() {
        return mArtCatLocalObservable.getArtCategorys()
                .compose(logSource("SQLiteDataBase"));
    }

    /**
     * 带超时机制（3S）
     * @return
     */
    public Observable<List<ArtCategory>> net() {
        return mArtCatNetObservable.getArtCategorys().doOnNext(new Action1<List<ArtCategory>>() {
            @Override
            public void call(List<ArtCategory> list) {
                //从网络获取到数据后，清空本地数据库，重新缓存
                mArtCatLocalObservable.saveArtCategorys(list);
            }
        }).compose(logSource("Net")).timeout(3, TimeUnit.SECONDS);
    }

    Observable.Transformer<List<ArtCategory>, List<ArtCategory>> logSource(final String source) {
        return new Observable.Transformer<List<ArtCategory>, List<ArtCategory>>() {
            @Override
            public Observable<List<ArtCategory>> call(Observable<List<ArtCategory>> listObservable) {
                return listObservable.doOnNext(new Action1<List<ArtCategory>>() {
                    @Override
                    public void call(List<ArtCategory> list) {
                        LogUtils.i(TAG, source + " has the data you are looking for!");
                    }
                });
            }
        };
    }

    /**
     * 抛出异常
     * @param reason
     * @return
     */
    public Observable<List<ArtCategory>> error(final String reason){
        return Observable.create(new Observable.OnSubscribe<List<ArtCategory>>() {
            @Override
            public void call(Subscriber<? super List<ArtCategory>> subscriber) {
                subscriber.onError(new Throwable(reason));
            }
        });
    }

}
