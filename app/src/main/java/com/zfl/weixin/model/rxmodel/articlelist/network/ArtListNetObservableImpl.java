package com.zfl.weixin.model.rxmodel.articlelist.network;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zfl.weixin.entity.Article;
import com.zfl.weixin.entity.ArticleJson;
import com.zfl.weixin.global.WeiXinVolley;
import com.zfl.weixin.model.retrofit.RetrofitApiService;
import com.zfl.weixin.utils.ApiUtils;
import com.zfl.weixin.utils.BooleanUtils;
import com.zfl.weixin.utils.LogUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/6/18.
 */
public class ArtListNetObservableImpl extends ArtListNetObservable {

    private static final String TAG = "ArtListNetObservableImpl";

    //增加retrofit数据源
    //retrofit依赖的OkHttpClient
    OkHttpClient mClient;
    Retrofit mRetrofit;
    RetrofitApiService mService;

    public ArtListNetObservableImpl() {
        //retrofit 数据初始化
        mClient = new OkHttpClient();
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mClient)
                .baseUrl(ApiUtils.Article_api)
                .build();
        mService = mRetrofit.create(RetrofitApiService.class);
    }

    @Override
    public Observable<List<Article>> loadArticleList(final String cid, final int page) {
        if (BooleanUtils.RetrofitEnable) {
            //是以call的形式获取数据还是observable的形式获取数据，
            // 由RetrofitRxjavaEnable这布尔值来决定
            if (BooleanUtils.RetrofitRxjavaEnable) {
                return mService.loadArticleList(cid, page).map(new Func1<ArticleJson, List<Article>>() {
                    @Override
                    public List<Article> call(ArticleJson articleJson) {
                        return articleJson.getResult().getList();
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

            } else {
                return Observable.create(new Observable.OnSubscribe<List<Article>>() {

                    @Override
                    public void call(Subscriber<? super List<Article>> subscriber) {
                        Call<ArticleJson> call = mService.loadArticleList_norm(cid, page);
                        try {
                            retrofit2.Response<ArticleJson> response = call.execute();
                            ArticleJson articleJson = response.body();
                            if ("success".equals(articleJson.getMsg())) {
                                subscriber.onNext(articleJson.getResult().getList());
                                subscriber.onCompleted();
                            } else {
                                subscriber.onError(new Throwable("net data error"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            subscriber.onError(e);
                        }

                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        } else {
            return Observable.create(new Observable.OnSubscribe<List<Article>>() {
                @Override
                public void call(final Subscriber<? super List<Article>> subscriber) {
                    LogUtils.i(TAG, "Now we load from Net!");
                    String url =
                            ApiUtils.Article_api + "search?key=" +
                                    ApiUtils.AppKey + "&cid=" +
                                    cid + "&page=" +
                                    page;
                    if (android.os.Build.VERSION.SDK_INT < 21) {
                        try {
                            url = new String(url.getBytes(), "ISO-8859-1");
                        } catch (UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    RequestFuture<JSONObject> future = RequestFuture.newFuture();
                    JsonObjectRequest request =
                            new JsonObjectRequest(Request.Method.GET, url, future, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    subscriber.onError(new Throwable("volley error!"));
                                }
                            });
                    WeiXinVolley.getQueue().add(request);
                    try {
                        JSONObject object = future.get();
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArticleJson>() {
                        }.getType();
                        ArticleJson articleJson = gson.fromJson(object.toString(), type);
                        if ("success".equals(articleJson.getMsg())) {
                            subscriber.onNext(articleJson.getResult().getList());
                            subscriber.onCompleted();
                        } else {
                            //出错了,让subscriber获取错误？
                            subscriber.onError(new Throwable(articleJson.getMsg()));
                        }
                    } catch (Exception e) {
                        Observable.error(e);
                    }
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }


    }
}
