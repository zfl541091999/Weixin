package com.zfl.weixin.weixinfavor;

import com.zfl.weixin.BasePresenter;
import com.zfl.weixin.BaseView;
import com.zfl.weixin.entity.Article;

import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public class FavorContract {

    interface View extends BaseView<Presenter>{
        /**
         * 更新收藏文章列表
         * @param list
         */
        void updateList(List<Article> list);

        /**
         * 是否显示正在更新的UI
         * @param loading
         */
        void setLoading(boolean loading);

        /**
         * 当没有收藏文章时，展示空白View
         */
        void showEmptyView();

        /**
         * 当遇到其他错误时，暂时错误View
         * @param msg
         */
        void showErrorView(String msg);

        /**
         * 展示信息
         * @param msg
         */
        void showMsg(String msg);
    }

    interface Presenter extends BasePresenter{
        /**
         * 获取收藏文章列表
         */
        void getFavorArticles();

        /**
         * 取消收藏文章
         * @param article
         */
        void removeFavorArticle(Article article);
    }
}
