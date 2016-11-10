package com.zfl.weixin.weixinskin;

import com.zfl.weixin.BasePresenter;
import com.zfl.weixin.BaseView;

import java.util.List;

import solid.ren.skinlibrary.entity.SkinItem;

/**
 * Created by ZFL on 2016/10/28.<br>
 *     mvp模式---给应用换肤
 */
public class SkinContract {

    interface View extends BaseView<Presenter> {

        /**
         * 展示操作成功信息
         */
        void showSuccessMsg(String msg);

        /**
         * 展示操作失败信息
         */
        void showErrorMsg(String err);

        /**
         * 读取指定的皮肤路径，获得list后，在界面显示
         * @param list
         */
        void updateSkinList(List<SkinItem> list);

        /**
         * 当刷新成功后，取消refresh的刷新状态
         * @param isRefresh
         */
        void setRefresh(boolean isRefresh);
    }

    interface Presenter extends BasePresenter {
        /**
         * 更换皮肤
         */
        void changeSkin(String skinPath);
        /**
         * 还原默认皮肤
         */
        void restoreDefaultSkin();

        /**
         * 扫描指定皮肤路径下的皮肤文件
         */
        void scanSkinFiles();

        /**
         * 删除指定的路径的皮肤文件
         */
        void deleteSkinFile(String skinFilePath);
    }
}
