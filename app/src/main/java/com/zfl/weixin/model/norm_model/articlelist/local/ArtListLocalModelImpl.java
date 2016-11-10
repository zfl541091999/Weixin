package com.zfl.weixin.model.norm_model.articlelist.local;

import android.content.Context;

import com.zfl.weixin.entity.Article;
import com.zfl.weixin.model.db.ArtListLocalDAO;
import com.zfl.weixin.model.db.ArtListLocalDAOImpl;

import java.util.List;

/**
 * Created by Administrator on 2016/6/15.
 */
public class ArtListLocalModelImpl extends ArtListLocalModel{

    ArtListLocalDAO mDAO;

    public ArtListLocalModelImpl(Context context) {
        mDAO = new ArtListLocalDAOImpl(context);
    }

    @Override
    public void loadArticleList(getArtListCallBack callBack, String cid) {
        List<Article> list = mDAO.getArticles(cid);
        if(list.size() > 0 ){
            callBack.onSuccess(list);
        } else {
            callBack.onFail("没有缓存数据！");
        }
    }

    @Override
    public void saveArticleList(List<Article> list) {
        String cid = list.get(0).getCid();
        mDAO.cleanArticles(cid);
        mDAO.saveArticles(list);
    }

}
