package com.zfl.weixin.global;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/6/17.
 */
public class WeiXinVolley {

    private static RequestQueue mQueue;

    public static void init(Context context){
        mQueue = Volley.newRequestQueue(context);
    }

    public static RequestQueue getQueue(){
        if (mQueue != null) {
            return mQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

}
