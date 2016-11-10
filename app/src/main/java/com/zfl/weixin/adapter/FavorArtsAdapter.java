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
 * Created by Administrator on 2016/7/12.<br>
 * 目前为止，照搬普通的ArtListAdapter。<br>
 * 后续会实现类似手机QQ会话栏的删除(即取消收藏)效果。<br>
 * 同时加上RecycleView初始的删除动画效果
 */
public class FavorArtsAdapter extends BaseAdapter {

    List<Article> mList;

    Context mContext;

    public FavorArtsAdapter(List<Article> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(mContext).inflate(R.layout.item_favor_art_list, parent, false);
    }

    @Override
    public CommViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new CommViewHolder(realContentView, viewType) {
        };
    }

    @Override
    protected void bindData(CommViewHolder holder, int position) {
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
        if (article.getThumbnails() != null) {
            thumbnailPath = article.getThumbnails().split("\\$")[0];
            Glide.with(mContext).load(thumbnailPath).error(R.mipmap.default_art_img).into(art_thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
