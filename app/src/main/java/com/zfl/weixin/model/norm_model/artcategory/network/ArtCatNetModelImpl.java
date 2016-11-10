package com.zfl.weixin.model.norm_model.artcategory.network;

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
import com.zfl.weixin.entity.ArtCategoryJson;
import com.zfl.weixin.utils.ApiUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/6/15.
 */
public class ArtCatNetModelImpl extends ArtCatNetModel {

    RequestQueue mQueue;

    @Override
    public void loadArtCategorys(final getArtCatCallback callback) {
        String url = ApiUtils.ArtCategory_api;
        if (android.os.Build.VERSION.SDK_INT < 21) {
            try {
                url = new String(url.getBytes(),"ISO-8859-1");
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
                        Type type = new TypeToken<ArtCategoryJson>(){}.getType();
                        ArtCategoryJson artCatJson = gson.fromJson(arg0.toString(),type);
                        if("success".equals(artCatJson.getMsg())) {
                            callback.onSuccess(artCatJson.getResult());
                        } else {
                            callback.onFail(artCatJson.getMsg());
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        // TODO Auto-generated method stub
                        if (arg0 instanceof AuthFailureError) {
                            callback.onFail("AuthFailure");
                        } else if (arg0 instanceof NetworkError) {
                            callback.onFail("NetworkError");
                        } else if (arg0 instanceof NoConnectionError) {
                            callback.onFail("NoConnectionError");
                        } else if (arg0 instanceof ParseError) {
                            callback.onFail("ParseError");
                        } else if (arg0 instanceof ServerError) {
                            callback.onFail("ServerError");
                        } else if (arg0 instanceof TimeoutError) {
                            callback.onFail("TimeoutError");
                        } else {
                            callback.onFail("UnkownError");
                        }
                    }
                });
        //设置超时时限和重试次数
        jRequest.setRetryPolicy(new DefaultRetryPolicy(5000,1,1.0f));
        mQueue.add(jRequest);
    }

    @Override
    public void setRequestQueue(RequestQueue queue) {
        mQueue = queue;
    }

}
