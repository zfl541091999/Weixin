package com.zfl.weixin.weixinfavor;

import com.zfl.weixin.entity.Article;
import com.zfl.weixin.model.rxmodel.favor_artlist.FavorArtListRxModel;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/7/12.
 */
public class FavorPresenter implements FavorContract.Presenter{

    FavorContract.View mFavorView;

    FavorArtListRxModel mRxModel;

    public FavorPresenter(FavorContract.View favorView, FavorArtListRxModel rxModel) {
        mFavorView = favorView;
        mRxModel = rxModel;
    }

    @Override
    public void getFavorArticles() {
        mFavorView.setLoading(true);
        mRxModel.local().subscribe(new Subscriber<List<Article>>() {
            @Override
            public void onCompleted() {
                mFavorView.setLoading(false);
            }

            @Override
            public void onError(Throwable e) {
                if ("NULL".equals(e.getMessage())) {
                    //显示收藏列表为空
                    mFavorView.showEmptyView();
                } else {
                    mFavorView.showErrorView(e.getMessage());
                }
            }

            @Override
            public void onNext(List<Article> list) {
                mFavorView.updateList(list);
            }
        });
    }

    @Override
    public void removeFavorArticle(Article article) {
        mRxModel.removeFavorArticle(article).subscribe(new Subscriber<Article>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Article article) {
                mFavorView.showMsg("文章("+article.getTitle()+")已移出收藏列表！");
            }
        });
    }

    @Override
    public void start() {
        getFavorArticles();
    }
}
