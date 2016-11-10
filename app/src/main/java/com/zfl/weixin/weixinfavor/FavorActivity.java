package com.zfl.weixin.weixinfavor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zfl.weixin.R;
import com.zfl.weixin.adapter.FavorArtsAdapter;
import com.zfl.weixin.entity.Article;
import com.zfl.weixin.model.rxmodel.favor_artlist.FavorArtListRxModel;
import com.zfl.weixin.view.DefaultItemDecoration;
import com.zfl.weixin.weixinweb.WebActivity;

import java.util.ArrayList;
import java.util.List;

import solid.ren.skinlibrary.attr.base.AttrFactory;
import solid.ren.skinlibrary.base.SkinBaseActivity;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * Created by Administrator on 2016/7/12.
 */
public class FavorActivity extends SkinBaseActivity implements FavorContract.View {

    Context mContext;

    Toolbar mToolbar;

    SwipeMenuRecyclerView mSwipeRecyclerView;

    SwipeRefreshLayout mRefreshLayout;

    TextView mExceptionTextView;

    FavorArtsAdapter mAdapter;

    List<Article> mFavorList;

    FavorContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_favor_arts);
        initData();
        initUI();
    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.favor_arts_toolbar);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.favor_refresh_layout);
        mSwipeRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.favor_arts_view);
        mExceptionTextView = (TextView) findViewById(R.id.favor_arts_exception_view);
        mToolbar.setTitle("收藏文章");
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //设置状态栏跟随主题颜色
        dynamicAddView(mToolbar, AttrFactory.BACKGROUND, R.color.colorPrimary);
        //设置refreshLayout下拉刷新时的圈圈跟随主题颜色
        dynamicAddView(mRefreshLayout, AttrFactory.COLOR_SCHEME, R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getFavorArticles();
            }
        });
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mSwipeRecyclerView.setLayoutManager(lm);
        mSwipeRecyclerView.setAdapter(mAdapter);
        mSwipeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSwipeRecyclerView.addItemDecoration(new DefaultItemDecoration(mContext, R.drawable.default_recycleview_item_decoration));
        //设置侧滑菜单点击
        mSwipeRecyclerView.setSwipeMenuCreator(mMenuCreator);
        mSwipeRecyclerView.setSwipeMenuItemClickListener(mOnMenuItemClickListener);
        //给Adapter设置各种Listener
        mAdapter.setOnItemClickListener(new FavorArtsAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(mContext, WebActivity.class);
                //直接将Article放入intent
                intent.putExtra("article", mFavorList.get(position));
                mContext.startActivity(intent);
            }
        });
    }
    //侧滑菜单生成器
    private SwipeMenuCreator mMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int size = 20;//菜单文字大小
            int width = getResources().getDimensionPixelSize(R.dimen.favor_article_menu_item_width);
            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            //分享
            SwipeMenuItem shareItem = new SwipeMenuItem(mContext)
                    .setBackgroundDrawable(SkinManager.getInstance().getDrawable(R.drawable.comm_theme_button_bg))
                    .setText("分享") // 文字，还可以设置文字颜色，大小等。。
                    .setTextColor(Color.WHITE)
                    .setTextSize(size)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(shareItem);// 添加一个按钮到右侧侧菜单。
            //删除
            SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                    .setBackgroundDrawable(R.drawable.selector_favor_item_menu_delete)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setTextSize(size)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);
        }
    };
    //侧滑菜单点击事件监听
    private OnSwipeMenuItemClickListener mOnMenuItemClickListener = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, @SwipeMenuRecyclerView.DirectionMode int direction) {
            closeable.smoothCloseMenu();
            switch (menuPosition) {
                case 0://分享
                    Toast.makeText(mContext, "你分享了：" + mFavorList.get(adapterPosition).getTitle(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                    intent.putExtra(Intent.EXTRA_TEXT, mFavorList.get(adapterPosition).getSubTitle() + mFavorList.get(adapterPosition).getSourceUrl());
                    intent.putExtra(Intent.EXTRA_TITLE, mFavorList.get(adapterPosition).getTitle());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(intent, "请选择"));
                    break;
                case 1://删除
                    mPresenter.removeFavorArticle(mFavorList.get(adapterPosition));
                    mFavorList.remove(adapterPosition);
                    mAdapter.notifyItemRemoved(adapterPosition);
                    if (adapterPosition != mFavorList.size()) {
                        mAdapter.notifyItemRangeChanged(adapterPosition, mFavorList.size() - adapterPosition);
                    }
                    break;
            }
        }
    };

    private void initData() {
        mFavorList = new ArrayList<>();
        mAdapter = new FavorArtsAdapter(mFavorList,mContext);
        mPresenter = new FavorPresenter(this,new FavorArtListRxModel(mContext));
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void updateList(List<Article> list) {
        mRefreshLayout.setVisibility(View.VISIBLE);
        mFavorList.clear();
        mFavorList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setLoading(boolean loading) {
        mRefreshLayout.setRefreshing(loading);
    }

    @Override
    public void showEmptyView() {
        mRefreshLayout.setVisibility(View.INVISIBLE);
        mExceptionTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorView(String msg) {
        mRefreshLayout.setVisibility(View.INVISIBLE);
        mExceptionTextView.setVisibility(View.VISIBLE);
        mExceptionTextView.setText(msg);
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void regPresenter(FavorContract.Presenter presenter) {
        //do nothing~
    }

    @Override
    public void unregPresenter() {
        //do nothing~
    }
}
