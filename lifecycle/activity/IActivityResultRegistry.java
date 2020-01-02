package com.hexin.znkflib.support.lifecycle.activity;

/**
 * desc: 对 onActivityResult 的分发管理接口
 * @author sunxianglei@myhexin.com
 * @date 2019/10/30.
 */

public interface IActivityResultRegistry {

    /**
     * 添加被监听的对象
     * @param listener
     */
    void addListener(OnActivityResult listener);

    /**
     * 移除被监听对象
     * @param listener
     */
    void removeListener(OnActivityResult listener);


}
