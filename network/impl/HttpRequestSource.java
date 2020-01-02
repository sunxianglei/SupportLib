package com.hexin.znkflib.support.network.impl;

import android.text.TextUtils;

import com.hexin.znkflib.support.network.ThreadPools;
import com.hexin.znkflib.support.network.api.IRequestSource;
import com.hexin.znkflib.support.network.api.RequestInfo;
import com.hexin.znkflib.support.network.api.ResultCallBack;

/**
 * desc: IRequestSource 默认实现
 * @author sunxianglei@myhexin.com
 * @date 2019/8/6.
 */

public class HttpRequestSource implements IRequestSource {

    @Override
    public void async(RequestInfo request, ResultCallBack callBack) {
        ThreadPools.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                String result = HttpTool.stringResponse(request.url, request.params, request.method);
                if(TextUtils.isEmpty(result)){
                    callBack.fail("请求失败");
                }else {
                    callBack.success(result);
                }
            }
        });
    }

    @Override
    public String sync(RequestInfo request) {
        return HttpTool.stringResponse(request.url, request.params, request.method);
    }
}
