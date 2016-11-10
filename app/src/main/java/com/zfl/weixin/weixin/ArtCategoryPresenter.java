package com.zfl.weixin.weixin;

import com.zfl.weixin.entity.ArtCategory;
import com.zfl.weixin.model.norm_model.artcategory.IArtCategoryModel;
import com.zfl.weixin.model.rxmodel.artcategory.ArtCategoryRxModel;
import com.zfl.weixin.utils.BooleanUtils;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/6/8.
 */
public class ArtCategoryPresenter implements ArtCategoryContract.Presenter {

    private static final String TAG = "ArtCategoryPresenter";

    ArtCategoryContract.View mArtCatsView;
    //正常的数据获取model
    IArtCategoryModel mModel;
    //RxJava的数据获取
    ArtCategoryRxModel mRxModel;

    public ArtCategoryPresenter(IArtCategoryModel model, ArtCategoryRxModel rxmodel, ArtCategoryContract.View view) {
        mModel = model;
        mRxModel = rxmodel;
        mArtCatsView = view;
        mArtCatsView.regPresenter(this);
    }

    @Override
    public void loadArtCategorys() {
        mArtCatsView.showLoading();
        //如果以rxjava的形式去获取
        if (BooleanUtils.RxJavaEnable) {
            //因为ArtCat不经常变动，所以优先提取SQLite的数据
            //同时设置了Net访问超时后的处理方法
            mRxModel.local()
                    .onErrorResumeNext(mRxModel.net().onErrorResumeNext(mRxModel.error("网络请求超时！")))
                    .subscribe(new Subscriber<List<ArtCategory>>() {
                @Override
                public void onCompleted() {
                    mArtCatsView.hideLoading();
                    mArtCatsView.hideErrorArea();
                    //解除订阅?
                    this.unsubscribe();
                }

                @Override
                public void onError(Throwable e) {
                    mArtCatsView.hideLoading();
                    mArtCatsView.showErrorArea(e.getMessage());
                    //解除订阅?
                    this.unsubscribe();
                }

                @Override
                public void onNext(List<ArtCategory> list) {
                    mArtCatsView.updateArtCat(list);
                }
            });
        } else {
            mModel.loadArtCategorys(new IArtCategoryModel.getArtCatCallback() {
                @Override
                public void onSuccess(List<ArtCategory> list) {
                    mArtCatsView.hideLoading();
                    mArtCatsView.updateArtCat(list);
                    mArtCatsView.hideErrorArea();
                }

                @Override
                public void onFail(String reason) {
                    mArtCatsView.hideLoading();
                    mArtCatsView.showErrorArea(reason);
                }
            });
        }

    }

    @Override
    public void start() {
        loadArtCategorys();
    }
}
