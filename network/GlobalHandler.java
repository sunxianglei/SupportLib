package com.hexin.znkflib.support.network;


import android.os.Handler;
import android.os.Looper;

/**
 * desc: 全局handler，用于post到主线程的场景
 * @author sunxianglei@myhexin.com
 * @date 2019/8/15.
 */

public class GlobalHandler {

    public static Handler handler = new Handler(Looper.getMainLooper());

    public static void post(Runnable runnable){
        handler.post(runnable);
    }

    public static void postDelayed(Runnable runnable, long time){
        handler.postDelayed(runnable, time);
    }

}
