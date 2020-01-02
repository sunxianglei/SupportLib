package com.hexin.znkflib.support.reactive;

/**
 * desc: Observer观察者
 * @author sunxianglei@myhexin.com
 * @date 2019/8/16.
 */

public interface Observer<T> {

    /**
     * 成功获取数据
     * @param data
     */
    void success(T data);

    /**
     * 获取数据失败
     * @param msg
     */
    void fail(String msg);

}
