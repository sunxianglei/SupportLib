package com.hexin.znkflib.support.lifecycle.permission;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * desc: 对权限申请 onRequestResultPermission 的分发管理接口
 * @author sunxianglei@myhexin.com
 * @date 2019/10/30.
 */

public class PermissionsResultDelivery implements IPermissionResultRegistry, OnRequestPermissionResult {

    private List<OnRequestPermissionResult> mLifecycleListeners = new ArrayList<>();

    @Override
    public void addListener(OnRequestPermissionResult listener) {
        mLifecycleListeners.add(listener);
    }

    @Override
    public void removeListener(OnRequestPermissionResult listener) {
        if(listener == null){
            mLifecycleListeners.clear();
            return ;
        }
        mLifecycleListeners.remove(listener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        for (OnRequestPermissionResult listener: mLifecycleListeners){
            listener.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
