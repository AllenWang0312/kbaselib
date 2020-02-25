# kbaselib
kotlin dev common library for Android

# 项目结构
  ...
  * common
    + adapter //常见adapter 业务无关可复用
    + base
      * gson //适配若对象语言服务器将数值类型字段返回为string
      * net //封装Retrofit2 使用多client支持多api链接
      * BaseActivity.kt//实现基础功能封装 包括统一样式的progressbar(网络加载等待 可自定义)
      * BaseApplication.kt//使用ArrayList管理实例化的activity 方便用户随时退出
      * BaseBarActivity.kt//需要使用基础样式的activity时 继承此activity或变种 快速高效实现布局
      * ...
      * BaseMVPActivity.kt //需要使用MVP封装activity时 继承此activity或变种 泛型传入Presenter onCreate 时初始化他
  * res //样式无关资源
  * res_ui //基础主题样式 可直接复制修改  在build.gradle里修改项目使用的样式库
  * res_biubiu //本工程采用的样式
  * res_xiaohongshu //小红书样式
