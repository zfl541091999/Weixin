package com.zfl.weixin.model.db;

import com.zfl.weixin.entity.ArtCategory;

import java.util.List;

/**
 * Created by Administrator on 2016/6/11.
 */
public interface ArtCatLocalDAO {
    /**
     * 查询本地缓存的文章类别
     * @return
     */
    List<ArtCategory> getArticleCategorys();
    /**
     * 清除本地缓存
     */
    void cleanArticleCategorys();

    /**
     * 将网络得来的文章类别存储到数据库
     */
    void saveArticleCategorys(List<ArtCategory> list);


}
