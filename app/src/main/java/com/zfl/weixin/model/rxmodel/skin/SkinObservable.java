package com.zfl.weixin.model.rxmodel.skin;

import java.util.List;

import rx.Observable;
import solid.ren.skinlibrary.entity.SkinItem;

/**
 * Created by ZFL on 2016/10/28.
 */
public interface SkinObservable {
    /**
     * 更换皮肤
     * @return
     */
    Observable<String> changeSkin(String skinPath);

    /**
     * 恢复默认皮肤,用于用户选择第一个或者更换皮肤出错时使用
     * @return
     */
    Observable<String> restoreDefaultSkin();

    /**
     * 扫描指定目录下的皮肤文件
     * @return
     */
    Observable<List<SkinItem>> scanSkinFiles();

    /**
     * 删除皮肤
     * @param skinPath
     * @return
     */
    Observable<String> deleteSkin(String skinPath);
}
