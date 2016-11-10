package com.zfl.weixin.weixinskin;

import com.zfl.weixin.model.rxmodel.skin.SkinRxModel;
import com.zfl.weixin.utils.LogUtils;

import java.util.List;

import rx.Subscriber;
import solid.ren.skinlibrary.entity.SkinItem;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * Created by Administrator on 2016/10/28.
 */
public class SkinPresenter implements SkinContract.Presenter{

    SkinContract.View mSkinView;

    SkinRxModel mRxModel;

    public SkinPresenter(SkinContract.View skinView, SkinRxModel rxModel) {
        mSkinView = skinView;
        mRxModel = rxModel;
    }

    @Override
    public void changeSkin(String skinPath) {
        mSkinView.setRefresh(true);
        mRxModel.changeSkin(skinPath).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mSkinView.setRefresh(false);
                mSkinView.showSuccessMsg("成功更换皮肤");
                //更换皮肤后，通知界面进行刷新
                SkinManager.getInstance().notifySkinUpdate();
            }

            @Override
            public void onError(Throwable e) {
                mSkinView.showErrorMsg(e.getMessage());
                //更换皮肤出错时，换回默认皮肤~
                restoreDefaultSkin();
            }

            @Override
            public void onNext(String s) {

            }
        });
    }

    @Override
    public void restoreDefaultSkin() {
        mSkinView.setRefresh(true);
        mRxModel.restoreDefaultSkin().subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mSkinView.setRefresh(false);
                mSkinView.showSuccessMsg("成功更换默认皮肤");
                //更换皮肤后，通知界面进行刷新
                SkinManager.getInstance().notifySkinUpdate();
            }

            @Override
            public void onError(Throwable e) {
                mSkinView.setRefresh(false);
                mSkinView.showErrorMsg(e.getMessage());
            }

            @Override
            public void onNext(String s) {

            }
        });
    }

    @Override
    public void scanSkinFiles() {
        LogUtils.i(LogUtils.TAG, "Now we begin load skin!");
        mSkinView.setRefresh(true);
        mRxModel.scanSkinFiles().subscribe(new Subscriber<List<SkinItem>>() {
            @Override
            public void onCompleted() {
                mSkinView.setRefresh(false);
                LogUtils.i(LogUtils.TAG, "Now we scan finish!");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<SkinItem> skinItems) {
                mSkinView.updateSkinList(skinItems);
            }
        });
    }

    @Override
    public void deleteSkinFile(String skinFilePath) {
        mSkinView.setRefresh(true);
        mRxModel.deleteSkinFile(skinFilePath).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mSkinView.setRefresh(false);
            }

            @Override
            public void onError(Throwable e) {
                mSkinView.setRefresh(false);
                mSkinView.showErrorMsg(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                mSkinView.showSuccessMsg(s);
            }
        });
    }

    @Override
    public void start() {
        scanSkinFiles();
    }
}
