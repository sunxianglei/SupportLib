package com.hexin.znkflib.support.lifecycle.activity;

import android.content.Intent;

/**
 * desc: 注册监听
 * @author sunxianglei@myhexin.com
 * @date 2019/10/30.
 */

public interface OnActivityResult {

    /**
     * fragment call onActivityResult
     * @param requestCode
     * @param resultCode
     * @param data
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);

}
