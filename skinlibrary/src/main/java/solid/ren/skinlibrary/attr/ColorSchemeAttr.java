package solid.ren.skinlibrary.attr;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * Created by ZFL on 2016/10/31.<br>
 *     这个仅用于改变refreshLayout的ColorSchemeResources（即下拉刷新时的那个圈圈的颜色）
 */
public class ColorSchemeAttr extends SkinAttr{
    @Override
    public void apply(View view) {
        if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
            int color = SkinManager.getInstance().getColor(attrValueRefId);
            SwipeRefreshLayout layout = (SwipeRefreshLayout) view;
            layout.setColorSchemeColors(color);
        }
    }
}
