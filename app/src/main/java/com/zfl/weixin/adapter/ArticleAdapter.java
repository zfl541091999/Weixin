package com.zfl.weixin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zfl.weixin.R;
import com.zfl.weixin.entity.Article;

import java.util.List;

/**
 * Created by Administrator on 2016/6/11.
 */
public class ArticleAdapter extends BaseAdapter {

    private static final String TAG = "ArticleAdapter";

    static final int NormalType = 11;
    static final int FooterType = 22;

    List<Article> mList;

    View mFooterView;
    //当FooterView不为空时，FooterCount=1
    int mFooterCount = 0;

    Context mContext;

    LayoutInflater mInflater;

    public ArticleAdapter(List<Article> list, Context context) {
        mList = list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        if (viewType == FooterType) {
            return mFooterView;
        } else {
            return mInflater.inflate(R.layout.item_art_list, null);
        }
    }

    @Override
    public CommViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new CommViewHolder(realContentView, viewType) {
        };
    }

    @Override
    protected void bindData(CommViewHolder holder, int position) {
        if (holder.getViewType() == NormalType) {
            Article article = mList.get(position);
            ImageView art_thumbnail = holder.getView(R.id.art_thumbnail);
            TextView art_title = holder.getView(R.id.art_title);
            TextView art_sub_title = holder.getView(R.id.art_sub_title);
            TextView art_pubtime = holder.getView(R.id.art_time);
            art_title.setText(article.getTitle());
            art_sub_title.setText(article.getSubTitle());
            art_pubtime.setText(article.getPubTime());
            art_thumbnail.setImageResource(R.mipmap.default_art_img);
            //图片缩略图地址，只取第一个图片显示
            String thumbnailPath = "";
            //有些新闻居然没有图片！
            if (article.getThumbnails() != null){
                thumbnailPath = article.getThumbnails().split("\\$")[0];
                Glide.with(mContext).load(thumbnailPath).error(R.mipmap.default_art_img).into(art_thumbnail);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + mFooterCount;
    }

    int getContentItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isFooterView(position)) return FooterType;
        return NormalType;
    }

    public void setFooterView(View view) {
        if (view != null) {
            mFooterView = view;
            mFooterCount = 1;
        }
    }

    /**
     * 检测当前position是否是footerview
     *
     * @param position
     * @return
     */
    boolean isFooterView(int position) {
        if (!hasFooterView()) return false;
        if (position >= getContentItemCount()) {
            return true;
        }
        return false;
    }

    boolean hasFooterView() {
        if (mFooterCount != 0 && mFooterView != null) {
            return true;
        }
        return false;
    }
}
