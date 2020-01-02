package com.hexin.znkflib.support.network.api;

import com.hexin.znkflib.support.reactive.Observable;
import com.hexin.znkflib.support.reactive.Observer;

/**
 * desc:
 *  FineObservable只允许被FineHttp创建且泛型参数类型为String，
 *  因为目前FineHttp只支持String类型的请求结果
 * @author sunxianglei@myhexin.com
 * @date 2019/8/16.
 */

public final class FineObservable<T> extends Observable<T> {
    
    private Call call;
    
    public FineObservable(Call call){
        this.call = call;
    }
    
    @Override
    public void subscribe(Observer<T> observer) {
        call.enqueue(new ResultCallBack() {
            @Override
            public void success(String result) {
                observer.success((T)result);
            }

            @Override
            public void fail(String message) {
                observer.fail(message);
            }
        });
    }


}
