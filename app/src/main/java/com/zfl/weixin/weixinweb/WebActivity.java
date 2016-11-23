package com.zfl.weixin.weixinweb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;
import com.zfl.weixin.R;
import com.zfl.weixin.entity.Article;
import com.zfl.weixin.global.WeiXinApp;
import com.zfl.weixin.model.rxmodel.favor_artlist.FavorArtListRxModel;
import com.zfl.weixin.utils.LogUtils;

import solid.ren.skinlibrary.attr.base.AttrFactory;
import solid.ren.skinlibrary.base.SkinBaseActivity;

/**
 * Created by Administrator on 2016/6/11.
 */
public class WebActivity extends SkinBaseActivity implements WebContract.View{

    private static final String TAG = "WebActivity";

    Context mContext;
    //默认网页标题
    String mTitle = "默认标题";
    //默认网页地址
    String mUrl = "www.mtime.com";
    //传入到WebActivity的Article
    Article mArticle;

    Toolbar mToolbar;

    WebView mWebView;
    //点击此按钮时,将当前文章加入收藏
    FloatingActionButton mFavorButton;
    //判断当前浏览的文章是否是收藏文章
    boolean mIsCurArtFavor = false;

    WebContract.Presenter mPresenter;

    ProgressBar mWebLoadingProgressBar;
    int mWebLoadingProgress = 0;

    //监视内存泄漏
    RefWatcher mRefWatcher;
    WeiXinApp mWeiXinApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mWeiXinApp = (WeiXinApp) getApplication();
        mRefWatcher = WeiXinApp.getRefWatcher(mContext);
        mRefWatcher.watch(this);
        setContentView(R.layout.activity_webview);
        initData();
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //设置收藏图标
        mPresenter.isFavorArticle(mArticle);
        //载入微信网页
        mWebView.loadUrl(mUrl);
    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.webview_toolbar);
        mWebView = (WebView) findViewById(R.id.weixin_webview);
        mWebLoadingProgressBar = (ProgressBar) findViewById(R.id.weixin_webview_loading);
        mFavorButton = (FloatingActionButton) findViewById(R.id.weixin_favor);
        //设置favorButton跟随主题颜色
        dynamicAddView(mFavorButton, AttrFactory.BACKGROUND_TINT, R.color.half_alpha_colorPrimary);
        mToolbar.setTitle(mTitle);
        //设置状态栏跟随主题颜色
        dynamicAddView(mToolbar, AttrFactory.BACKGROUND, R.color.colorPrimary);
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
        //支持JS
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        //网页自适应屏幕大小
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //网页加载进度条
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                //有时候会发生怪事，加载网页时会突然提醒已经全部加载完毕
                //只好用笨方法，监听上一个加载进度，与当前进度相比较
                //差值超过80的，不会设置给Progress
                LogUtils.i(TAG, "Progress:" + newProgress);
                if (newProgress - mWebLoadingProgress < 70) {
                    if (newProgress > mWebLoadingProgress) {
                        mWebLoadingProgressBar.setProgress(newProgress);
                        mWebLoadingProgress = newProgress;
                    }
                    if (newProgress == 100) {
                        //代表网页已经加载完成，进度条隐藏
                        mWebLoadingProgressBar.setVisibility(View.INVISIBLE);
                        LogUtils.i(LogUtils.TAG, "WebView is Done!");
                    }
                }

            }
        });

        mFavorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将当前文章加入收藏或者取消收藏(目前只实现数据库收藏)
                if (mIsCurArtFavor) {
                    mPresenter.removeFavorArticle(mArticle);
                } else {
                    mPresenter.saveFavorArticle(mArticle);
                }
            }
        });

    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) finish();
        mArticle = (Article) intent.getSerializableExtra("article");
        if (mArticle != null) {
            mTitle = mArticle.getTitle();
            mUrl = mArticle.getSourceUrl();
//            finish();
        }
        mPresenter = new WebPresenter(this,new FavorArtListRxModel(mContext));
    }

    @Override
    public void showFavorButton() {
        mFavorButton.show();
    }

    @Override
    public void hideFavorButton() {
        mFavorButton.hide();
    }

    @Override
    public void setFavorButtonSrc(boolean isFavor) {
        //设置当前浏览文章布尔值-
        mIsCurArtFavor = isFavor;
        if (isFavor){
            mFavorButton.setImageResource(R.mipmap.ic_favor);
        } else{
            mFavorButton.setImageResource(R.mipmap.ic_un_favor);
        }
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void regPresenter(WebContract.Presenter presenter) {
        //we don't need to do things in here
    }

    @Override
    public void unregPresenter() {
        //we don't need to do things in here
    }
}
