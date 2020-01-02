package com.hexin.znkflib.support.lifecycle;

import android.app.Activity;
import android.app.FragmentManager;

import com.hexin.znkflib.support.lifecycle.activity.OnActivityResult;
import com.hexin.znkflib.support.lifecycle.permission.OnRequestPermissionResult;

/**
 * desc: 注册Fragment生命周期给其他对象的单例类
 * @author sunxianglei@myhexin.com
 * @date 2019/4/19.
 */

public class LifecycleDetector {
    public static final String FRAGMENT_TAG = "com.hexin.znkflib.lifecycle.fragment";

    public static LifecycleDetector getInstance(){
        return Holder.instance;
    }

    private static class Holder {
        private static final LifecycleDetector instance = new LifecycleDetector();
    }

    /**
     * 监听 lifecycleListener 对象
     * @param activity
     * @param listener
     */
    public void observeLifecycle(Activity activity, LifecycleListener listener){
        LifecycleManagerFragment lifecycleFragment = getLifecycleFragment(activity.getFragmentManager());
        lifecycleFragment.getMyLifecycle().addListener(listener);
    }

    /**
     * 移除 lifecycleListener 对象
     * @param activity
     * @param listener
     */
    public void removeLifecycle(Activity activity, LifecycleListener listener){
        LifecycleManagerFragment lifecycleFragment = getLifecycleFragment(activity.getFragmentManager());
        lifecycleFragment.getMyLifecycle().removeListener(listener);
    }

    /**
     * 监听 PermissionsResultListener 对象
     * @param activity
     * @param listener
     */
    public void observePermission(Activity activity, OnRequestPermissionResult listener){
        LifecycleManagerFragment lifecycleFragment = getLifecycleFragment(activity.getFragmentManager());
        lifecycleFragment.getPermissionsResultCall().addListener(listener);
    }

    /**
     * 移除 PermissionsResultListener 对象
     * @param activity
     * @param listener
     */
    public void removePermission(Activity activity, OnRequestPermissionResult listener){
        LifecycleManagerFragment lifecycleFragment = getLifecycleFragment(activity.getFragmentManager());
        lifecycleFragment.getPermissionsResultCall().removeListener(listener);
    }

    /**
     * 监听 ActivityResultListener 对象
     * @param activity
     * @param listener
     */
    public void observeActivityResult(Activity activity, OnActivityResult listener){
        LifecycleManagerFragment lifecycleFragment = getLifecycleFragment(activity.getFragmentManager());
        lifecycleFragment.getActivityResultCall().addListener(listener);
    }

    /**
     * 移除 ActivityResultListener 对象
     * @param activity
     * @param listener
     */
    public void removeActivityResult(Activity activity, OnActivityResult listener){
        LifecycleManagerFragment lifecycleFragment = getLifecycleFragment(activity.getFragmentManager());
        lifecycleFragment.getActivityResultCall().removeListener(listener);
    }

    /**
     * 移除所有监听
     * @param activity
     */
    public void removeAll(Activity activity){
        FragmentManager fm =  activity.getFragmentManager();
        LifecycleManagerFragment lifecycleFragment = getLifecycleFragment(fm);
        lifecycleFragment.getMyLifecycle().removeListener(null);
        lifecycleFragment.getPermissionsResultCall().removeListener(null);
        lifecycleFragment.getActivityResultCall().removeListener(null);
    }

    private LifecycleManagerFragment getLifecycleFragment(FragmentManager fm){
        LifecycleManagerFragment lifecycleFragment = (LifecycleManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if(lifecycleFragment == null){
            lifecycleFragment = new LifecycleManagerFragment();
            fm.beginTransaction().add(lifecycleFragment, FRAGMENT_TAG).commitAllowingStateLoss();
            fm.executePendingTransactions();
        }
        return lifecycleFragment;
    }

}
