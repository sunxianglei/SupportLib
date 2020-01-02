package com.hexin.znkflib.support.bus;

/**
 * desc: 保存订阅者和对应的注解方法
 * @author sunxianglei@myhexin.com
 * @date 2019/7/4.
 */

public class VSubscription {
    final Object subscriber;
    final VSubscriberMethod subscriberMethod;
    public VSubscription(Object subscriber, VSubscriberMethod subscriberMethod){
        this.subscriber = subscriber;
        this.subscriberMethod = subscriberMethod;
    }
}
