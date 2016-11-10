package com.zfl.weixin.model.norm_model.articlelist.network;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zfl.weixin.entity.ArticleJson;
import com.zfl.weixin.utils.ApiUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/6/15.
 */
public class ArtListNetModelImpl extends ArtListNetModel {

    RequestQueue mQueue;

    @Override
    public void setRequestQueue(RequestQueue queue) {
        mQueue = queue;
    }

    @Override
    public void loadArticleList(final getArtListCallBack callBack, String cid) {
        loadArticles(callBack,cid,0);
    }

    @Override
    public void loadMoreArticleList(final getArtListCallBack callBack, String cid, int page) {
        loadArticles(callBack,cid,page);
    }

    private void loadArticles(final getArtListCallBack callBack, String cid, int page){
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
        JsonObjectRequest jRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject arg0) {
                        // TODO Auto-generated method stub
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArticleJson>() {
                        }.getType();
                        ArticleJson articleJson = gson.fromJson(arg0.toString(), type);
                        if ("success".equals(articleJson.getMsg())) {
                            callBack.onSuccess(articleJson.getResult().getList());
                        } else {
                            callBack.onFail(articleJson.getMsg());
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        // TODO Auto-generated method stub
                        //下面可以给出各种各样的错误详细处理
                        if (arg0 instanceof AuthFailureError) {
                            callBack.onFail("AuthFailure");
                        } else if (arg0 instanceof NetworkError) {
                            callBack.onFail("NetworkError");
                        } else if (arg0 instanceof NoConnectionError) {
                            callBack.onFail("NoConnectionError");
                        } else if (arg0 instanceof ParseError) {
                            callBack.onFail("ParseError");
                        } else if (arg0 instanceof ServerError) {
                            callBack.onFail("ServerError");
                        } else if (arg0 instanceof TimeoutError) {
                            callBack.onFail("TimeoutError");
                        } else {
                            callBack.onFail("UnkownError");
                        }
                    }
                });
        //设置超时时限和重试次数
        jRequest.setRetryPolicy(new DefaultRetryPolicy(5000,1,1.0f));
        mQueue.add(jRequest);
    }

}
