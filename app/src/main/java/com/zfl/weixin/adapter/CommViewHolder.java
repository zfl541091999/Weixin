package com.zfl.weixin.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by Administrator on 2016/5/24.
 */
public abstract class CommViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    private SparseArray<View> mViews;

    View mItemView;
    //本来父类ViewHolder中有mItemViewType的,但是不允许引用,所以自己新增参数mItemViewType
    int mItemViewType;
    //在ViewHolder那里设置OnItemClickListener？
    BaseAdapter.OnItemClickListener mOnItemClickListener;
    //添加长按事件？
    BaseAdapter.OnItemLongClickListener mOnItemLongClickListener;

    public CommViewHolder(View itemView, int viewType) {
        super(itemView);
        mItemView = itemView;
        mItemView.setOnClickListener(this);
        mItemView.setOnLongClickListener(this);
        mViews = new SparseArray<>();
        mItemViewType = viewType;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mItemView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }

    /**
     * 不能取名为getItemViewType,会提示复写ViewHolder函数
     * @return
     */
    public int getViewType() {
        return mItemViewType;
    }

    /**
     * 获取整个ItemView
     * @return
     */
    public View getItemView(){
        return itemView;
    }

    public void setOnItemClickListener(BaseAdapter.OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(BaseAdapter.OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemLongClickListener != null) {
            mOnItemLongClickListener.onLongClick(v, getAdapterPosition());
        }
        return true;
    }
}
