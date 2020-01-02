package com.hexin.znkflib.support.reactive;

/**
 * desc: 通用数据类型转换接口
 * @author sunxianglei@myhexin.com
 * @date 2019/8/16.
 */

public interface Function<T, R> {

    /**
     * 转换数据类型
     * @param data
     * @return
     */
    R apply(T data);

}
