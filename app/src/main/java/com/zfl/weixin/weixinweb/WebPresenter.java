package com.zfl.weixin.weixinweb;

import com.zfl.weixin.entity.Article;
import com.zfl.weixin.model.rxmodel.favor_artlist.FavorArtListRxModel;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/7/10.
 */
public class WebPresenter implements WebContract.Presenter {


    WebContract.View mWebView;

    FavorArtListRxModel mRxModel;

    public WebPresenter(WebContract.View webview, FavorArtListRxModel model) {
        mWebView = webview;
        mRxModel = model;
    }

    @Override
    public void start() {

    }

    @Override
    public void saveFavorArticle(final Article article) {
        //点击后隐藏按钮
        mWebView.hideFavorButton();
        mRxModel.saveFavorArticle(article).subscribe(new Subscriber<Article>() {
            @Override
            public void onCompleted() {
                //判断并且设置FavorButton图标
                isFavorArticle(article);
                //完成后显示按钮
                mWebView.showFavorButton();
                mWebView.showMessage("当前文章已收藏！");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Article article) {

            }
        });
    }

    @Override
    public void removeFavorArticle(final Article article) {
        mWebView.hideFavorButton();
        mRxModel.removeFavorArticle(article).subscribe(new Subscriber<Article>() {
            @Override
            public void onCompleted() {
                //判断并且设置FavorButton图标
                isFavorArticle(article);
                //完成后显示按钮
                mWebView.showFavorButton();
                mWebView.showMessage("当前文章已取消收藏！");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Article article) {

            }
        });
    }

    @Override
    public void isFavorArticle(Article article) {
        mRxModel.isFavorArticle(article).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                if ("yes".equals(s)) {
                    mWebView.setFavorButtonSrc(true);
                } else {
                    mWebView.setFavorButtonSrc(false);
                }
            }
        });
    }
}
