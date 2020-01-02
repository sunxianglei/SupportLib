package com.hexin.znkflib.support.reactive;

import com.hexin.znkflib.support.network.GlobalHandler;

/**
 * desc:
 *  TransformObservable是在调用 map 操作符后产生的Observable
 *  用于数据转换场景，并且会回调到主线程
 * @author sunxianglei@myhexin.com
 * @date 2019/8/16.
 */

public final class TransformObservable<T,R> extends Observable<R> {

    private Observable<T> source;
    private Function<T,R> function;

    public TransformObservable(Observable<T> source, Function<T,R> function){
        this.source = source;
        this.function = function;
    }

    @Override
    public void subscribe(Observer<R> observer) {
        source.subscribe(new TransformObserver<>(observer, function));
    }

    public static final class TransformObserver<T,R> implements Observer<T> {

        private Observer<R> observer;
        private Function<T,R> function;

        public TransformObserver(Observer<R> observer, Function<T,R> function){
            this.observer = observer;
            this.function = function;
        }

        @Override
        public void success(T data) {
            R result = function.apply(data);
            GlobalHandler.post(() -> observer.success(result));
        }

        @Override
        public void fail(String msg) {
            GlobalHandler.post(() -> observer.fail(msg));
        }
    }
}
