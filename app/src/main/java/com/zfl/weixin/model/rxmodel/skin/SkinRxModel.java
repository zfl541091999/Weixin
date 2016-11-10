package com.zfl.weixin.model.rxmodel.skin;

import java.util.List;

import rx.Observable;
import solid.ren.skinlibrary.entity.SkinItem;

/**
 * Created by Administrator on 2016/10/28.
 */
public class SkinRxModel {
    //目前只做了本地皮肤文件的获取
    SkinObservable mSkinObservable;

    public SkinRxModel() {
        mSkinObservable = new SkinObservableImpl();
    }

    /**
     * 更换皮肤
     * @param skinPath
     * @return
     */
    public Observable<String> changeSkin(String skinPath) {
        return mSkinObservable.changeSkin(skinPath);
    }

    /**
     * 还原默认皮肤
     * @return
     */
    public Observable<String> restoreDefaultSkin() {
        return mSkinObservable.restoreDefaultSkin();
    }

    public Observable<List<SkinItem>> scanSkinFiles() {
        return mSkinObservable.scanSkinFiles();
    }

    public Observable<String> deleteSkinFile(String skinFilePath) {
        return mSkinObservable.deleteSkin(skinFilePath);
    }

}
