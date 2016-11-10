package com.zfl.weixin.weixin.articlelist;

import com.zfl.weixin.entity.Article;
import com.zfl.weixin.model.norm_model.articlelist.IArticleListModel;
import com.zfl.weixin.model.rxmodel.articlelist.ArticleListRxModel;
import com.zfl.weixin.utils.BooleanUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/6/8.
 */
public class ArticleListPresenter implements ArticleListContract.Presenter {
    //做成单例模式咯！
    static ArticleListContract.Presenter mPresenter;

    public static ArticleListContract.Presenter getInstance(IArticleListModel model, ArticleListRxModel rxModel) {
        if (mPresenter == null) mPresenter = new ArticleListPresenter(model, rxModel);
        return mPresenter;
    }

    private ArticleListPresenter(IArticleListModel model, ArticleListRxModel rxModel) {
        mModel = model;
        mRxModel = rxModel;
        mArtViews = new ArrayList<>();
    }


    List<ArticleListContract.View> mArtViews;
    //正常的获取ArtList的model
    IArticleListModel mModel;
    //Rxjava模式获取ArtList的model
    ArticleListRxModel mRxModel;

    @Override
    public void getArticles(final String cid) {
        //采用Rxjava模式获取文章资源
        if (BooleanUtils.RxJavaEnable) {
            //4S之内没有获取到数据立刻从本地获取数据
            mRxModel.net(cid, 0)
                    .onErrorResumeNext(mRxModel.local(cid))
                    .subscribe(new Subscriber<List<Article>>() {
                @Override
                public void onCompleted() {
                    for (ArticleListContract.View view : mArtViews) {
                        if (view.getCid().equals(cid)) {
                            view.setLoading(false);
                        }
                    }
                }
                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    for (ArticleListContract.View view : mArtViews) {
                        if (view.getCid().equals(cid)) {
                            view.setLoading(false);
                            view.showErrorMessage(e.getMessage());
                        }
                    }
                }
                @Override
                public void onNext(List<Article> list) {
                    String art_cid = list.get(0).getCid();
                    for (ArticleListContract.View view : mArtViews) {
                        if (view.getCid().equals(art_cid)) {
                            view.updateArtList(list);
                        }
                    }
                }
            });
        } else {
            mModel.loadArticleList(new IArticleListModel.getArtListCallBack() {
                @Override
                public void onSuccess(List<Article> list) {
                    String art_cid = list.get(0).getCid();
                    for (ArticleListContract.View view : mArtViews) {
                        if (view.getCid().equals(art_cid)) {
                            view.setLoading(false);
                            view.updateArtList(list);
                        }
                    }
                }

                @Override
                public void onFail(String reason) {
                    for (ArticleListContract.View view : mArtViews) {
                        if (view.getCid().equals(cid)) {
                            view.setLoading(false);
                            view.showErrorMessage(reason);
                        }
                    }
                }
            }, cid);
        }


    }

    @Override
    public void getMoreArticles(final String cid, int page) {
        for (ArticleListContract.View view : mArtViews) {
            if (view.getCid().equals(cid)) {
                view.showLoadMoreLabel();
            }
        }
        if (BooleanUtils.RxJavaEnable) {
            //不会从数据库加载更多
            mRxModel.net(cid, page)
                    .onErrorResumeNext(mRxModel.error("网络请求超时！"))
                    .subscribe(new Subscriber<List<Article>>() {
                @Override
                public void onCompleted() {
                    for (ArticleListContract.View view : mArtViews) {
                        if (view.getCid().equals(cid)) {
                            view.setLoading(false);
                        }
                    }
                }

                @Override
                public void onError(Throwable e) {
                    for (ArticleListContract.View view : mArtViews) {
                        if (view.getCid().equals(cid)) {
                            view.showErrorFooter(e.getMessage());
                        }
                    }
                }

                @Override
                public void onNext(List<Article> list) {
                    String art_cid = list.get(0).getCid();
                    for (ArticleListContract.View view : mArtViews) {
                        if (view.getCid().equals(art_cid)) {
                            view.addMoreArtList(list);
                        }
                    }
                }
            });
        } else {
            mModel.loadMoreArticleList(new IArticleListModel.getArtListCallBack() {
                @Override
                public void onSuccess(List<Article> list) {
                    String art_cid = list.get(0).getCid();
                    for (ArticleListContract.View view : mArtViews) {
                        if (view.getCid().equals(art_cid)) {
                            view.addMoreArtList(list);
                        }
                    }
                }

                @Override
                public void onFail(String reason) {
                    for (ArticleListContract.View view : mArtViews) {
                        if (view.getCid().equals(cid)) {
                            view.showErrorFooter(reason);
                        }
                    }
                }
            }, cid, page);
        }
    }

    @Override
    public void addArtListView(ArticleListContract.View view) {
        mArtViews.add(view);
    }

    @Override
    public void removeArtListView(ArticleListContract.View view) {
        mArtViews.remove(view);
    }

    @Override
    public void start(String cid) {
        for (ArticleListContract.View view : mArtViews) {
            if (view.getCid().equals(cid)) {
                view.setLoading(true);
            }
        }
        getArticles(cid);
    }

    @Override
    public void start() {

    }
}
