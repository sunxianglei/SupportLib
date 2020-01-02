package com.hexin.znkflib.support.reactive;

/**
 * desc: Observable基类，子类必须实现subscribe方法订阅观察者，可以拓展操作符
 * @author sunxianglei@myhexin.com
 * @date 2019/8/16.
 */

public abstract class Observable<T> {

    public static <T> Observable<T> from(T data){
        return new AsyncObservable<>(data);
    }

    public <R> Observable<R> map(Function<T, R> function){
        return new TransformObservable<>(this, function);
    }

    public void subscribe(SimpleObserver<T> observer){
        subscribe(new Observer<T>() {
            @Override
            public void success(T data) {
                observer.success(data);
            }

            @Override
            public void fail(String msg) {
                // fail will not be processed
            }
        });
    }

    /**
     * 订阅观察者
     * @param observer
     */
    public abstract void subscribe(Observer<T> observer);

}
