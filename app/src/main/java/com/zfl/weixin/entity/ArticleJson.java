package com.zfl.weixin.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/6/8.
 */
public class ArticleJson {

    /**
     * msg : success
     * result : {"curPage":1,"list":[{"cid":"1","id":"3337211","pubTime":"2016-06-08 10:08","sourceUrl":"http://toutiao.com/i6293637750436397569/","subTitle":"不想拍出呆呆毕业照 这几个小心机一定要学起来！","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/LAA/K5L5PLAM6KXU6IRBHVLA_143x107.jpg","title":"不想拍出呆呆毕业照 这几个小心机一定要学起来！"},{"cid":"1","id":"3337208","pubTime":"2016-06-07 18:05","sourceUrl":"http://toutiao.com/i6293389442702901761/","subTitle":"不要乱剪头发了，这些发型谁剪谁好看！","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/LIQ/K5L5PLIM6KXU6IRBHVMQ_160x120.jpg","title":"不要乱剪头发了，这些发型谁剪谁好看！"},{"cid":"1","id":"3337171","pubTime":"2016-06-08 11:15","sourceUrl":"http://toutiao.com/i6293654873749586433/","subTitle":"这些让你脸变大的小习惯，你中了几条？","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/LIQ/K5L5PLIM6KXU6IRBHVNQ_160x120.jpg","title":"这些让你脸变大的小习惯，你中了几条？"},{"cid":"1","id":"3336966","pubTime":"2016-06-07 09:08","sourceUrl":"http://toutiao.com/i6292989986568405506/","subTitle":"鱼尾裙这样穿真心邪恶，李冰冰穿鱼尾裙最美了！","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/LIA/K5L5PLIM6KXU6IRBHVPA_160x120.jpg","title":"鱼尾裙这样穿真心邪恶，李冰冰穿鱼尾裙最美了！"},{"cid":"1","id":"3336960","pubTime":"2016-06-08 11:04","sourceUrl":"http://toutiao.com/i6293652189516661249/","subTitle":"纹身故事：滥情女人的痛悔","title":"纹身故事：滥情女人的痛悔"},{"cid":"1","id":"3336932","pubTime":"2016-06-08 10:14","sourceUrl":"http://toutiao.com/i6293639320133714433/","subTitle":"流水的男票 铁打的闺蜜 霉霉天后帮的大长腿才是直男斩","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/LIQ/K5L5PLIM6KXU6IRBHVQQ_160x120.jpg","title":"流水的男票 铁打的闺蜜 霉霉天后帮的大长腿才是直男斩"},{"cid":"1","id":"3336781","pubTime":"2016-06-08 07:58","sourceUrl":"http://toutiao.com/i6291795239392772609/","subTitle":"19款轻盈飘逸的连衣裙，增添惬意与凉爽的同时更具女性魅力","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/LQA/K5L5PLQM6KXU6IRBHVSA_160x120.jpg","title":"19款轻盈飘逸的连衣裙，增添惬意与凉爽的同时更具女性魅力"},{"cid":"1","id":"3336744","pubTime":"2016-06-08 10:08","sourceUrl":"http://toutiao.com/i6293637714092753409/","subTitle":"OMG！从不出错的乔妹被粉底\u201c坑\u201d了","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/LYQ/K5L5PLYM6KXU6IRBHVTQ_145x108.jpg","title":"OMG！从不出错的乔妹被粉底\u201c坑\u201d了"},{"cid":"1","id":"3336720","pubTime":"2016-06-08 11:01","sourceUrl":"http://mp.weixin.qq.com/s?__biz=MTY3OTM1NTY2MQ==&mid=2654190792&idx=1&sn=8f040e88104b7703ed285cfb4317d347&scene=4#wechat_redirect","subTitle":"今年这道时髦考题很好解，答案就是：开衩！","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/AIQ/K5L23AIM6KXU6IRBGEJQ_160x120.jpg","title":"今年这道时髦考题很好解，答案就是：开衩！"},{"cid":"1","id":"3336715","pubTime":"2016-06-08 11:01","sourceUrl":"http://mp.weixin.qq.com/s?__biz=MTY3OTM1NTY2MQ==&mid=2654190792&idx=2&sn=7d21cc2a2a1153b87f683d354e41e2c0&scene=4#wechat_redirect","subTitle":"健身自拍之迷思，运动到底要不要化妆？","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/AYQ/K5L23AYM6KXU6IRBGEMQ_160x120.jpg","title":"健身自拍之迷思，运动到底要不要化妆？"},{"cid":"1","id":"3336714","pubTime":"2016-06-08 11:01","sourceUrl":"http://mp.weixin.qq.com/s?__biz=MTY3OTM1NTY2MQ==&mid=2654190792&idx=4&sn=77d6949f7bbd752eb215914cf217d094&scene=4#wechat_redirect","subTitle":"小幸福之外，袁弘和张歆艺想用两人的爱心温暖世界","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/AQA/K5L23AQM6KXU6IRBGELA_160x120.jpg","title":"小幸福之外，袁弘和张歆艺想用两人的爱心温暖世界"},{"cid":"1","id":"3336713","pubTime":"2016-06-08 11:01","sourceUrl":"http://mp.weixin.qq.com/s?__biz=MTY3OTM1NTY2MQ==&mid=2654190792&idx=3&sn=7dbd8d92cb7745f9728544c39720dc64&scene=4#wechat_redirect","subTitle":"吴亦凡、超模Cara...光看阵容，这部戏已经赢了~","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/AYA/K5L23AYM6KXU6IRBGEOA_160x120.jpg","title":"吴亦凡、超模Cara...光看阵容，这部戏已经赢了~"},{"cid":"1","id":"3336656","pubTime":"2016-06-08 11:02","sourceUrl":"http://mp.weixin.qq.com/s?__biz=MjM5MjM0MjEzMg==&mid=2650734709&idx=2&sn=9df966ac15b4cabbf4a9357fdb2032b4&scene=4#wechat_redirect","subTitle":"","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/EQA/K5L3XEQM6KXU6IRBGVVA_160x120.jpg","title":""},{"cid":"1","id":"3336654","pubTime":"2016-06-08 11:02","sourceUrl":"http://mp.weixin.qq.com/s?__biz=MjM5MjM0MjEzMg==&mid=2650734709&idx=1&sn=df145ac5efb359ffd431ce8d23b04ba3&scene=4#wechat_redirect","subTitle":"","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/IAQ/K5L4TIAM6KXU6IRBHFZQ_160x120.jpg","title":""},{"cid":"1","id":"3336574","pubTime":"2016-06-08 10:02","sourceUrl":"http://toutiao.com/i6293636249777357314/","subTitle":"美女街拍酷似吴亦凡惹尖叫","title":"美女街拍酷似吴亦凡惹尖叫"},{"cid":"1","id":"3336556","pubTime":"2016-06-08 09:37","sourceUrl":"http://toutiao.com/i6293629564002238977/","subTitle":"嗨！美女你敢不敢穿长一点的短裤，这样太短了","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/LYA/K5L5PLYM6KXU6IRBHVVA_160x120.jpg","title":"嗨！美女你敢不敢穿长一点的短裤，这样太短了"},{"cid":"1","id":"3336552","pubTime":"2016-06-08 09:18","sourceUrl":"http://toutiao.com/i6293624872006320642/","subTitle":"夏天最有魅力的生肖女TOP3","title":"夏天最有魅力的生肖女TOP3"},{"cid":"1","id":"3336520","pubTime":"2016-06-08 09:07","sourceUrl":"http://mp.weixin.qq.com/s?__biz=MzAwNzEzMDk5OQ==&mid=2650011323&idx=1&sn=75460f7cce4a9d962d2fefa768568166&scene=4#wechat_redirect","subTitle":"❤女人都该用香蕉,它比黄瓜好太多了!","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/3YA/K5LZ63YM6KXU6IRBFUBA_160x120.jpg","title":"❤女人都该用香蕉,它比黄瓜好太多了!"},{"cid":"1","id":"3336513","pubTime":"2016-06-08 06:31","sourceUrl":"http://toutiao.com/i6293581984954319361/","subTitle":"国际权威认证：这就是\u201c世界最丑颜色\u201d","title":"国际权威认证：这就是\u201c世界最丑颜色\u201d"},{"cid":"1","id":"3336489","pubTime":"2016-06-07 14:50","sourceUrl":"http://toutiao.com/i6293339151257305602/","subTitle":"不是你的脸不好看，而是你不知道合适的发型要这么选","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/NAQ/K5L5PNAM6KXU6IRBHVWQ_160x120.jpg","title":"不是你的脸不好看，而是你不知道合适的发型要这么选"}],"total":5588}
     * retCode : 200
     */
    @SerializedName("msg")
    private String msg;
    /**
     * curPage : 1
     * list : [{"cid":"1","id":"3337211","pubTime":"2016-06-08 10:08","sourceUrl":"http://toutiao.com/i6293637750436397569/","subTitle":"不想拍出呆呆毕业照 这几个小心机一定要学起来！","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/LAA/K5L5PLAM6KXU6IRBHVLA_143x107.jpg","title":"不想拍出呆呆毕业照 这几个小心机一定要学起来！"},{"cid":"1","id":"3337208","pubTime":"2016-06-07 18:05","sourceUrl":"http://toutiao.com/i6293389442702901761/","subTitle":"不要乱剪头发了，这些发型谁剪谁好看！","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/LIQ/K5L5PLIM6KXU6IRBHVMQ_160x120.jpg","title":"不要乱剪头发了，这些发型谁剪谁好看！"},{"cid":"1","id":"3337171","pubTime":"2016-06-08 11:15","sourceUrl":"http://toutiao.com/i6293654873749586433/","subTitle":"这些让你脸变大的小习惯，你中了几条？","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/LIQ/K5L5PLIM6KXU6IRBHVNQ_160x120.jpg","title":"这些让你脸变大的小习惯，你中了几条？"},{"cid":"1","id":"3336966","pubTime":"2016-06-07 09:08","sourceUrl":"http://toutiao.com/i6292989986568405506/","subTitle":"鱼尾裙这样穿真心邪恶，李冰冰穿鱼尾裙最美了！","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/LIA/K5L5PLIM6KXU6IRBHVPA_160x120.jpg","title":"鱼尾裙这样穿真心邪恶，李冰冰穿鱼尾裙最美了！"},{"cid":"1","id":"3336960","pubTime":"2016-06-08 11:04","sourceUrl":"http://toutiao.com/i6293652189516661249/","subTitle":"纹身故事：滥情女人的痛悔","title":"纹身故事：滥情女人的痛悔"},{"cid":"1","id":"3336932","pubTime":"2016-06-08 10:14","sourceUrl":"http://toutiao.com/i6293639320133714433/","subTitle":"流水的男票 铁打的闺蜜 霉霉天后帮的大长腿才是直男斩","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/LIQ/K5L5PLIM6KXU6IRBHVQQ_160x120.jpg","title":"流水的男票 铁打的闺蜜 霉霉天后帮的大长腿才是直男斩"},{"cid":"1","id":"3336781","pubTime":"2016-06-08 07:58","sourceUrl":"http://toutiao.com/i6291795239392772609/","subTitle":"19款轻盈飘逸的连衣裙，增添惬意与凉爽的同时更具女性魅力","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/LQA/K5L5PLQM6KXU6IRBHVSA_160x120.jpg","title":"19款轻盈飘逸的连衣裙，增添惬意与凉爽的同时更具女性魅力"},{"cid":"1","id":"3336744","pubTime":"2016-06-08 10:08","sourceUrl":"http://toutiao.com/i6293637714092753409/","subTitle":"OMG！从不出错的乔妹被粉底\u201c坑\u201d了","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/LYQ/K5L5PLYM6KXU6IRBHVTQ_145x108.jpg","title":"OMG！从不出错的乔妹被粉底\u201c坑\u201d了"},{"cid":"1","id":"3336720","pubTime":"2016-06-08 11:01","sourceUrl":"http://mp.weixin.qq.com/s?__biz=MTY3OTM1NTY2MQ==&mid=2654190792&idx=1&sn=8f040e88104b7703ed285cfb4317d347&scene=4#wechat_redirect","subTitle":"今年这道时髦考题很好解，答案就是：开衩！","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/AIQ/K5L23AIM6KXU6IRBGEJQ_160x120.jpg","title":"今年这道时髦考题很好解，答案就是：开衩！"},{"cid":"1","id":"3336715","pubTime":"2016-06-08 11:01","sourceUrl":"http://mp.weixin.qq.com/s?__biz=MTY3OTM1NTY2MQ==&mid=2654190792&idx=2&sn=7d21cc2a2a1153b87f683d354e41e2c0&scene=4#wechat_redirect","subTitle":"健身自拍之迷思，运动到底要不要化妆？","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/AYQ/K5L23AYM6KXU6IRBGEMQ_160x120.jpg","title":"健身自拍之迷思，运动到底要不要化妆？"},{"cid":"1","id":"3336714","pubTime":"2016-06-08 11:01","sourceUrl":"http://mp.weixin.qq.com/s?__biz=MTY3OTM1NTY2MQ==&mid=2654190792&idx=4&sn=77d6949f7bbd752eb215914cf217d094&scene=4#wechat_redirect","subTitle":"小幸福之外，袁弘和张歆艺想用两人的爱心温暖世界","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/AQA/K5L23AQM6KXU6IRBGELA_160x120.jpg","title":"小幸福之外，袁弘和张歆艺想用两人的爱心温暖世界"},{"cid":"1","id":"3336713","pubTime":"2016-06-08 11:01","sourceUrl":"http://mp.weixin.qq.com/s?__biz=MTY3OTM1NTY2MQ==&mid=2654190792&idx=3&sn=7dbd8d92cb7745f9728544c39720dc64&scene=4#wechat_redirect","subTitle":"吴亦凡、超模Cara...光看阵容，这部戏已经赢了~","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/AYA/K5L23AYM6KXU6IRBGEOA_160x120.jpg","title":"吴亦凡、超模Cara...光看阵容，这部戏已经赢了~"},{"cid":"1","id":"3336656","pubTime":"2016-06-08 11:02","sourceUrl":"http://mp.weixin.qq.com/s?__biz=MjM5MjM0MjEzMg==&mid=2650734709&idx=2&sn=9df966ac15b4cabbf4a9357fdb2032b4&scene=4#wechat_redirect","subTitle":"","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/EQA/K5L3XEQM6KXU6IRBGVVA_160x120.jpg","title":""},{"cid":"1","id":"3336654","pubTime":"2016-06-08 11:02","sourceUrl":"http://mp.weixin.qq.com/s?__biz=MjM5MjM0MjEzMg==&mid=2650734709&idx=1&sn=df145ac5efb359ffd431ce8d23b04ba3&scene=4#wechat_redirect","subTitle":"","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/IAQ/K5L4TIAM6KXU6IRBHFZQ_160x120.jpg","title":""},{"cid":"1","id":"3336574","pubTime":"2016-06-08 10:02","sourceUrl":"http://toutiao.com/i6293636249777357314/","subTitle":"美女街拍酷似吴亦凡惹尖叫","title":"美女街拍酷似吴亦凡惹尖叫"},{"cid":"1","id":"3336556","pubTime":"2016-06-08 09:37","sourceUrl":"http://toutiao.com/i6293629564002238977/","subTitle":"嗨！美女你敢不敢穿长一点的短裤，这样太短了","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/LYA/K5L5PLYM6KXU6IRBHVVA_160x120.jpg","title":"嗨！美女你敢不敢穿长一点的短裤，这样太短了"},{"cid":"1","id":"3336552","pubTime":"2016-06-08 09:18","sourceUrl":"http://toutiao.com/i6293624872006320642/","subTitle":"夏天最有魅力的生肖女TOP3","title":"夏天最有魅力的生肖女TOP3"},{"cid":"1","id":"3336520","pubTime":"2016-06-08 09:07","sourceUrl":"http://mp.weixin.qq.com/s?__biz=MzAwNzEzMDk5OQ==&mid=2650011323&idx=1&sn=75460f7cce4a9d962d2fefa768568166&scene=4#wechat_redirect","subTitle":"❤女人都该用香蕉,它比黄瓜好太多了!","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/3YA/K5LZ63YM6KXU6IRBFUBA_160x120.jpg","title":"❤女人都该用香蕉,它比黄瓜好太多了!"},{"cid":"1","id":"3336513","pubTime":"2016-06-08 06:31","sourceUrl":"http://toutiao.com/i6293581984954319361/","subTitle":"国际权威认证：这就是\u201c世界最丑颜色\u201d","title":"国际权威认证：这就是\u201c世界最丑颜色\u201d"},{"cid":"1","id":"3336489","pubTime":"2016-06-07 14:50","sourceUrl":"http://toutiao.com/i6293339151257305602/","subTitle":"不是你的脸不好看，而是你不知道合适的发型要这么选","thumbnails":"$http://f2.mob.com/imgs/2016/06/08/NAQ/K5L5PNAM6KXU6IRBHVWQ_160x120.jpg","title":"不是你的脸不好看，而是你不知道合适的发型要这么选"}]
     * total : 5588
     */
    @SerializedName("result")
    private ArticleList result;
    @SerializedName("retCode")
    private String retCode;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArticleList getResult() {
        return result;
    }

    public void setResult(ArticleList result) {
        this.result = result;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public static class ArticleList {
        @SerializedName("curPage")
        private int curPage;
        @SerializedName("total")
        private int total;
        /**
         * cid : 1
         * id : 3337211
         * pubTime : 2016-06-08 10:08
         * sourceUrl : http://toutiao.com/i6293637750436397569/
         * subTitle : 不想拍出呆呆毕业照 这几个小心机一定要学起来！
         * thumbnails : $http://f2.mob.com/imgs/2016/06/08/LAA/K5L5PLAM6KXU6IRBHVLA_143x107.jpg
         * title : 不想拍出呆呆毕业照 这几个小心机一定要学起来！
         */
        @SerializedName("list")
        private List<Article> list;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<Article> getList() {
            return list;
        }

        public void setList(List<Article> list) {
            this.list = list;
        }
    }
}
