package com.hexin.znkflib.support.network.api;

import com.hexin.znkflib.support.network.impl.HttpRequestSource;

/**
 * desc: FineHttp是网络请求二次封装框架。
 * 生产 call 对象的工厂类，call 对象是发请求和响应的二次封装对象。
 * 支持自定义IRequestSource请求源，可替换底层的真正网络请求的框架。
 *
 * <p>使用示例：<pre>{@code
 *     Request request = new Request.Builder()
 *                 .method(Request.GET)
 *                 .url("http://www.baidu.com")
 *                 .putParam("key1", "value1")
 *                 .putParam("key2", "value2");
 *      FineHttp.get().newCall(request)
 *         .enqueue(new ResultCallBack() {
 *             @Override
 *             public void success(String result) {
 *                  // 需要做一次数据解析并更新UI
 *             }
 *             @Override
 *             public void fail(String message) {
 *             }
 *         });
 *      // 推荐做法：结合FineObservable
 *      FineHttp.get().newObservable(request)
 *          .map((result) -> {
 *              // 这里是子线程，做数据解析并返回
 *          })
 *          .subscribe((data) -> {
 *              // 这里是主线程，用于更新UI
 *          })
 * }</pre>
 *
 * @author sunxianglei@myhexin.com
 * @date 2019/8/8.
 */

public final class FineHttp {

    private IRequestSource source;

    public static FineHttp get(){
        return Holder.instance;
    }

    public FineHttp(){
        this.source = new HttpRequestSource();
    }

    private static final class Holder {
        private static final FineHttp instance = new FineHttp();
    }

    public void register(IRequestSource source){
        this.source = source;
    }

    public Call newCall(RequestInfo request){
        return new RealCall(source, request, true);
    }

    public FineObservable<String> newObservable(RequestInfo request){
        return new FineObservable<>(new RealCall(source, request,false));
    }

}
