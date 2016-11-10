package solid.ren.skinlibrary.attr.base;

import java.util.HashMap;

import solid.ren.skinlibrary.attr.BackgroundAttr;
import solid.ren.skinlibrary.attr.ColorSchemeAttr;
import solid.ren.skinlibrary.attr.NavigationViewAttr;
import solid.ren.skinlibrary.attr.ProgressDrawableAttr;
import solid.ren.skinlibrary.attr.TextColorAttr;
import solid.ren.skinlibrary.utils.SkinL;

/**
 * Created by _SOLID
 * Date:2016/4/14
 * Time:9:47
 */
public class AttrFactory {

    private static String TAG = "AttrFactory";

    public static final String BACKGROUND = "background";
    public static final String TEXT_COLOR = "textColor";
    public static final String TAB_INDICATOR_COLOR = "tabIndicatorColor";
    public static final String CONTENT_SCRIM_COLOR = "contentScrimColor";
    public static final String BACKGROUND_TINT = "backgroundTint";
    public static final String NAVIGATION_VIEW_MENU = "navigationViewMenu";
    public static final String COLOR_SCHEME = "colorScheme";//这个是refreshlayout下拉刷新时的那个圈圈的颜色
    public static final String PROGRESSDRAWABLE = "progressDrawable";//进度条的颜色，跟随主题颜色colorPrimaryDark

    public static HashMap<String, SkinAttr> mSupportAttr = new HashMap<>();

    static {
        mSupportAttr.put(BACKGROUND, new BackgroundAttr());
        mSupportAttr.put(TEXT_COLOR, new TextColorAttr());
        //放入backgroundTint，会发生什么事情呢？
        mSupportAttr.put(BACKGROUND_TINT, new BackgroundAttr());
        //放入colorScheme
        mSupportAttr.put(COLOR_SCHEME, new ColorSchemeAttr());
        //放入navigationViewMenu
        mSupportAttr.put(NAVIGATION_VIEW_MENU, new NavigationViewAttr());
        //放入progressDrawable
        mSupportAttr.put(PROGRESSDRAWABLE, new ProgressDrawableAttr());
    }


    public static SkinAttr get(String attrName, int attrValueRefId, String attrValueRefName, String typeName) {
        SkinL.i(TAG, "attrName:" + attrName);
        SkinAttr mSkinAttr = mSupportAttr.get(attrName).clone();
        if (mSkinAttr == null) return null;
        mSkinAttr.attrName = attrName;
        mSkinAttr.attrValueRefId = attrValueRefId;
        mSkinAttr.attrValueRefName = attrValueRefName;
        mSkinAttr.attrValueTypeName = typeName;
        return mSkinAttr;
    }

    /**
     * 检测属性是否被支持
     *
     * @param attrName
     * @return true : supported <br>
     * false: not supported
     */
    public static boolean isSupportedAttr(String attrName) {
        return mSupportAttr.containsKey(attrName);
    }

    /**
     * 增加对换肤属性的支持
     *
     * @param attrName 属性名
     * @param skinAttr 自定义的属性
     */
    public static void addSupportAttr(String attrName, SkinAttr skinAttr) {
        mSupportAttr.put(attrName, skinAttr);
    }
}
