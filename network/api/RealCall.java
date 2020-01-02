package com.hexin.znkflib.support.network.api;

import com.hexin.znkflib.support.network.GlobalHandler;

/**
 * desc: Call接口的实现类
 * @author sunxianglei@myhexin.com
 * @date 2019/8/8.
 */

final class RealCall implements Call {

    private IRequestSource source;
    private RequestInfo request;
    private boolean changeToMainThread;

    public RealCall(IRequestSource source, RequestInfo request, boolean changeToMainThread){
        this.source = source;
        this.request = request;
        this.changeToMainThread = changeToMainThread;
    }

    @Override
    public void enqueue(final ResultCallBack callBack) {
        source.async(request, new ResultCallBack() {
            @Override
            public void success(final String result) {
                if(changeToMainThread){
                    GlobalHandler.post(() -> callBack.success(result));
                }else {
                    callBack.success(result);
                }
            }

            @Override
            public void fail(final String message) {
                if(changeToMainThread){
                    GlobalHandler.post(() -> callBack.fail(message));
                }else{
                    callBack.fail(message);
                }
            }
        });
    }

    @Override
    public String execute() {
        return source.sync(request);
    }

}
