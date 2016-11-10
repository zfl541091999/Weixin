package com.zfl.weixin.weixin;

import com.zfl.weixin.BasePresenter;
import com.zfl.weixin.BaseView;
import com.zfl.weixin.entity.ArtCategory;

import java.util.List;

/**
 * Created by Administrator on 2016/6/8.
 */
public class ArtCategoryContract {

    interface View extends BaseView<Presenter> {
        /**
         * 展示精选文章类别！
         */
        void updateArtCat(List<ArtCategory> list);

        void showLoading();

        void hideLoading();
        /**
         * 加载错误时，显示此View
         * @param reason
         */
        void showErrorArea(String reason);

        void hideErrorArea();
    }

    interface Presenter extends BasePresenter {
        /**
         * 获取微信精选文章类别
         * @return
         */
        void loadArtCategorys();

    }


}
