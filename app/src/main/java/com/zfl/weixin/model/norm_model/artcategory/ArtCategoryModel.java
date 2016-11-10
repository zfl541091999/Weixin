package com.zfl.weixin.model.norm_model.artcategory;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.zfl.weixin.entity.ArtCategory;
import com.zfl.weixin.model.norm_model.artcategory.local.ArtCatLocalModelImpl;
import com.zfl.weixin.model.norm_model.artcategory.network.ArtCatNetModelImpl;
import com.zfl.weixin.utils.BooleanUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/6/10.
 */
public class ArtCategoryModel implements IArtCategoryModel {

    static IArtCategoryModel sModel;

    public static IArtCategoryModel getInstance(Context context) {
        if (sModel == null) sModel = new ArtCategoryModel(context);
        return sModel;
    }

    private ArtCategoryModel(Context context) {
        mNetModel = new ArtCatNetModelImpl();
        mLocalModel = new ArtCatLocalModelImpl(context);
    }
    //网络获取数据模块
    IArtCategoryModel mNetModel;
    //本地获取数据模块
    IArtCategoryModel mLocalModel;

    @Override
    public void loadArtCategorys(final getArtCatCallback callback) {
        mNetModel.loadArtCategorys(new getArtCatCallback() {
            @Override
            public void onSuccess(List<ArtCategory> list) {
                callback.onSuccess(list);
                saveArtCategorys(list);
            }

            @Override
            public void onFail(String reason) {
                if (BooleanUtils.CacheEnable) {
                    mLocalModel.loadArtCategorys(new getArtCatCallback() {
                        @Override
                        public void onSuccess(List<ArtCategory> list) {
                            callback.onSuccess(list);
                        }

                        @Override
                        public void onFail(String reason) {
                            callback.onFail(reason);
                        }
                    });
                } else {
                    callback.onFail(reason);
                }

            }
        });
    }

    @Override
    public void saveArtCategorys(List<ArtCategory> list) {
        //在这里进行本地model的数据更新，缓存工作
        mLocalModel.saveArtCategorys(list);
    }

    @Override
    public void setRequestQueue(RequestQueue queue) {
        mNetModel.setRequestQueue(queue);
    }
}
