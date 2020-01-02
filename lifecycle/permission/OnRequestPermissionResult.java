package com.hexin.znkflib.support.lifecycle.permission;

import android.support.annotation.NonNull;

/**
 * desc: 权限回调监听
 * @author sunxianglei@myhexin.com
 * @date 2019/10/30.
 */

public interface OnRequestPermissionResult {

    /**
     * Fragment call onRequestPermissionsResult
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

}
