package com.zfl.weixin.model.rxmodel.artcategory;

import com.zfl.weixin.entity.ArtCategory;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/6/17.
 */
public interface ArtCatObservable {

    Observable<List<ArtCategory>> getArtCategorys();

    void saveArtCategorys(List<ArtCategory> list);

}
