package com.hexin.znkflib.support.bus;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * desc: 解析注解方法
 * @author sunxianglei@myhexin.com
 * @date 2019/7/4.
 */

public class VSubscriberMethodFinder {
    private static final String TAG = "VSubscriberFinder";
    private static final String HEXIN_PACKAGE = "com.hexin";
    private static final int MODIFIERS_IGNORE = Modifier.ABSTRACT | Modifier.STATIC;

    Map<Class<?>, List<VSubscriberMethod>> METHOD_CACHE = new HashMap<>();

    List<VSubscriberMethod> findSubscriberMethods(Class<?> subscriberClass) {
        List<VSubscriberMethod> subscriberMethods = METHOD_CACHE.get(subscriberClass);
        if(subscriberMethods != null){
            return subscriberMethods;
        }
        // 寻找自身和父类的注解方法，只需要 com.hexin 包下的类。
        Class<?> clazz = subscriberClass;
        subscriberMethods = new ArrayList<>();
        while(clazz != null && clazz.getName().contains(HEXIN_PACKAGE)){
            subscriberMethods.addAll(findUsingReflection(clazz));
            clazz = clazz.getSuperclass();
        }
        METHOD_CACHE.put(subscriberClass, subscriberMethods);
        return subscriberMethods;
    }

    private List<VSubscriberMethod> findUsingReflection(Class<?> subscriberClass){
        List<VSubscriberMethod> subscriberMethods = new ArrayList<>();
        Method[] methods = subscriberClass.getDeclaredMethods();
        for(int i=0;i<methods.length;i++){
            Method method = methods[i];
            if((method.getModifiers() & Modifier.PUBLIC) != 0 && (method.getModifiers() & MODIFIERS_IGNORE) == 0){
                VSubscribe vSubscribe = method.getAnnotation(VSubscribe.class);
                if(vSubscribe != null){
                    Class<?>[] params = method.getParameterTypes();
                    if(params.length == 1){
                        Class<?> eventType = method.getParameterTypes()[0];
                        subscriberMethods.add(new VSubscriberMethod(method, eventType, subscriberClass,
                                vSubscribe.specifyMethod(), vSubscribe.sticky()));
                    }else {
                        throw new VoiceAssistantException("params can't more than one");
                    }
                }
            }
        }
        return subscriberMethods;
    }
}
