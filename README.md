应用功能：可以浏览微信精选的文章，在本地保存用户收藏的文章，分享文章，同时支持灵活的换肤功能。
此应用是我在工作生活闲暇之余的作品，应用了不少我学习的Android新知识，算是我拿来练手的APP。同时，我也参考了不少Github上优秀的开源项目，应用断断续续地开发，一路走来，现在终于有点像样的样子，非常感谢给予我灵感和帮助的开源项目的作者。
此应用开发时，使用的设计模式，数据来源，界面设计，使用的Github开源项目如下：

1、本应用使用的开发设计模式是MVP设计模式。

2、数据来源于Mob官网的“微信精选”，并且数据是完全免费的，非常感谢Mob官网为移动开发者所提供的数据服务。Mob官网地址：http://www.mob.com

3、界面设计理念使用了Google的Material Design，并且使用了Google为Material Design所提供的一部分Icon。Google提供的Icon地址：http://google.github.io/material-design-icons

4、网络Json数据请求方面，使用了开源框架volley和retrofit，代码均有保留，用户可以随时切换数据请求方式。
retrofit框架地址：https://github.com/square/retrofit
volley框架地址：https://android.googlesource.com/platform/frameworks/volley/

5、图片请求方面，使用的是开源框架Glide。框架地址：https://github.com/bumptech/glide

6、因为涉及到网络请求和本地IO数据处理，使用的是优秀的异步处理框架RxJava。框架地址：https://github.com/ReactiveX/RxJava

7、某些组件使用了仿Android5.0的Material风格进度动画，CircularProgressview，它可以在Android5.0以下的机型实现这种Material风格进度动画效果。Github地址：https://github.com/rahatarmanahmed/CircularProgressView

8、某些组件使用了圆角图片，circleimageview。Github地址：https://github.com/hdodenhof/CircleImageView

9、应用使用了leakcanary这款开源项目来检测内存泄漏，它的Github项目地址：https://github.com/square/leakcanary

10、收藏文章界面的RecyclerView使用了侧滑菜单，有“分享”和“删除”两个选项，它使用了SwipeRecyclerView，非常感谢该开源项目的作者，严振杰。该开源项目的地址是：https://github.com/yanzhenjie/SwipeRecyclerView

11、应用更换皮肤的功能基于开源框架Android-Skin-Loader，它的Github地址是：https://github.com/fengjundev/Android-Skin-Loader。

12、同时也非常感谢@_SOLID，他将开源框架Android-Skin-Loader适当进行了封装，解决了AppCompatActivity的兼容问题。_SOLID封装的开源项目地址：https://github.com/burgessjp/MaterialDesignDemo

备注：目前应用的分享功能只是简单的调用了Android系统的分享功能，如果想要更精确更细致的分享，请使用ShareSDK。

作者：瘸腿蚊
