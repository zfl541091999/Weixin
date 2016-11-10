package com.zfl.weixin.model.norm_model.artcategory.local;

import com.android.volley.RequestQueue;
import com.zfl.weixin.entity.ArtCategory;
import com.zfl.weixin.model.norm_model.artcategory.IArtCategoryModel;

import java.util.List;

/**
 * Created by Administrator on 2016/6/11.
 */
public abstract class ArtCatLocalModel implements IArtCategoryModel {

    @Override
    public abstract void loadArtCategorys(getArtCatCallback callback);

    @Override
    public abstract void saveArtCategorys(List<ArtCategory> list);

    @Override
    public void setRequestQueue(RequestQueue queue) {
        //do nothing
        //在这里将不需要用到的接口置空
    }
}
