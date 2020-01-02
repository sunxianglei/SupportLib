package com.hexin.znkflib.support.network.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * desc: 保存请求所需的信息
 * @author sunxianglei@myhexin.com
 * @date 2019/8/8.
 */

public class RequestInfo {

    public static final String GET = "GET";
    public static final String POST = "POST";

    public final String url;
    public String method;
    public final Map<String, String> params;

    private RequestInfo(Builder builder){
        this.url = builder.url;
        this.method = builder.method;
        this.params = builder.params;
    }

    /**
     * get 请求方式拼接参数
     * @return
     */
    public String buildUrlParams(){
        StringBuffer request = new StringBuffer(url);
        try {
            if (params != null && !params.isEmpty()) {
                request.append("?");
                Iterator iter = params.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry) iter.next();
                    request.append(entry.getKey()).append("=")
                            .append(entry.getValue() != null ? URLEncoder.encode(entry.getValue(), "UTF-8") : "")
                            .append("&");
                }
                request.deleteCharAt(request.length() - 1);
            }
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return request.toString();
    }

    public static final class Builder{
        private String url;
        private Map<String, String> params = new HashMap<>(4);
        private String method = GET;

        public Builder url(String url){
            this.url = url;
            return this;
        }

        public Builder method(String method){
            this.method = method;
            return this;
        }

        public Builder putParam(String key, String value){
            params.put(key, value);
            return this;
        }

        public Builder putParam(Map<String,String> params){
            this.params = params;
            return this;
        }

        public RequestInfo build(){
            return new RequestInfo(this);
        }
    }

}
