package com.zfl.weixin.weixinweb;

import com.zfl.weixin.BasePresenter;
import com.zfl.weixin.BaseView;
import com.zfl.weixin.entity.Article;

/**
 * Created by Administrator on 2016/7/10.
 */
public class WebContract {


    interface View extends BaseView<Presenter>{
        /**
         * 显示当前网页按钮
         */
        void showFavorButton();

        /**
         * 隐藏当前网页按钮
         */
        void hideFavorButton();

        /**
         * 设置当前网页收藏按钮
         * @param isFavor
         */
        void setFavorButtonSrc(boolean isFavor);

        /**
         *展示消息，在收藏或者取消收藏后，给用户一定的提示
         * 暂时以Toast的形式通知用户
         * @param msg
         */
        void showMessage(String msg);
    }


    interface Presenter extends BasePresenter{
        /**
         * 提供接口：收藏喜欢的文章
         * @param article
         */
        void saveFavorArticle(Article article);

        /**
         * 提供接口：取消收藏文章
         * @param article
         */
        void removeFavorArticle(Article article);

        /**
         * 判断当前浏览的文章是否是收藏文章
         * @param article
         * @return
         */
        void isFavorArticle(Article article);
    }

}
