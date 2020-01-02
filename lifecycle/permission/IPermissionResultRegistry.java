package com.hexin.znkflib.support.lifecycle.permission;

/**
 * desc: 注册监听
 * @author sunxianglei@myhexin.com
 * @date 2019/10/30.
 */

public interface IPermissionResultRegistry {

    /**
     * 添加被监听的对象
     * @param listener
     */
    void addListener(OnRequestPermissionResult listener);

    /**
     * 移除被监听对象
     * @param listener
     */
    void removeListener(OnRequestPermissionResult listener);

}
