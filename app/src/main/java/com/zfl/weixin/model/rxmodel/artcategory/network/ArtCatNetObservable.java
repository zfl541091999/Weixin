package com.zfl.weixin.model.rxmodel.artcategory.network;

import com.zfl.weixin.entity.ArtCategory;
import com.zfl.weixin.model.rxmodel.artcategory.ArtCatObservable;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/6/17.
 */
public abstract class ArtCatNetObservable implements ArtCatObservable {

    private static final String TAG = "ArtCatNetObservable";

    @Override
    public abstract Observable<List<ArtCategory>> getArtCategorys();

    @Override
    public void saveArtCategorys(List<ArtCategory> list) {
        //do nothing
        //接口置空
    }
}
