package com.zfl.weixin.model.norm_model.artcategory;

import com.zfl.weixin.BaseModel;
import com.zfl.weixin.entity.ArtCategory;

import java.util.List;

/**
 * Created by Administrator on 2016/6/10.<br>
 * 继承于BaseModel，后续可能有本地获取以及网络获取两种model
 */
public interface IArtCategoryModel extends BaseModel {
    /**
     * 获取微信精选文章类别
     *
     * @return
     */
    void loadArtCategorys(getArtCatCallback callback);

    /**
     * 本地model专用,将ArtCategory信息存储到数据库中
     */
    void saveArtCategorys(List<ArtCategory> list);

    /**
     * callback.监听load ArtCat 的状态
     */
    interface getArtCatCallback {
        /**
         * 成功，返回ArtCategory列表
         *
         * @param list
         */
        void onSuccess(List<ArtCategory> list);

        /**
         * 失败，返回失败原因String
         *
         * @param reason
         */
        void onFail(String reason);
    }


}
