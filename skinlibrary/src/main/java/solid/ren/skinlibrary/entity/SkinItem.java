package solid.ren.skinlibrary.entity;

import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.utils.SkinListUtils;


/**
 * Created by _SOLID
 * Date:2016/4/14
 * Time:9:21
 * <p></p>
 * 用来存储那些有皮肤更改需求的View及其对应的属性<br>
 * edit by ZFL 2016/11/1 <br>
 * 现在的SkinItem里面还可以存储皮肤id，皮肤的名字，皮肤的路径，以满足皮肤文件读取不受路径限制的要求
 */
public class SkinItem {

    //皮肤id,每个皮肤文件都有其唯一id,用于系统识别
    private String skinId;
    //皮肤名字
    private String skinName;
    //皮肤路径
    private String skinPath;
    //皮肤主题颜色,用于在界面显示(通常是colorPrimary)
    private int skinColor;
    //皮肤主题按钮,用于设置删除界面的按钮的背景
    private Drawable skinButtonBg;
    //皮肤是否是默认皮肤的布尔值，默认为false
    private boolean isDefault = false;

    public SkinItem() {
        attrs = new ArrayList<>();
    }

    public SkinItem(String skinId, String skinName, String skinPath, int skinColor, Drawable skinButtonBg) {
        attrs = new ArrayList<>();
        this.skinId = skinId;
        this.skinName = skinName;
        this.skinPath = skinPath;
        this.skinColor = skinColor;
        this.skinButtonBg = skinButtonBg;
    }

    public String getSkinId() {
        return skinId;
    }

    public void setSkinId(String skinId) {
        this.skinId = skinId;
    }

    public String getSkinName() {
        return skinName;
    }

    public void setSkinName(String skinName) {
        this.skinName = skinName;
    }

    public String getSkinPath() {
        return skinPath;
    }

    public void setSkinPath(String skinPath) {
        this.skinPath = skinPath;
    }

    public int getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(int skinColor) {
        this.skinColor = skinColor;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public Drawable getSkinButtonBg() {
        return skinButtonBg;
    }

    public void setSkinButtonBg(Drawable skinButtonBg) {
        this.skinButtonBg = skinButtonBg;
    }

    public View view;

    public List<SkinAttr> attrs;

    public void apply() {
        if (SkinListUtils.isEmpty(attrs)) {
            return;
        }
        for (SkinAttr at : attrs) {
            at.apply(view);
        }
    }

    public void clean() {
        if (SkinListUtils.isEmpty(attrs)) {
            return;
        }
        for (SkinAttr at : attrs) {
            at = null;
        }
    }

    @Override
    public String toString() {
        return "SkinItem [view=" + view.getClass().getSimpleName() + ", attrs=" + attrs + "]";
    }
}
