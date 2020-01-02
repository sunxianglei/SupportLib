package com.hexin.znkflib.support.lifecycle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * desc: 生命周期分发实现类
 * @author sunxianglei@myhexin.com
 * @date 2019/4/19.
 */

public class ActivityFragmentLifecycle implements Lifecycle{

    private List<LifecycleListener> mLifecycleListeners = new ArrayList<>();

    @Override
    public void addListener(LifecycleListener listener) {
        mLifecycleListeners.add(listener);
    }

    @Override
    public void removeListener(LifecycleListener listener) {
        if(listener == null){
            mLifecycleListeners.clear();
            return ;
        }
        mLifecycleListeners.remove(listener);
    }

    public void onCreate(@Nullable Bundle savedInstanceState){
        for (LifecycleListener listener: mLifecycleListeners){
            listener.onCreate(savedInstanceState);
        }
    }

    public void onStart(){
        for (LifecycleListener listener: mLifecycleListeners){
            listener.onStart();
        }
    }

    public void onStop(){
        for (LifecycleListener listener: mLifecycleListeners){
            listener.onStop();
        }
    }


    public void onDestroy(){
        for (LifecycleListener listener: mLifecycleListeners){
            listener.onDestroy();
        }
    }

}
