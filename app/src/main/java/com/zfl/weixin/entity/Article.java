package com.zfl.weixin.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/8.<br>
 * 微信精选文章数据实体<br>
 * Serializable是为了方便将Article传入WebActivity
 */
public class Article implements Serializable{
    @SerializedName("cid")
    private String cid;
    @SerializedName("id")
    private String id;
    @SerializedName("pubTime")
    private String pubTime;
    @SerializedName("sourceUrl")
    private String sourceUrl;
    @SerializedName("subTitle")
    private String subTitle;
    @SerializedName("thumbnails")
    private String thumbnails;
    @SerializedName("title")
    private String title;

    public Article() {

    }

    public Article(String cid, String id, String pubTime, String subTitle, String sourceUrl, String thumbnails, String title) {
        this.cid = cid;
        this.id = id;
        this.pubTime = pubTime;
        this.subTitle = subTitle;
        this.sourceUrl = sourceUrl;
        this.thumbnails = thumbnails;
        this.title = title;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
