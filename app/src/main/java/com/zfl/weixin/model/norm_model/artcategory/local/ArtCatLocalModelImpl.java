package com.zfl.weixin.model.norm_model.artcategory.local;

import android.content.Context;

import com.zfl.weixin.entity.ArtCategory;
import com.zfl.weixin.model.db.ArtCatLocalDAO;
import com.zfl.weixin.model.db.ArtCatLocalDAOImpl;

import java.util.List;

/**
 * Created by Administrator on 2016/6/15.
 */
public class ArtCatLocalModelImpl extends ArtCatLocalModel {

    Context mContext;

    ArtCatLocalDAO mDAO;

    public ArtCatLocalModelImpl(Context context) {
        mContext = context;
        mDAO = new ArtCatLocalDAOImpl(context);
    }


    @Override
    public void loadArtCategorys(getArtCatCallback callback) {

        List<ArtCategory> list = mDAO.getArticleCategorys();
        if(list.size() > 0) {
            callback.onSuccess(list);
        } else {
            callback.onFail("没有缓存数据！");
        }
    }

    @Override
    public void saveArtCategorys(List<ArtCategory> list) {
        mDAO.cleanArticleCategorys();
        mDAO.saveArticleCategorys(list);
    }

}
