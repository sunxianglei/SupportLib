package com.hexin.znkflib.support.network.api;

/**
 * desc: 请求源
 * @author sunxianglei@myhexin.com
 * @date 2019/8/6.
 */

public interface IRequestSource {

    /**
     * 网络请求在子线程执行
     * @param request
     * @param callBack
     */
    void async(RequestInfo request, ResultCallBack callBack);

    /**
     * 网络请求在主线程执行
     * @param request
     * @return
     */
    String sync(RequestInfo request);

}
