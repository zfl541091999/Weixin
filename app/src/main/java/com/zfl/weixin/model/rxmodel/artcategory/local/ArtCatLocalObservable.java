package com.zfl.weixin.model.rxmodel.artcategory.local;

import com.zfl.weixin.entity.ArtCategory;
import com.zfl.weixin.model.rxmodel.artcategory.ArtCatObservable;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/6/17.
 */
public abstract class ArtCatLocalObservable implements ArtCatObservable {

    @Override
    public abstract Observable<List<ArtCategory>> getArtCategorys();

    @Override
    public abstract void saveArtCategorys(List<ArtCategory> list);
}
