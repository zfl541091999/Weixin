package com.zfl.weixin.weixin.articlelist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.zfl.weixin.R;
import com.zfl.weixin.adapter.ArticleAdapter;
import com.zfl.weixin.adapter.BaseAdapter;
import com.zfl.weixin.entity.Article;
import com.zfl.weixin.model.norm_model.articlelist.ArticleListModel;
import com.zfl.weixin.model.rxmodel.articlelist.ArticleListRxModel;
import com.zfl.weixin.view.DefaultItemDecoration;
import com.zfl.weixin.weixinweb.WebActivity;

import java.util.ArrayList;
import java.util.List;

import solid.ren.skinlibrary.base.SkinBaseFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Administrator on 2016/6/8.
 */
public class ArticleListFragment extends SkinBaseFragment implements ArticleListContract.View {

    Context mContext;
    //单例模式的model使用AppContext进行初始化
    Context mAppContext;
    //文章类别
    String mCid = "";
    //目前是第几页
    int mCurPage = 0;
    //精选文章的合集
    List<Article> mArticleList;
    //Article的Adapter
    ArticleAdapter mAdapter;
    //上拉刷新时显示的footerView
    View mFooterView;
    //上拉刷新发送错误时的原因
    TextView mLoadMoreWrongReasonText;
    //上拉刷新时的进度条
    CircularProgressView mLoadMoreProgress;
    //下拉刷新的layout
    SwipeRefreshLayout mRefreshLayout;
    //装载着Article的recycView
    RecyclerView mRecyclerView;
    //加载数据时，遇到的错误
    TextView mWrongReasonText;
    //点击它可以快速返回RecycleView顶部
    ImageView mUpToTop;
    ArticleListContract.Presenter mPresenter;
    //设置正在加载中的boolean值，防止多次加载
    boolean mIsLoadingMore = false;
    //设置新闻是否已经加载的布尔值，防止每次看完新闻都重复加载
    boolean mIsLoaded = false;

    public ArticleListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFooterView = inflater.inflate(R.layout.item_art_list_footer, null);
        mLoadMoreWrongReasonText = (TextView) mFooterView.findViewById(R.id.load_more_wrong_reason_text);
        mLoadMoreProgress = (CircularProgressView) mFooterView.findViewById(R.id.load_more_circle_progress);
        View rootView = inflater.inflate(R.layout.frag_art_list, container, false);
        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.art_refresh_layout);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.art_list_view);
        mWrongReasonText = (TextView) rootView.findViewById(R.id.wrong_reason_text);
        mUpToTop = (ImageView) rootView.findViewById(R.id.up_to_top);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mAppContext = mContext.getApplicationContext();
        initData();
        initUI();
    }

    private void initUI() {
        //设置refreshLayout下拉刷新时的圈圈跟随主题颜色
        dynamicAddView(mRefreshLayout, "colorScheme", R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //在这里让Presenter获取首页数据
                mPresenter.getArticles(mCid);
            }
        });
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DefaultItemDecoration(mContext, R.drawable.default_recycleview_item_decoration));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager mLayoutManager = recyclerView.getLayoutManager();
                int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                //最后一项显示且是下滑的时候调用加载
                if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                    //在isLoadingMore为false的情况下，让presenter加载更多数据
                    if(!mIsLoadingMore) {
                        mCurPage++;
                        mPresenter.getMoreArticles(mCid,mCurPage);
                        mIsLoadingMore = true;
                    }
                }
            }
        });
        //让“滚动到顶部的按钮跟随主题颜色”
        dynamicAddView(mUpToTop, "background", R.color.half_alpha_colorPrimary);
        mUpToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在这里让recycleView快速返回顶部
                mRecyclerView.smoothScrollToPosition(0);
            }
        });

    }

    private void initData() {
        mPresenter = ArticleListPresenter.getInstance(ArticleListModel.getInstance(mAppContext),
                ArticleListRxModel.getInstance(mAppContext));
        regPresenter(mPresenter);
        mArticleList = new ArrayList<>();
        mAdapter = new ArticleAdapter(mArticleList, mContext);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(mContext, WebActivity.class);
                //直接将Article放入intent
                intent.putExtra("article", mArticleList.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!mIsLoaded) {
            //当Loaded布尔值为false时，加载它
            mPresenter.start(mCid);
            mIsLoaded = true;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //在这里视图被destroy，设置加载布尔值为false
        mIsLoaded = false;
        unregPresenter();
        //List进行清空
        mArticleList.clear();
        mArticleList = null;
    }

    @Override
    public void updateArtList(List<Article> list) {
        //现在又回到首页了
        hideErrorMessage();
        mCurPage = 1;
        mArticleList.clear();
        mArticleList.addAll(list);
        if(mArticleList.size() > 0) {
            //只有在列表存在的情况下，footerview才能存在
            mAdapter.setFooterView(mFooterView);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addMoreArtList(List<Article> list) {
        mArticleList.addAll(list);
        mAdapter.notifyDataSetChanged();
        //在这里让loadingMore设置为false
        mIsLoadingMore = false;
    }

    @Override
    public void setLoading(boolean refreshing) {
        mRefreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void showErrorMessage(String reason) {
        if (mArticleList.size() > 0) {
            //在文章列表还存在的情况下，用Toast显示错误原因
            Toast.makeText(mContext, reason, Toast.LENGTH_SHORT).show();
        } else {
            mWrongReasonText.setVisibility(View.VISIBLE);
            mWrongReasonText.setText(reason);
        }
    }

    @Override
    public void hideErrorMessage() {
        mWrongReasonText.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLoadMoreLabel() {
        mLoadMoreProgress.setVisibility(View.VISIBLE);
        mLoadMoreWrongReasonText.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorFooter(String reason) {
        mLoadMoreProgress.setVisibility(View.INVISIBLE);
        mLoadMoreWrongReasonText.setVisibility(View.VISIBLE);
        mLoadMoreWrongReasonText.setText(reason);
        //在这里让loadingMore设置为false
        mIsLoadingMore = false;
    }

    @Override
    public void setCid(String cid) {
        mCid = cid;
    }

    @Override
    public String getCid() {
        return mCid;
    }

    @Override
    public void regPresenter(ArticleListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
        mPresenter.addArtListView(this);
    }

    @Override
    public void unregPresenter() {
        mPresenter.removeArtListView(this);
    }
}
