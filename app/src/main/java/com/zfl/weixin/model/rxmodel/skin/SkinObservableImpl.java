package com.zfl.weixin.model.rxmodel.skin;

import android.graphics.drawable.Drawable;

import com.zfl.weixin.R;
import com.zfl.weixin.utils.ConstansUtils;
import com.zfl.weixin.utils.LogUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import solid.ren.skinlibrary.entity.SkinItem;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * Created by ZFL on 2016/10/28.<br>
 */
public class SkinObservableImpl implements SkinObservable {
    @Override
    public Observable<String> changeSkin(final String skinPath) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (SkinManager.getInstance().loadSkin(skinPath) != null) {
                    //皮肤加载成功
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Throwable("加载皮肤资源失败！"));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<String> restoreDefaultSkin() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                SkinManager.getInstance().restoreDefaultTheme();
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<SkinItem>> scanSkinFiles() {
        return Observable.create(new Observable.OnSubscribe<List<SkinItem>>() {
            @Override
            public void call(Subscriber<? super List<SkinItem>> subscriber) {
                //检查zfl_data文件夹是否存在,不存在则创建它(其实完全不需要这一步的)
                File zfl_data_file = new File(ConstansUtils.ZFL_DATA_DIR);
                if (!zfl_data_file.exists()) {
                    zfl_data_file.mkdir();
                }
                //检查指定的皮肤文件夹是否存在,不存在则创建它
                File skin_files_dir = new File(ConstansUtils.SKIN_DIR);
                if (!skin_files_dir.exists()) {
                    skin_files_dir.mkdir();
                }
                //获取指定的皮肤文件夹下的所有皮肤文件
                File[] skinFiles = skin_files_dir.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        String fileName = pathname.getPath();
                        if (fileName.endsWith(".skin")) return true;
                        return false;
                    }
                });
                //使用SkinManager获取皮肤文件的id，名字，以及颜色
                List<SkinItem> list = new ArrayList<>();
                for (File skinFile : skinFiles) {
                    String skinInfo[] = SkinManager.getInstance().loadSkinInfo(skinFile).split("\\|");
                    int schemeColor = SkinManager.getInstance().loadSkinSchemeColor(skinFile);
                    Drawable schemeButtonBg = null;
                    schemeButtonBg = SkinManager.getInstance().loadSkinSchemeButtonBg(skinFile);
                    if (schemeButtonBg == null) {
                        schemeButtonBg = SkinManager.getInstance().getDrawable(R.drawable.comm_button_bg);
                    }
                    LogUtils.i(LogUtils.TAG, "skin_id:" + skinInfo[0] + "  skin_name:" + skinInfo[1]
                            + " skin_color:" + schemeColor);
                    SkinItem skinItem = new SkinItem(skinInfo[0], skinInfo[1], skinFile.getPath(), schemeColor, schemeButtonBg);
                    list.add(skinItem);
                }
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<String> deleteSkin(final String skinPath) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                File file = new File(skinPath);
                if (!file.exists()) {
                    subscriber.onError(new Throwable("皮肤文件不存在"));
                    return;
                }
                if (file.delete()) {
                    subscriber.onNext("皮肤文件删除成功");
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Throwable("皮肤文件删除失败"));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
