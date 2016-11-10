package com.zfl.weixin.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/6/8.<br>
 * 微信精选文章类别
 */
public class ArtCategory {
    @SerializedName("cid")
    private String cid;
    @SerializedName("name")
    private String name;

    public ArtCategory() {

    }

    public ArtCategory(String cid, String name) {
        this.cid = cid;
        this.name = name;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
