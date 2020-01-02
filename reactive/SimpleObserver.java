package com.hexin.znkflib.support.reactive;

/**
 * desc: 简化Observer观察者，不需要回调失败的情况
 * @author sunxianglei@myhexin.com
 * @date 2019/8/16.
 */

public interface SimpleObserver<T> {

    /**
     * 成功获取数据
     * @param data
     */
    void success(T data);

}
