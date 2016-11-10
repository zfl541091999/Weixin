package solid.ren.skinlibrary.attr;

import android.view.View;
import android.widget.ProgressBar;

import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * Created by Administrator on 2016/11/1.
 */
public class ProgressDrawableAttr extends SkinAttr{
    @Override
    public void apply(View view) {
        if (view instanceof ProgressBar) {
            ProgressBar bar = (ProgressBar) view;
            if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
                bar.setProgressDrawable(SkinManager.getInstance().getDrawable(attrValueRefId));
            }
        }
    }
}
