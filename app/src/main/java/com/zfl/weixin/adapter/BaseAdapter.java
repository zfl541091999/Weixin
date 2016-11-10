package com.zfl.weixin.adapter;

import android.view.View;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

/**
 * Created by Administrator on 2016/10/27.<br>
 *     适配器的基类,继承这个适配器的可以添加侧滑菜单
 */
public abstract class BaseAdapter extends SwipeMenuAdapter<CommViewHolder> {

    @Override
    public void onBindViewHolder(CommViewHolder holder, int position) {
        holder.setOnItemClickListener(mOnItemClickListener);
        holder.setOnItemLongClickListener(mOnItemLongClickListener);
        bindData(holder, position);
    }

    /**
     * 继承这个抽象类的adapter需要实现这个方法
     */
    protected abstract void bindData(CommViewHolder holder,int position);

    /*Item点击相关*/
    /**
     * 处理点击事件的回调接口
     */
    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        mOnItemClickListener = clickListener;
    }

    /*item长点击相关*/
    public interface OnItemLongClickListener {
        void onLongClick(View v, int position);
    }

    OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener clickListener) {
        mOnItemLongClickListener = clickListener;
    }

    /*item中的子组件点击事件相关*/
    public interface OnItemWidgetClickListener {
        /**
         * 参数view即这个item中被点击的widget,可以使用view.getId()确定被点击的widget<br>
         * position代表这个item所处的位置
         * @param view
         * @param position
         */
        void onWidgetClick(View view, int position);
    }

    OnItemWidgetClickListener mOnItemWidgetClickListener;

    public void setOnItemWidgetClickListener(OnItemWidgetClickListener clickListener) {
        mOnItemWidgetClickListener = clickListener;
    }
}
