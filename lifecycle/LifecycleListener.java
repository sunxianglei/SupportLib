package com.hexin.znkflib.support.lifecycle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * desc: Fragment 生命周期监听接口
 * @author sunxianglei@myhexin.com
 * @date 2019/4/19.
 */

public interface LifecycleListener {

    /**
     * Fragment call onCreate
     * @param savedInstanceState
     */
    void onCreate(@Nullable Bundle savedInstanceState);

    /**
     * Fragment call onStart
     */
    void onStart();

    /**
     * Fragment call onStop
     */
    void onStop();

    /**
     * Fragment call onDestroy
     */
    void onDestroy();
}
