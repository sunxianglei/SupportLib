package com.hexin.znkflib.support.lifecycle;

/**
 * desc: 对象生命周期注册接口
 * @author sunxianglei@myhexin.com
 * @date 2019/4/19.
 */

public interface Lifecycle {

    /**
     * 添加被监听的对象
     * @param listener
     */
    void addListener(LifecycleListener listener);

    /**
     * 移除被监听对象
     * @param listener
     */
    void removeListener(LifecycleListener listener);
}
