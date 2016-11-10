package com.zfl.weixin.weixin.articlelist;

import com.zfl.weixin.BasePresenter;
import com.zfl.weixin.BaseView;
import com.zfl.weixin.entity.Article;

import java.util.List;

/**
 * Created by Administrator on 2016/6/8.
 */
public class ArticleListContract {

    interface View extends BaseView<Presenter> {
        /**
         * 刷新精选文章列表界面<br>
         * 操作过程：View的List清空，然后添加list的数据
         */
        void updateArtList(List<Article> list);

        /**
         * 上拉刷新成功获得数据后，将list传回View
         * 操作过程：View的List直接加上list的数据
         * @param list
         */
        void addMoreArtList(List<Article> list);

        /**
         * 设置refresh layout是否处在刷新状态
         * @param refreshing
         */
        void setLoading(boolean refreshing);

        /**
         * 当加载出错后，显示错误原因
         * @param reason
         */
        void showErrorMessage(String reason);

        /**
         * 隐藏掉错误信息Text,用于刷新,并且成功获取到数据的情形
         */
        void hideErrorMessage();

        /**
         * 上拉刷新时,显示加载进度动画,隐藏上拉刷新错误Text
         */
        void showLoadMoreLabel();

        /**
         * 上拉刷新遇到错误时，显示上拉刷新错误Text,隐藏加载进度动画
         */
        void showErrorFooter(String reason);

        /**
         * 设置每个Fragment的cid(ArtCatFragment这边专用)
         * @param cid
         */
        void setCid(String cid);

        /**
         * 获取View的cid,用于presenter判断哪个View需要更新
         */
        String getCid();


    }


    interface Presenter extends BasePresenter {

        /**
         * 请求model层获取ArticleList(通常是首页数据)
         */
        void getArticles(String cid);

        /**
         * 获取更多的文章（上拉刷新）
         *
         * @param cid
         * @param page
         */
        void getMoreArticles(String cid, int page);

        /**
         * 将ArtListView加入Presenter
         * @param view
         */
        void addArtListView(View view);

        /**
         * 将ArtListView移除叼Presenter
         * @param view
         */
        void removeArtListView(View view);

        /**
         * 添加自定义的start方法
         */
        void start(String cid);

    }
}
