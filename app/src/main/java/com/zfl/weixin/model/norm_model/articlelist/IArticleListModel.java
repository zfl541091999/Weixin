package com.zfl.weixin.model.norm_model.articlelist;

import com.zfl.weixin.BaseModel;
import com.zfl.weixin.entity.Article;

import java.util.List;

/**
 * Created by Administrator on 2016/6/10.<br>
 * 继承于BaseModel，后续可能有本地获取以及网络获取两种model
 */
public interface IArticleListModel extends BaseModel{
    /**
     * 获取微信精选文章列表<br>
     *
     * @param cid 文章类别
     */
    void loadArticleList(getArtListCallBack callBack, String cid);

    void loadMoreArticleList(getArtListCallBack callBack, String cid,int page);

    /**
     * 将网络获取的文章列表存储到本地数据库中
     * @param list
     */
    void saveArticleList(List<Article> list);

    interface getArtListCallBack {
        /**
         * 成功，返回ArtCategory列表
         *
         * @param list
         */
        void onSuccess(List<Article> list);

        /**
         * 失败，返回失败原因String
         *
         * @param reason
         */
        void onFail(String reason);
    }

}
