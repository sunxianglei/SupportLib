package com.hexin.znkflib.support.network.api;

/**
 * desc: 已准备好执行的请求对象
 * @author sunxianglei@myhexin.com
 * @date 2019/8/8.
 */

public interface Call {

    /**
     * 异步
     * @param callBack
     * @return
     */
    void enqueue(ResultCallBack callBack);

    /**
     * 同步
     * @return
     */
     String execute();
}
