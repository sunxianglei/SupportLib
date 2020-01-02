package com.hexin.znkflib.support.bus;

import java.lang.reflect.Method;

/**
 * desc: 记录解析注解方法后保存的信息
 * @author sunxianglei@myhexin.com
 * @date 2019/7/4.
 */

public class VSubscriberMethod {
    final Method method;
    final Class<?> eventType;
    final Class<?> subscriberClass;
    final String specifyLiteral;
    final boolean sticky;

    public VSubscriberMethod(Method method, Class<?> eventType, Class<?> subscriberClass, String specifyLiteral, boolean sticky){
        this.method = method;
        this.eventType = eventType;
        this.subscriberClass = subscriberClass;
        this.specifyLiteral = specifyLiteral;
        this.sticky = sticky;
    }

}
