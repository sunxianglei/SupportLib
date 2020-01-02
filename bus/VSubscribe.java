package com.hexin.znkflib.support.bus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * desc: 订阅方法的注解
 * @author sunxianglei@myhexin.com
 * @date 2019/7/4.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface VSubscribe {

    /**
     * 指定方法字面量，可以让Bus只发给定义这样字面量方法
     * @return
     */
    String specifyMethod() default "";

    boolean sticky() default false;

}
