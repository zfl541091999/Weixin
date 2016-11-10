package com.zfl.weixin.weixinskin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zfl.weixin.R;
import com.zfl.weixin.adapter.BaseAdapter;
import com.zfl.weixin.adapter.SkinAdapter;
import com.zfl.weixin.model.rxmodel.skin.SkinRxModel;
import com.zfl.weixin.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import solid.ren.skinlibrary.attr.base.AttrFactory;
import solid.ren.skinlibrary.base.SkinBaseActivity;
import solid.ren.skinlibrary.config.SkinConfig;
import solid.ren.skinlibrary.entity.SkinItem;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * Created by ZFL on 2016/10/28.<br>
 *     更换皮肤
 */
public class SkinActivity extends SkinBaseActivity implements SkinContract.View{

    Context mContext;

    Toolbar mToolbar;

    List<SkinItem> mSkinList;

    SkinAdapter mAdapter;

    SwipeMenuRecyclerView mRecyclerView;

    SwipeRefreshLayout mRefreshLayout;

    SkinContract.Presenter mPresenter;
    //呼出了删除菜单的item的position
    int mDeletingPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_skin);
        initData();
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.skin_toolbar);
        mToolbar.setTitle("更换皮肤");
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置状态栏跟随主题颜色
        dynamicAddView(mToolbar, AttrFactory.BACKGROUND, R.color.colorPrimary);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.skin_refresh_layout);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.scanSkinFiles();
            }
        });
        //设置refreshLayout下拉刷新时的圈圈跟随主题颜色
        dynamicAddView(mRefreshLayout, AttrFactory.COLOR_SCHEME, R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.scanSkinFiles();
            }
        });
        mRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.skins_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        //给adapter设置点击事件
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                SkinItem item = mSkinList.get(position);
                if (SkinConfig.getSkinId(mContext).equals(item.getSkinId())) {
                    Toast.makeText(mContext, "你已在使用该皮肤！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (item.isDefault()) {
                    mPresenter.restoreDefaultSkin();
                } else {
                    mPresenter.changeSkin(item.getSkinPath());
                }
            }
        });
        //给adapter设置长按事件
        mAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(View v, int position) {
                LogUtils.i(LogUtils.TAG, "We open menu!");
                if (position == mDeletingPosition) {
                    //说明用户在长按已经呼出删除菜单的item，应该提醒用户
                    Toast.makeText(mContext, "删除菜单已经弹出", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mSkinList.get(position).getSkinId().equals(SkinConfig.DEFAULT_SKIN_ID)) {
                    //说明用户长按的是默认皮肤item，默认皮肤不能被删除，应提醒用户
                    Toast.makeText(mContext, "不能删除默认皮肤", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mDeletingPosition > 0) {
                    mAdapter.closeDeleteArea(mDeletingPosition);
                }
                mDeletingPosition = position;
                mAdapter.openDeleteArea(mDeletingPosition);
            }
        });
        //给adapter设置"删除"按钮点击事件
        mAdapter.setOnItemWidgetClickListener(new BaseAdapter.OnItemWidgetClickListener() {
            @Override
            public void onWidgetClick(View view, int position) {
                SkinItem item = mSkinList.remove(position);
                mAdapter.notifyItemRemoved(position);
                if (position != mSkinList.size()) {
                    mAdapter.notifyItemRangeChanged(position, mSkinList.size() - position);
                }
                if (item.getSkinId().equals(SkinConfig.getSkinId(mContext))) {
                    //代表删除的皮肤正在被使用，删除后使用默认皮肤
                    SkinManager.getInstance().restoreDefaultTheme();
                }
                mPresenter.deleteSkinFile(item.getSkinPath());
                //删除完皮肤文件后，deletingPosiiton变回-1
                mDeletingPosition = -1;
            }
        });
    }

    private void initData() {
        mPresenter = new SkinPresenter(this, new SkinRxModel());
        mSkinList = new ArrayList<>();
        //添加一个默认的玛瑙绿
        SkinItem item = new SkinItem(SkinConfig.DEFAULT_SKIN_ID,
                "玛瑙绿(默认)",
                null,
                mContext.getResources().getColor(R.color.colorPrimary),
                mContext.getResources().getDrawable(R.drawable.comm_button_bg));
        //这是默认的玛瑙绿
        item.setDefault(true);
        mSkinList.add(item);
        mAdapter = new SkinAdapter(mSkinList, mContext);
    }

    @Override
    public void onBackPressed() {
        if (mDeletingPosition >= 0) {
            //说明有某个item呼出了删除菜单,按回退键会先将删除菜单取消
            mAdapter.closeDeleteArea(mDeletingPosition);
            mDeletingPosition = -1;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showSuccessMsg(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        mAdapter.notifyDataSetChanged();
        //界面刷新后，所有删除菜单都会被隐藏，所以deletingPosition变回-1
        mDeletingPosition = -1;
    }

    @Override
    public void showErrorMsg(String err) {
        Toast.makeText(mContext, err, Toast.LENGTH_SHORT).show();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateSkinList(List<SkinItem> list) {
        mSkinList.clear();
        //添加一个默认的玛瑙绿
        SkinItem item = new SkinItem("00",
                "玛瑙绿(默认)",
                null,
                mContext.getResources().getColor(R.color.colorPrimary),
                mContext.getResources().getDrawable(R.drawable.comm_button_bg));
        //这是默认的玛瑙绿
        item.setDefault(true);
        mSkinList.add(item);
        mSkinList.addAll(list);
        mAdapter.notifyDataSetChanged();
        mDeletingPosition = -1;
    }

    @Override
    public void setRefresh(boolean isRefresh) {
        mRefreshLayout.setRefreshing(isRefresh);
    }

    @Override
    public void regPresenter(SkinContract.Presenter presenter) {
        //do nothing~
    }

    @Override
    public void unregPresenter() {
        //do nothing~
    }
}
