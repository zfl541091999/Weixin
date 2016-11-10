package com.zfl.weixin.weixinabout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.zfl.weixin.R;
import com.zfl.weixin.utils.ApiUtils;
import com.zfl.weixin.utils.LogUtils;
import com.zfl.weixin.view.CollapsingToolbarLayoutListener;

import de.hdodenhof.circleimageview.CircleImageView;
import solid.ren.skinlibrary.base.SkinBaseActivity;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * Created by ZFL on 2016/11/7.<br>
 * 关于"传阅-微信精选"
 */
public class AboutActivity extends SkinBaseActivity {

    Context mContext;

    Toolbar mToolbar;

    CollapsingToolbarLayout mToolbarLayout;

    AppBarLayout mAppBarLayout;

    CircleImageView mMyHomePage;
    //判断图标是否在显示
    boolean mIsMyHomePageShow = true;

    TextView mAboutMsg;

    TextView mAboutLabel;
    //避免一进来就被设置状态栏颜色
    boolean mIsUiSetup = false;
    //判断状态栏是否是透明
    boolean mIsStatusBarTranslucent = true;
    //判断状态栏是否已经设置颜色
    boolean mIsStatusBarColorSet = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mContext = this;
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsUiSetup = true;
    }

    @Override
    public void changeStatusColor(int color, boolean translucent, Animation animation) {
        if (mIsUiSetup) {
            super.changeStatusColor(color, translucent, animation);
        }
    }

    private void initUI() {
        //沉浸式状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mToolbar = (Toolbar) findViewById(R.id.about_toolbar);
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
        mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.about_collapsing_toolbar_layout);
        mToolbarLayout.setTitle("关于“传阅-微信精选”");
        //展开时字体设置为透明
        mToolbarLayout.setExpandedTitleColor(mContext.getResources().getColor(android.R.color.transparent));
        mToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        //让标题栏收缩后跟随主题颜色
        mToolbarLayout.setContentScrimColor(SkinManager.getInstance().getColor(R.color.colorPrimary));

        mMyHomePage = (CircleImageView) findViewById(R.id.about_my_home_page);

        mAppBarLayout = (AppBarLayout) findViewById(R.id.about_app_bar);
        //监听滚动折叠状态，当appbar折叠后，将homepage的图标隐藏掉
        mAppBarLayout.addOnOffsetChangedListener(new CollapsingToolbarLayoutListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, CollapsingToolbarLayoutListener.State state, int verticalOffset) {
                double totalScrollRange = appBarLayout.getTotalScrollRange();
                if (Math.abs((double) verticalOffset) <= totalScrollRange / 2) {
                    showOrHideHomeIcon(true);
                } else {
                    showOrHideHomeIcon(false);
                }
                if (Math.abs((double) verticalOffset) <= totalScrollRange / 1.43) {
                    makeStatusBarTranslucentOrShow(true);
                }
                if (Math.abs((double) verticalOffset) >= totalScrollRange / 1.40) {
                    makeStatusBarTranslucentOrShow(false);
                }
            }
        });

        mMyHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跑去我的github主页
                Uri uri = Uri.parse(ApiUtils.My_Github_url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        //给简介信息中的链接加入可点击跳转事件
        mAboutMsg = (TextView) findViewById(R.id.about_msg);
        mAboutMsg.setAutoLinkMask(Linkify.ALL);
        mAboutMsg.setMovementMethod(LinkMovementMethod
                .getInstance());
        //超链接字体颜色跟随主题颜色
        mAboutMsg.setLinkTextColor(SkinManager.getInstance().getColor(R.color.colorPrimary));

        mAboutLabel = (TextView) findViewById(R.id.about_label);
        //字体跟随主题颜色
        mAboutLabel.setTextColor(SkinManager.getInstance().getColor(R.color.colorPrimary));
    }

    /**
     * 当show为true时,表示icon需要显示。当为false时，将icon隐藏
     * 同时更改状态栏颜色
     * @param show
     */
    private void showOrHideHomeIcon(boolean show) {
        if (mIsMyHomePageShow == show) return;//状态相同时不需要改变
        Animation animation;
        if (show) {
            animation = AnimationUtils.loadAnimation(mContext, R.anim.scale_in);
            mMyHomePage.setVisibility(View.VISIBLE);
        } else {
            animation = AnimationUtils.loadAnimation(mContext, R.anim.scale_out);
            mMyHomePage.setVisibility(View.INVISIBLE);
        }
        mMyHomePage.clearAnimation();
        mMyHomePage.startAnimation(animation);
        mIsMyHomePageShow = show;
    }

    /**
     * 当translucent为true时，状态栏透明
     * @param translucent
     */
    private void makeStatusBarTranslucentOrShow(boolean translucent) {
        if (mIsStatusBarTranslucent == translucent) return;//状态相同时不需要改变
        if (translucent) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
            alphaAnimation.setDuration(400);
            startStatusBarAnimation(alphaAnimation);
        } else {
            if (!mIsStatusBarColorSet) {
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1f);
                alphaAnimation.setDuration(750);
                changeStatusColor(SkinManager.getInstance().getColorPrimary(), false, alphaAnimation);
                mIsStatusBarColorSet = true;
            } else {
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1f);
                alphaAnimation.setDuration(750);
                startStatusBarAnimation(alphaAnimation);
            }
        }
        mIsStatusBarTranslucent = translucent;
    }
}
