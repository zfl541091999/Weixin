package com.zfl.weixin.model.rxmodel.artcategory.network;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zfl.weixin.entity.ArtCategory;
import com.zfl.weixin.entity.ArtCategoryJson;
import com.zfl.weixin.global.WeiXinVolley;
import com.zfl.weixin.model.retrofit.RetrofitApiService;
import com.zfl.weixin.utils.ApiUtils;
import com.zfl.weixin.utils.BooleanUtils;
import com.zfl.weixin.utils.LogUtils;

import org.json.JSONObject;

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
 * Created by Administrator on 2016/6/17.
 */
public class ArtCatNetObservableImpl extends ArtCatNetObservable {

    private static final String TAG = "ArtCatNetObservableImpl";

    //增加retrofit数据源
    //retrofit依赖的OkHttpClient
    OkHttpClient mClient;
    Retrofit mRetrofit;
    RetrofitApiService mService;

    public ArtCatNetObservableImpl() {
        //retrofit 数据初始化
        mClient = new OkHttpClient();
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mClient)
                .baseUrl(ApiUtils.ArtCategory_api)
                .build();
        mService = mRetrofit.create(RetrofitApiService.class);
    }

    @Override
    public Observable<List<ArtCategory>> getArtCategorys() {
        if (BooleanUtils.RetrofitEnable) {
            //是以call的形式获取数据还是observable的形式获取数据，
            // 由RetrofitRxjavaEnable这布尔值来决定
            if(BooleanUtils.RetrofitRxjavaEnable){
                return mService.loadArtCategorys().map(new Func1<ArtCategoryJson, List<ArtCategory>>() {
                    @Override
                    public List<ArtCategory> call(ArtCategoryJson artCategoryJson) {
                        return artCategoryJson.getResult();
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            } else {
                return Observable.create(new Observable.OnSubscribe<List<ArtCategory>>() {
                    @Override
                    public void call(Subscriber<? super List<ArtCategory>> subscriber) {
                        Call<ArtCategoryJson> call = mService.loadArtCategorys_norm();
                        try {
                            retrofit2.Response<ArtCategoryJson> response = call.execute();
                            ArtCategoryJson artCategoryJson = response.body();
                            if("success".equals(artCategoryJson.getMsg())) {
                                List<ArtCategory> list = artCategoryJson.getResult();
                                subscriber.onNext(list);
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
            //以volley的形式去获取
            return Observable.create(new Observable.OnSubscribe<List<ArtCategory>>() {
                @Override
                public void call(final Subscriber<? super List<ArtCategory>> subscriber) {
                    LogUtils.i(TAG, "Now we load from Net!");
                    String url = ApiUtils.ArtCategory_api + "query?key=" + ApiUtils.AppKey;
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
                        Type type = new TypeToken<ArtCategoryJson>() {
                        }.getType();
                        ArtCategoryJson artCatJson = gson.fromJson(object.toString(), type);
                        if ("success".equals(artCatJson.getMsg())) {
                            subscriber.onNext(artCatJson.getResult());
                            subscriber.onCompleted();
                        } else {
                            //出错了,让subscriber获取错误？
                            subscriber.onError(new Throwable(artCatJson.getMsg()));
                        }
                    } catch (Exception e) {
                        Observable.error(e);
                    }
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    }
}
