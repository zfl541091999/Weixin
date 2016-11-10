package com.zfl.weixin.model.norm_model.articlelist;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.zfl.weixin.entity.Article;
import com.zfl.weixin.model.norm_model.articlelist.local.ArtListLocalModelImpl;
import com.zfl.weixin.model.norm_model.articlelist.network.ArtListNetModelImpl;
import com.zfl.weixin.utils.BooleanUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/6/10.
 */
public class ArticleListModel implements IArticleListModel{

    static IArticleListModel sModel;

    public static IArticleListModel getInstance(Context context) {
        if (sModel == null) sModel = new ArticleListModel(context);
        return sModel;
    }


    private ArticleListModel(Context context) {
        mNetModel = new ArtListNetModelImpl();
        mLocalModel = new ArtListLocalModelImpl(context);
    }

    IArticleListModel mNetModel;

    IArticleListModel mLocalModel;

    @Override
    public void setRequestQueue(RequestQueue queue) {
        mNetModel.setRequestQueue(queue);
    }

    @Override
    public void loadArticleList(final getArtListCallBack callBack, final String cid) {
        mNetModel.loadArticleList(new getArtListCallBack() {
            @Override
            public void onSuccess(List<Article> list) {
                callBack.onSuccess(list);
                saveArticleList(list);
            }

            @Override
            public void onFail(String reason) {
                if(BooleanUtils.CacheEnable){
                    mLocalModel.loadArticleList(new getArtListCallBack() {
                        @Override
                        public void onSuccess(List<Article> list) {
                            callBack.onSuccess(list);
                        }

                        @Override
                        public void onFail(String reason) {
                            callBack.onFail(reason);
                        }
                    },cid);
                } else {
                    callBack.onFail(reason);
                }
            }
        },cid);
    }

    @Override
    public void loadMoreArticleList(final getArtListCallBack callBack, final String cid, int page) {
        mNetModel.loadMoreArticleList(new getArtListCallBack() {
            @Override
            public void onSuccess(List<Article> list) {
                callBack.onSuccess(list);
                saveArticleList(list);
            }

            @Override
            public void onFail(String reason) {
                    callBack.onFail(reason);

            }
        },cid,page);
    }

    @Override
    public void saveArticleList(List<Article> list) {
        mLocalModel.saveArticleList(list);
    }
}
