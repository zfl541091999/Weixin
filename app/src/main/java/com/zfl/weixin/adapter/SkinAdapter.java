package com.zfl.weixin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfl.weixin.R;

import java.util.ArrayList;
import java.util.List;

import solid.ren.skinlibrary.config.SkinConfig;
import solid.ren.skinlibrary.entity.SkinItem;

/**
 * Created by ZFL on 2016/11/1.<br>
 *
 */
public class SkinAdapter extends BaseAdapter{

    List<SkinItem> mList;

    Context mContext;
    //每新建一个holder，都会存储在这个集合里
    List<CommViewHolder> mSkinItemHolders;

    public SkinAdapter(List<SkinItem> list, Context context) {
        mList = list;
        mContext = context;
        mSkinItemHolders = new ArrayList<>();
    }

    @Override
    protected void bindData(CommViewHolder holder, final int position) {
        SkinItem item = mList.get(position);
        ImageView item_image = holder.getView(R.id.skin_item_image);
        TextView item_name = holder.getView(R.id.skin_item_name);
        TextView item_using_label = holder.getView(R.id.skin_item_using_label);
        if (SkinConfig.getSkinId(mContext).equals(item.getSkinId())) {
            //如果皮肤是正在使用....
            item_using_label.setVisibility(View.VISIBLE);
        } else {
            item_using_label.setVisibility(View.INVISIBLE);
        }
        item_image.setBackgroundColor(item.getSkinColor());
        item_name.setText(item.getSkinName());
        //设置删除皮肤文件的组件
        TextView item_delete = holder.getView(R.id.skin_item_delete);
        //给这个按钮设置点击
        item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemWidgetClickListener != null) {
                    mOnItemWidgetClickListener.onWidgetClick(v, position);
                }
            }
        });
        item_delete.setBackground(item.getSkinButtonBg());
        //皮肤文件的删除区域设置为隐藏
        View item_delete_area = holder.getView(R.id.skin_item_delete_area);
        item_delete_area.setVisibility(View.INVISIBLE);
        item_delete_area.clearAnimation();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(mContext).inflate(R.layout.item_skin_list, parent, false);
    }

    @Override
    public CommViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        CommViewHolder holder = new CommViewHolder(realContentView, viewType) {
        };
        mSkinItemHolders.add(holder);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //新增删除相关

    /**
     * 呼出指定position的item的删除菜单
     * @param position
     */
    public void openDeleteArea(int position) {
        for (CommViewHolder holder : mSkinItemHolders) {
            if (position == holder.getAdapterPosition()) {
                View deleteArea = holder.getView(R.id.skin_item_delete_area);
                openOrCloseView(deleteArea, true);
            }
        }
    }

    public void closeDeleteArea(int position) {
        for (CommViewHolder holder : mSkinItemHolders) {
            if (position == holder.getAdapterPosition()) {
                View deleteArea = holder.getView(R.id.skin_item_delete_area);
                openOrCloseView(deleteArea, false);
            }
        }
    }

    /**
     * 当布尔值open为true时，表示view需要打开
     * @param v
     * @param open
     */
    private void openOrCloseView(View v, boolean open) {
        if (open) {
            Animation openAnimation = AnimationUtils.loadAnimation(mContext, R.anim.scale_in);
            v.startAnimation(openAnimation);
            v.setVisibility(View.VISIBLE);
        } else {
            Animation closeAnimation = AnimationUtils.loadAnimation(mContext, R.anim.scale_out);
            v.startAnimation(closeAnimation);
            v.setVisibility(View.INVISIBLE);
        }
    }

}
