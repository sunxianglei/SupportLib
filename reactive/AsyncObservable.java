package com.hexin.znkflib.support.reactive;

import com.hexin.znkflib.support.network.ThreadPools;

/**
 * desc: 异步Observable，经过此Observable后线程切换到子线程
 * @author sunxianglei@myhexin.com
 * @date 2019/8/16.
 */

public class AsyncObservable<T> extends Observable<T> {

    private T data;

    public AsyncObservable(T data){
        this.data = data;
    }

    @Override
    public void subscribe(Observer<T> observer) {
        ThreadPools.getThreadPool().execute(() -> observer.success(data));
    }
}
