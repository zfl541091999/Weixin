package com.zfl.weixin.global;

import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import solid.ren.skinlibrary.base.SkinBaseApplication;
import solid.ren.skinlibrary.config.SkinConfig;

/**
 * Created by Administrator on 2016/6/10.<br>
 *     继承自SkinBaseApplication,初始化皮肤管理器
 */
public class WeiXinApp extends SkinBaseApplication{
    //内存泄漏监视者
    RefWatcher mWatcher;

    public static RefWatcher getRefWatcher(Context context){
        WeiXinApp app= (WeiXinApp) context.getApplicationContext();
        return app.mWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        WeiXinVolley.init(this);
        mWatcher = LeakCanary.install(this);
        //设置可以连状态栏颜色也可以改变
        SkinConfig.setCanChangeStatusColor(true);
    }
}
