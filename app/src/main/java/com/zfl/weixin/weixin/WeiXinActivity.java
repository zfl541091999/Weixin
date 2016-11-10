package com.zfl.weixin.weixin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.squareup.leakcanary.RefWatcher;
import com.zfl.weixin.R;
import com.zfl.weixin.entity.ArtCategory;
import com.zfl.weixin.global.WeiXinApp;
import com.zfl.weixin.global.WeiXinVolley;
import com.zfl.weixin.model.norm_model.artcategory.ArtCategoryModel;
import com.zfl.weixin.model.norm_model.articlelist.ArticleListModel;
import com.zfl.weixin.model.rxmodel.artcategory.ArtCategoryRxModel;
import com.zfl.weixin.utils.LogUtils;
import com.zfl.weixin.weixin.articlelist.ArticleListFragment;
import com.zfl.weixin.weixinabout.AboutActivity;
import com.zfl.weixin.weixinfavor.FavorActivity;
import com.zfl.weixin.weixinskin.SkinActivity;

import java.util.ArrayList;
import java.util.List;

import solid.ren.skinlibrary.attr.base.AttrFactory;
import solid.ren.skinlibrary.base.SkinBaseActivity;

public class WeiXinActivity extends SkinBaseActivity implements ArtCategoryContract.View {

    Context mContext;

    WeiXinApp mWeiXinApp;
    //菜单视图
    DrawerLayout mDrawerLayout;
    //此布尔值判断侧滑菜单是否在打开
    boolean mIsDrawerLayoutOpen = false;
    //菜单开关
    ActionBarDrawerToggle mDrawerToggle;
    //菜单本体
    NavigationView mNavigationView;
    //记录之前的选中项来清除Checked状态
    MenuItem mPreMenuItem;

    Toolbar mToolbar;

    ArtCategoryContract.Presenter mCategoryPresenter;

    TabLayout mArtCatsTab;
    //存放着精选文章List的Fragment的ViewPager
    ViewPager mArtViewPager;
    //存放着ArtlistFragment的集合
    List<ArticleListFragment> mArticleListFragments;
    //pagerAdapter
    FragmentStatePagerAdapter mPagerAdapter;
    //加载时显示的进度条
    CircularProgressView mLoadArtCatsProgress;
    //加载时遇到错误,此区域会显示
    LinearLayout mWrongArea;
    //错误原因
    TextView mWrongReasonText;
    //"重试"按钮
    Button mTryAgainBtn;

    List<ArtCategory> mArtCatList;
    //重复给Tablayout设置viewpager会出问题...
    boolean isLoaded = false;

    //监视内存泄漏
    RefWatcher mRefWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mWeiXinApp = (WeiXinApp) getApplication();
        mRefWatcher = WeiXinApp.getRefWatcher(mContext);
        mRefWatcher.watch(this);
        setContentView(R.layout.activity_wei_xin);
        initModel();
        initData();
        initUI();
    }

    private void initData() {
        mArtCatList = new ArrayList<>();
        mArticleListFragments = new ArrayList<>();
        mPagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mArticleListFragments.get(position);
            }

            @Override
            public int getCount() {
                return mArticleListFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mArtCatList.get(position).getName();
            }
        };
        //初始化ArtCatPresenter
        mCategoryPresenter =
                new ArtCategoryPresenter(
                        ArtCategoryModel.getInstance(mContext.getApplicationContext()),
                        new ArtCategoryRxModel(mContext.getApplicationContext()),
                        this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isLoaded) mCategoryPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清理数据？
        mArtCatsTab.removeAllTabs();
        mArtViewPager.removeAllViews();
        mArtCatList.clear();
        mArticleListFragments.clear();
    }

    private void initUI() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.weixin_drawer);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                LogUtils.e(LogUtils.TAG, "open!");
                mIsDrawerLayoutOpen = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mIsDrawerLayoutOpen = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        mNavigationView = (NavigationView) findViewById(R.id.weixin_nav_view);
        mToolbar = (Toolbar) findViewById(R.id.weixin_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("传阅-微信精选");
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        //设置侧滑菜单开关
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        //设置状态栏跟随主题颜色
        dynamicAddView(mToolbar, AttrFactory.BACKGROUND, R.color.colorPrimary);
        //设置NavigationView的字体颜色跟随主题颜色
        dynamicAddView(mNavigationView, AttrFactory.NAVIGATION_VIEW_MENU, R.color.colorPrimary);
        //设置NavigationView的click事件？
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (null != mPreMenuItem) {
                    mPreMenuItem.setChecked(false);
                }
                Intent intent = null;
                switch (item.getItemId()){
                    case R.id.nav_favor:
                        intent = new Intent(mContext, FavorActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_skin:
                        intent = new Intent(mContext, SkinActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_info:
                        intent = new Intent(mContext, AboutActivity.class);
                        startActivity(intent);
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                mPreMenuItem = item;
                return false;
            }
        });

        mArtCatsTab = (TabLayout) findViewById(R.id.art_cats);
        //让tablayout的颜色跟随主题颜色
        dynamicAddView(mArtCatsTab, AttrFactory.BACKGROUND, R.color.colorPrimary);
        mArtViewPager = (ViewPager) findViewById(R.id.art_viewpager);
        mLoadArtCatsProgress = (CircularProgressView) findViewById(R.id.load_art_cats_progress);
        mWrongArea = (LinearLayout) findViewById(R.id.load_art_cats_wrong_area);
        mWrongReasonText = (TextView) findViewById(R.id.wrong_reason_text);
        mTryAgainBtn = (Button) findViewById(R.id.try_again_btn);

        mTryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategoryPresenter.loadArtCategorys();
            }
        });
        mArtViewPager.setAdapter(mPagerAdapter);
        //缓存2页？
        mArtViewPager.setOffscreenPageLimit(3);
    }

    /**
     * 在这里进行ArtCatModel和ArtListModel的初始化
     */
    private void initModel() {
        //使用ApplicationContext来避免内存泄漏的发生
        ArtCategoryModel.getInstance(mContext.getApplicationContext()).setRequestQueue(WeiXinVolley.getQueue());
        ArticleListModel.getInstance(mContext.getApplicationContext()).setRequestQueue(WeiXinVolley.getQueue());
    }

    @Override
    public void onBackPressed() {
        if (mIsDrawerLayoutOpen) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void updateArtCat(List<ArtCategory> list) {
        //进行清空
        mArtCatList.clear();
        mArtCatsTab.removeAllTabs();
        mArtViewPager.removeAllViews();
        mArticleListFragments.clear();
        //清空完毕，开始装载数据
        mArtCatList.addAll(list);
        for (ArtCategory artCategory : mArtCatList) {
            ArticleListFragment articleListFragment = new ArticleListFragment();
            articleListFragment.setCid(artCategory.getCid());
            mArticleListFragments.add(articleListFragment);
        }
        mPagerAdapter.notifyDataSetChanged();
        mArtCatsTab.setupWithViewPager(mArtViewPager);
        mArtCatsTab.setTabsFromPagerAdapter(mPagerAdapter);
        isLoaded = true;
    }

    @Override
    public void showLoading() {
        mLoadArtCatsProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoadArtCatsProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorArea(String reason) {
        mWrongArea.setVisibility(View.VISIBLE);
        mWrongReasonText.setText(reason);
    }

    @Override
    public void hideErrorArea() {
        mWrongArea.setVisibility(View.INVISIBLE);
    }

    @Override
    public void regPresenter(ArtCategoryContract.Presenter presenter) {
        //seems we don't need to do something
    }

    @Override
    public void unregPresenter() {
        //seems we don't need to do something
    }
}
