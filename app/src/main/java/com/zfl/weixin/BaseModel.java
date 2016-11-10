package com.zfl.weixin;

import com.android.volley.RequestQueue;

/**
 * Created by Administrator on 2016/6/10.<br>
 * BaseModel:ArtCategoryModel和ArticleModel继承于它<br>
 * 后续会添加这些model共同的操作api(如果能想得到的话....)<br>
 * 添加设置Volley请求队列函数(因为继承自这个BaseModel的类全部都有网络请求...)
 */
public interface BaseModel {
    void setRequestQueue(RequestQueue queue);
}
