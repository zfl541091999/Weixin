package solid.ren.skinlibrary.statusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;
import android.view.animation.Animation;

/**
 * 设置状态栏的颜色，依赖了SystemBarTintManager类
 */
public class StatusBarUtil {

    private Activity activity;
    private int color;

    public void setColor(int color) {
        this.color = color;
    }

    public StatusBarUtil(Activity activity, int color) {

        this.activity = activity;
        this.color = color;


    }

    private SystemBarTintManager mManager;

    public void setStatusBarbackColor(boolean shouldTranslucent, Animation animation)//记得在布局文件根组件上添加android:fitsSystemWindows="true"
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            mManager = new SystemBarTintManager(activity);
            mManager.setStatusBarTintEnabled(true);
            // tintManager.setStatusBarTintResource(color);
            mManager.setStatusBarTintColor(color, shouldTranslucent, animation);
        }
    }

    public void clearStatusBarTintView () {
        if (mManager != null) {
            mManager.clearStatusBarTintView();
        }
    }

    public void startStatusBarAnimation(Animation animation) {
        if (mManager != null) {
            mManager.startStatusBarAnimation(animation);
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {

        WindowManager.LayoutParams winParams = activity.getWindow().getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        activity.getWindow().setAttributes(winParams);
    }
}
