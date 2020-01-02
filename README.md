# SupportLib
各种底层封装库。

### 1. bus

模仿 EventBus 写的库，两个特点：

- 去掉了 EventBus 的一些功能，代码体积更小。
- 支持发送到指定的方法和类中。



### 2.imageloader

图片二次封装库。

接口抽象，可以扩展替换底层真正的图片框架，目前用的是比较老的 `ImageLoader` 框架。



### 3. lifecycle

生命周期感知库。

实现任意对象都能监听到 `Activity/Fragment` 生命周期，并且扩展出权限申请监听、`ActivityResult` 回调监听。更具体的看这里： [LifecycleAwareLib](https://github.com/sunxianglei/LifecycleAwareLib)



### 4. log

可视化日志窗口，能在app上直接动态调出日志窗口，方便查找问题。



### 5. network

网络请求二次封装库。

接口抽象，可以扩展替换底层真正的网络框架，目前网络框架用的是简单的 `HttpUrlConnection` 工具类，配合响应式编程库实现链式调用。原理解析看这里：[网络库封装之响应式编程](https://www.yuque.com/kakasi/xlzyhv/of61zu)



### 6. reactive

响应式编程库。

仿 RxJava 链式调用的实现简单写了几个操作符，可以扩展配合上面的 `network` 库。原理解析看这里：[网络库封装之响应式编程](https://www.yuque.com/kakasi/xlzyhv/of61zu)