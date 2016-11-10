package solid.ren.skinlibrary.loader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListenerV1;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import solid.ren.skinlibrary.config.SkinConfig;
import solid.ren.skinlibrary.listener.ILoaderListener;
import solid.ren.skinlibrary.listener.ISkinLoader;
import solid.ren.skinlibrary.listener.ISkinUpdate;
import solid.ren.skinlibrary.utils.SkinFileUtils;
import solid.ren.skinlibrary.utils.SkinL;
import solid.ren.skinlibrary.utils.TypefaceUtils;


/**
 * Created by _SOLID
 * Date:2016/4/13
 * Time:21:07
 */
public class SkinManager implements ISkinLoader {

    private List<ISkinUpdate> mSkinObservers;
    private static volatile SkinManager mInstance;
    private Context context;
    private Resources mResources;
    private boolean isDefaultSkin = false;//当前的皮肤是否是默认的
    private String skinPackageName;//皮肤的包名
    private String skinPath;//皮肤路径

    private SkinManager() {

    }

    public void init(Context ctx) {
        context = ctx.getApplicationContext();
        TypefaceUtils.CURRENT_TYPEFACE = TypefaceUtils.getTypeface(context);
    }

    public Context getContext() {
        return context;
    }

    public int getColorPrimaryDark() {
        if (mResources != null) {
            int identify = mResources.getIdentifier("colorPrimaryDark", "color", skinPackageName);
            if (!(identify <= 0))
                return mResources.getColor(identify);
        }
        return -1;
    }

    public int getColorPrimary() {
        if (mResources != null) {
            int identify = mResources.getIdentifier("colorPrimary", "color", skinPackageName);
            if (!(identify <= 0))
                return mResources.getColor(identify);
        }
        return -1;
    }

    /**
     * 判断当前使用的皮肤是否来自外部
     *
     * @return
     */
    public boolean isExternalSkin() {
        return !isDefaultSkin && mResources != null;
    }

    /**
     * 得到当前的皮肤路径
     *
     * @return
     */
    public String getSkinPath() {
        return skinPath;
    }

    /**
     * 得到当前皮肤的包名
     *
     * @return
     */
    public String getSkinPackageName() {
        return skinPackageName;
    }

    public Resources getResources() {
        return mResources;
    }

    /**
     * 恢复到默认主题
     */
    public void restoreDefaultTheme() {
        SkinConfig.saveSkinPath(context, SkinConfig.DEFAULT_SKIN);
        SkinConfig.saveSkinId(context, SkinConfig.DEFAULT_SKIN_ID);
        isDefaultSkin = true;
        mResources = context.getResources();
        skinPackageName = context.getPackageName();
    }

    public static SkinManager getInstance() {
        if (mInstance == null) {
            synchronized (SkinManager.class) {
                if (mInstance == null) {
                    mInstance = new SkinManager();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void attach(ISkinUpdate observer) {
        if (mSkinObservers == null) {
            mSkinObservers = new ArrayList<>();
        }
        if (!mSkinObservers.contains(mSkinObservers)) {
            mSkinObservers.add(observer);
        }
    }

    @Override
    public void detach(ISkinUpdate observer) {
        if (mSkinObservers == null) return;
        if (mSkinObservers.contains(observer)) {
            mSkinObservers.remove(observer);
        }
    }

    @Override
    public void notifySkinUpdate() {
        if (mSkinObservers == null) return;
        for (ISkinUpdate observer : mSkinObservers) {
            observer.onThemeUpdate();
        }
    }

    public void loadSkin() {
        String skinPath = SkinConfig.getCustomSkinPath(context);
        if (loadSkin(skinPath) == null) {
            //说明是初始默认皮肤
            restoreDefaultTheme();
        }
    }

    public void loadSkin(ILoaderListener callback) {
        String skin = SkinConfig.getCustomSkinPath(context);
        if (SkinConfig.isDefaultSkin(context)) {
            return;
        }
        loadSkin(skin);
    }

    /**
     * load skin form local
     * <p>
     * eg:theme.skin
     * </p>
     * edit by ZFL 2016/11/1 参数改为皮肤文件路径(不限asset)
     * @param skinPkgPath the path of skin
     */
    public Resources loadSkin(@NonNull String skinPkgPath) {

        try {
            File file = new File(skinPkgPath);
            if (file == null || !file.exists()) {
                return null;
            }
            PackageManager mPm = context.getPackageManager();
            PackageInfo mInfo = mPm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);
            skinPackageName = mInfo.packageName;
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, skinPkgPath);

            Resources superRes = context.getResources();
            Resources skinResource = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());

            skinPath = skinPkgPath;
            isDefaultSkin = false;

            mResources = skinResource;

            if (mResources != null) {
                //皮肤包存在的前提下，换肤成功后，存储皮肤id和皮肤path
                SkinConfig.saveSkinPath(context, skinPkgPath);
                //存储皮肤id
                int skin_id_id = skinResource.getIdentifier("skin_id", "string", mInfo.packageName);
                if (skin_id_id > 0) {
                    String skin_id = mResources.getString(skin_id_id);
                    SkinConfig.saveSkinId(context, skin_id);
                }
            } else {
                isDefaultSkin = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mResources;
    }

    /**
     * 将皮肤文件的id，name读取出来。id和name的字符串会连接起来一起返回,中间用“|”隔开，方便切割
     * @param skinFile
     * @return
     */
    public String loadSkinInfo(File skinFile) {
        String skinPkgPath = skinFile.getPath();
        String skinInfo = "";
        try {
            PackageManager mPm = context.getPackageManager();
            PackageInfo mInfo = mPm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, skinPkgPath);
            Resources superRes = context.getResources();
            Resources skinResource = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
            if (skinResource != null) {
                int skin_id_id = skinResource.getIdentifier("skin_id", "string", mInfo.packageName);
                if (!(skin_id_id <= 0)) {
                    String skin_id = skinResource.getString(skin_id_id);
                    skinInfo = skin_id;
                }
                int skin_name_id = skinResource.getIdentifier("skin_name", "string", mInfo.packageName);
                if (!(skin_name_id <= 0)) {
                    String skin_name = skinResource.getString(skin_name_id);
                    skinInfo = skinInfo + "|" + skin_name;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skinInfo;
    }

    public int loadSkinSchemeColor(File skinFile) {
        String skinPkgPath = skinFile.getPath();
        try {
            PackageManager mPm = context.getPackageManager();
            PackageInfo mInfo = mPm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, skinPkgPath);
            Resources superRes = context.getResources();
            Resources skinResource = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
            if (skinResource != null) {
                int color_id = skinResource.getIdentifier("colorPrimary", "color", mInfo.packageName);
                if (!(color_id <= 0)) {
                    return skinResource.getColor(color_id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Drawable loadSkinSchemeButtonBg(File skinFile) {
        String skinPkgPath = skinFile.getPath();
        try {
            PackageManager mPm = context.getPackageManager();
            PackageInfo mInfo = mPm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, skinPkgPath);
            Resources superRes = context.getResources();
            Resources skinResource = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
            if (skinResource != null) {
                int drawable_id = skinResource.getIdentifier("comm_button_bg", "drawable", mInfo.packageName);
                if (!(drawable_id <= 0)) {
                    return skinResource.getDrawable(drawable_id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * load skin form internet
     * <p>
     * eg:https://raw.githubusercontent.com/burgessjp/ThemeSkinning/master/app/src/main/assets/skin/theme.skin
     * </p>
     *
     * @param skinUrl  the url of skin
     * @param callback load Callback
     */
    public void loadSkinFromUrl(String skinUrl, final ILoaderListener callback) {
        String skinPath = SkinFileUtils.getSkinDir(context);
        final String skinName = skinUrl.substring(skinUrl.lastIndexOf("/") + 1);
        String skinFullName = skinPath + File.separator + skinName;
        File skinFile = new File(skinFullName);
        if (skinFile.exists()) {
            loadSkin(skinName);
            return;
        }

        Uri downloadUri = Uri.parse(skinUrl);
        Uri destinationUri = Uri.parse(skinFullName);

        DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                .setRetryPolicy(new DefaultRetryPolicy())
                .setDestinationURI(destinationUri)
                .setPriority(DownloadRequest.Priority.HIGH);
        callback.onStart();
        downloadRequest.setStatusListener(new DownloadStatusListenerV1() {
            @Override
            public void onDownloadComplete(DownloadRequest downloadRequest) {
                loadSkin(skinName);
            }

            @Override
            public void onDownloadFailed(DownloadRequest downloadRequest, int errorCode, String errorMessage) {
                callback.onFailed(errorMessage);
            }

            @Override
            public void onProgress(DownloadRequest downloadRequest, long totalBytes, long downloadedBytes, int progress) {
                callback.onProgress(progress);
            }
        });

        ThinDownloadManager manager = new ThinDownloadManager();
        manager.add(downloadRequest);


    }

    public void loadFont(String fontName) {
        Typeface tf = TypefaceUtils.createTypeface(context, fontName);
        TextViewRepository.applyFont(tf);
    }

    public int getColor(int resId) {
        int originColor = context.getResources().getColor(resId);
        if (mResources == null || isDefaultSkin) {
            return originColor;
        }

        String resName = context.getResources().getResourceEntryName(resId);

        int trueResId = mResources.getIdentifier(resName, "color", skinPackageName);
        int trueColor = 0;

        try {
            trueColor = mResources.getColor(trueResId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            trueColor = originColor;
        }

        return trueColor;
    }

    public Drawable getDrawable(int resId) {
        Drawable originDrawable = context.getResources().getDrawable(resId);
        if (mResources == null || isDefaultSkin) {
            return originDrawable;
        }
        String resName = context.getResources().getResourceEntryName(resId);

        int trueResId = mResources.getIdentifier(resName, "drawable", skinPackageName);

        Drawable trueDrawable = null;
        try {
            SkinL.i("SkinManager getDrawable", "SDK_INT = " + android.os.Build.VERSION.SDK_INT);
            if (android.os.Build.VERSION.SDK_INT < 22) {
                trueDrawable = mResources.getDrawable(trueResId);
            } else {
                trueDrawable = mResources.getDrawable(trueResId, null);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            trueDrawable = originDrawable;
        }

        return trueDrawable;
    }

    /**
     * 加载指定资源颜色drawable,转化为ColorStateList，保证selector类型的Color也能被转换。
     * 无皮肤包资源返回默认主题颜色
     *
     * @param resId
     * @return
     * @author pinotao
     */
    public ColorStateList getColorStateList(int resId) {
        boolean isExtendSkin = true;
        if (mResources == null || isDefaultSkin) {
            isExtendSkin = false;
        }

        String resName = context.getResources().getResourceEntryName(resId);
        if (isExtendSkin) {
            int trueResId = mResources.getIdentifier(resName, "color", skinPackageName);
            ColorStateList trueColorList = null;
            if (trueResId == 0) { // 如果皮肤包没有复写该资源，但是需要判断是否是ColorStateList
                try {
                    ColorStateList originColorList = context.getResources().getColorStateList(resId);
                    return originColorList;
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                    SkinL.e("resName = " + resName + " NotFoundException : " + e.getMessage());
                }
            } else {
                try {
                    trueColorList = mResources.getColorStateList(trueResId);
                    return trueColorList;
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                    SkinL.e("resName = " + resName + " NotFoundException :" + e.getMessage());
                }
            }
        } else {
            try {
                ColorStateList originColorList = context.getResources().getColorStateList(resId);
                return originColorList;
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
                SkinL.e("resName = " + resName + " NotFoundException :" + e.getMessage());
            }

        }

        int[][] states = new int[1][1];
        return new ColorStateList(states, new int[]{context.getResources().getColor(resId)});
    }
}
