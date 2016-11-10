package com.zfl.weixin.model.norm_model.articlelist.local;

import com.android.volley.RequestQueue;
import com.zfl.weixin.entity.Article;
import com.zfl.weixin.model.norm_model.articlelist.IArticleListModel;

import java.util.List;

/**
 * Created by Administrator on 2016/6/11.
 */
public abstract class ArtListLocalModel implements IArticleListModel{

    @Override
    public abstract void loadArticleList(getArtListCallBack callBack, String cid);

    @Override
    public void loadMoreArticleList(getArtListCallBack callBack, String cid, int page) {
        //do nothing~
    }

    @Override
    public abstract void saveArticleList(List<Article> list);

    @Override
    public void setRequestQueue(RequestQueue queue) {
        //do nothing
    }
}
