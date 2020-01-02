package com.hexin.znkflib.support.lifecycle.activity;

import android.content.Intent;

/**
 * desc: onActivityResult 最终结果回调
 * @author sunxianglei@myhexin.com
 * @date 2019/11/1.
 */

public interface IActivityResultCall {

    /**
     * @param isOk 结果回调成功
     * @param data 数据
     */
    void callResult(boolean isOk, Intent data);
}
