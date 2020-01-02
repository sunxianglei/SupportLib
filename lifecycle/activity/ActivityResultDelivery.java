package com.hexin.znkflib.support.lifecycle.activity;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * desc: 对 onActivityResult 的分发管理接口
 * @author sunxianglei@myhexin.com
 * @date 2019/10/30.
 */

public class ActivityResultDelivery implements IActivityResultRegistry, OnActivityResult {

    private List<OnActivityResult> mLifecycleListeners = new ArrayList<>();


    @Override
    public void addListener(OnActivityResult listener) {
        mLifecycleListeners.add(listener);
    }

    @Override
    public void removeListener(OnActivityResult listener) {
        if(listener == null){
            mLifecycleListeners.clear();
            return ;
        }
        mLifecycleListeners.remove(listener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (OnActivityResult listener: mLifecycleListeners){
            listener.onActivityResult(requestCode, resultCode, data);
        }
    }
}
