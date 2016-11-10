package com.zfl.weixin.model.norm_model.articlelist.network;

import com.android.volley.RequestQueue;
import com.zfl.weixin.entity.Article;
import com.zfl.weixin.model.norm_model.articlelist.IArticleListModel;

import java.util.List;

/**
 * Created by Administrator on 2016/6/11.
 */
public abstract class ArtListNetModel implements IArticleListModel {

    @Override
    public abstract void loadArticleList(final getArtListCallBack callBack, String cid);

    @Override
    public abstract void loadMoreArticleList(final getArtListCallBack callBack, String cid, int page);

    @Override
    public void saveArticleList(List<Article> list) {
        //do nothing~
    }
    @Override
    public abstract void setRequestQueue(RequestQueue queue);
}
