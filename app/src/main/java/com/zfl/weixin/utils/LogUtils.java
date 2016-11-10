package com.zfl.weixin.utils;

import android.util.Log;

import com.zfl.weixin.BuildConfig;

/**
 * Created by Administrator on 2016/6/11.<br>
 * 输出Log
 */
public class LogUtils {

    public static final String TAG = "ZFL_WeiXin";

    public static void i(String TAG, String Text) {
        if (BuildConfig.LOG_DEBUG)
            Log.i(TAG, Text);
    }

    public static void e(String TAG, String Text) {
        if (BuildConfig.LOG_DEBUG)
            Log.e(TAG, Text);
    }

    public static void w(String TAG, String Text) {
        if (BuildConfig.LOG_DEBUG)
            Log.w(TAG, Text);
    }

    public static void d(String TAG, String Text) {
        if (BuildConfig.LOG_DEBUG)
            Log.d(TAG, Text);
    }
}
