package com.hexin.znkflib.support.lifecycle.permission;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;

import com.hexin.znkflib.support.lifecycle.LifecycleDetector;
import com.hexin.znkflib.support.lifecycle.LifecycleManagerFragment;
import com.hexin.znkflib.util.AudioPermissionUtil;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * desc: 权限申请管理类
 * @author sunxianglei@myhexin.com
 * @date 2019/10/31.
 */

public class PermissionManager implements OnRequestPermissionResult {

    private static final int REQUESTCODE_START = 1000;
    /**
     * requestCode 识别匹配的权限，从 1000 起始
     */
    private static int requestCode = REQUESTCODE_START;
    private static Map<String, PermissionManager> permissionManagerMap = new WeakHashMap<>();
    private SparseArray<IPermissionResult> mPermissionResults = new SparseArray<>();
    private Activity activity;

    private PermissionManager(Activity activity){
        this.activity = activity;
        LifecycleDetector.getInstance().observePermission(activity, this);
    }

    public static PermissionManager get(Activity activity){
        /**
         * 以activity内存地址作为key保存permissionManager实例。
         * 这样做的理由是避免一个activity中创造多个实例造成内存浪费。
         * 在map中找不到内存地址对应的实例后直接清空map，否则map会越积越大，
         * 因为同一时间不会出现两个activity同时去申请权限的情况。
         */
        if(permissionManagerMap.containsKey(activity.toString())){
            return permissionManagerMap.get(activity.toString());
        } else {
            permissionManagerMap.clear();
        }
        PermissionManager manager = new PermissionManager(activity);
        permissionManagerMap.put(activity.toString(), manager);
        return manager;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(activity == null){
            return ;
        }
        IPermissionResult permissionResult = mPermissionResults.get(requestCode);
        if(permissionResult != null){
            // 有任意一个权限被拒绝都不行
            boolean isGrant = true;
            for (int granted : grantResults) {
                if (granted == PackageManager.PERMISSION_DENIED) {
                    isGrant = false;
                    break;
                }
            }
            if(isGrant){
                permissionResult.granted();
            }else {
                // 检测用户是否永久拒绝了
                LifecycleManagerFragment lifecycleFragment = (LifecycleManagerFragment) activity.getFragmentManager().findFragmentByTag(LifecycleDetector.FRAGMENT_TAG);
                for (String permission : permissions){
                    if(!lifecycleFragment.shouldShowRequestPermissionRationale(permission)){
                        permissionResult.deny(true);
                        return ;
                    }
                }
                permissionResult.deny(false);
            }
            mPermissionResults.remove(requestCode);
        }
    }

    /**
     * 权限申请调此方法
     * @param permission
     * @param permissionResult
     */
    public void requestPermissions(String permission, IPermissionResult permissionResult){
        requestPermissions(new String[]{permission}, permissionResult);
    }

    /**
     * 权限申请调此方法
     * @param permissions
     * @param permissionResult
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissions(String[] permissions, IPermissionResult permissionResult){
        if(permissions == null || permissions.length == 0 || activity == null){
            return ;
        }
        LifecycleManagerFragment lifecycleFragment = (LifecycleManagerFragment) activity.getFragmentManager().findFragmentByTag(LifecycleDetector.FRAGMENT_TAG);
        requestCode++;
        mPermissionResults.put(requestCode, permissionResult);
        if(lifecycleFragment != null){
            lifecycleFragment.requestPermissions(permissions, requestCode);
        }
    }

    /**
     * 检查权限
     * @param context
     * @param permission
     * @return
     */
    public static boolean checkSelfPermission(Context context, String permission){
        // 录音权限特殊处理
        if(permission.equals(Manifest.permission.RECORD_AUDIO)){
            return AudioPermissionUtil.checkAudioPermission(context);
        }
        if(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }

}
