package com.hexin.znkflib.support.network.api;

/**
 * desc: 上层应用需调用的接口
 * @author sunxianglei@myhexin.com
 * @date 2019/8/8.
 */

public interface ResultCallBack {

    /**
     * 上层应用统一回调字符串比较通用
     * @param result
     */
    void success(String result);

    /**
     * 失败的回调
     * @param message
     */
    void fail(String message);
}
