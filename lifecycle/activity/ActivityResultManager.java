package com.hexin.znkflib.support.lifecycle.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.SparseArray;

import com.hexin.znkflib.support.lifecycle.LifecycleDetector;
import com.hexin.znkflib.support.lifecycle.LifecycleManagerFragment;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * desc: onActivityResult回调分发管理类
 *
 * @author sunxianglei@myhexin.com
 * @date 2019/11/1.
 */

public class ActivityResultManager implements OnActivityResult{

    private static final int REQUESTCODE_START = 2000;

    private static int requestCode = REQUESTCODE_START;
    private static Map<String, ActivityResultManager> activityResultManagerMap = new WeakHashMap<>();
    private Activity activity;
    private SparseArray<IActivityResultCall> mActivityResultCalls = new SparseArray<>();

    private ActivityResultManager(Activity activity){
        this.activity = activity;
        LifecycleDetector.getInstance().observeActivityResult(activity, this);
    }

    public static ActivityResultManager get(Activity activity){
        /**
         * 以activity内存地址作为key保存permissionManager实例。
         * 这样做的理由是避免一个activity中创造多个实例造成内存浪费。
         * 在map中找不到内存地址对应的实例后直接清空map，否则map会越积越大，
         * 因为同一时间不会出现两个activity同时去申请权限的情况。
         */
        if(activityResultManagerMap.containsKey(activity.toString())){
            return activityResultManagerMap.get(activity.toString());
        } else {
            activityResultManagerMap.clear();
        }
        ActivityResultManager manager = new ActivityResultManager(activity);
        activityResultManagerMap.put(activity.toString(), manager);
        return manager;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IActivityResultCall resultCall = mActivityResultCalls.get(requestCode);
        if(resultCall != null){
            boolean isOk = false;
            if(resultCode == Activity.RESULT_OK){
                isOk = true;
            }
            resultCall.callResult(isOk, data);
        }
    }

    public void startActivityResult(Intent intent, IActivityResultCall activityResultCall){
        LifecycleManagerFragment lifecycleFragment = (LifecycleManagerFragment) activity.getFragmentManager().findFragmentByTag(LifecycleDetector.FRAGMENT_TAG);
        requestCode++;
        mActivityResultCalls.put(requestCode, activityResultCall);
        if(lifecycleFragment != null){
            lifecycleFragment.startActivityForResult(intent, requestCode);
        }
    }

}
