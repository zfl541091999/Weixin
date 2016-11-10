package com.zfl.weixin.model.norm_model.artcategory.network;

import com.android.volley.RequestQueue;
import com.zfl.weixin.entity.ArtCategory;
import com.zfl.weixin.model.norm_model.artcategory.IArtCategoryModel;

import java.util.List;

/**
 * Created by Administrator on 2016/6/11.
 */
public abstract class ArtCatNetModel implements IArtCategoryModel{

    @Override
    public abstract void loadArtCategorys(final IArtCategoryModel.getArtCatCallback callback);


    @Override
    public void saveArtCategorys(List<ArtCategory> list) {
        //do nothing
        //在这里将不必实现的接口置空
    }

    @Override
    public abstract void setRequestQueue(RequestQueue queue);
}
