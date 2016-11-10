package com.zfl.weixin.model.retrofit;

import com.zfl.weixin.entity.ArtCategoryJson;
import com.zfl.weixin.entity.ArticleJson;
import com.zfl.weixin.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/6/19.<br>
 * 创造出Retrofit的网络请求接口。<br>
 * 包括ArtCategory和ArticleList的网络请求
 */
public interface RetrofitApiService {
    /**
     * 获取文章分类,以Rxjava的形式返回
     *
     * @return
     */
    @GET("query?key=" + ApiUtils.AppKey)
    Observable<ArtCategoryJson> loadArtCategorys();

    /**
     * 获取文章分类，以Call的形式返回
     *
     * @return
     */
    @GET("query?key=" + ApiUtils.AppKey)
    Call<ArtCategoryJson> loadArtCategorys_norm();

    /**
     * 获取文章列表，以Rxjava的形式返回
     *
     * @return
     */
    @GET("search?key=" + ApiUtils.AppKey)
    Observable<ArticleJson> loadArticleList(@Query("cid") String cid,
                                            @Query("page") int page);

    /**
     * 获取文章列表，以Call的形式返回
     *
     * @param cid
     * @param page
     * @return
     */
    @GET("search?key=" + ApiUtils.AppKey)
    Call<ArticleJson> loadArticleList_norm(@Query("cid") String cid,
                                           @Query("page") int page);
}
