package com.hexin.znkflib.support.lifecycle.permission;

/**
 * desc: 权限请求结果回调
 * @author sunxianglei@myhexin.com
 * @date 2019/10/31.
 */

public interface IPermissionResult {

    /**
     * 允许申请的权限
     */
    void granted();

    /**
     * 拒绝申请的权限
     * @param isForever 是否永久拒绝
     */
    void deny(boolean isForever);

}
