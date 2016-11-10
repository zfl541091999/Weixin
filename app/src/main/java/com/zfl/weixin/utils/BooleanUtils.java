package com.zfl.weixin.utils;

/**
 * Created by Administrator on 2016/6/10.<br>
 * 存放着一些全局boolean，影响着整个应用
 */
public class BooleanUtils {
    //决定是否使用数据库缓存
    public static boolean CacheEnable = true;
    //决定是否输出log
    public static boolean LogEnable = false;
    //决定是否使用rxjava的model进行数据获取
    public static boolean RxJavaEnable = true;
    //决定是否使用Retrofit获取网络数据
    public static boolean RetrofitEnable = true;
    //决定是否使用Rxjava+Retrofit来获取网络数据
    public static boolean RetrofitRxjavaEnable = false;

}
