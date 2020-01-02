package com.hexin.znkflib.support.bus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * desc: 事件总线订阅分发类
 * @author sunxianglei@myhexin.com
 * @date 2019/7/4.
 */

public class VoiceAssistantBus {
    private static final String TAG = "VoiceAssistantBus";

    private VSubscriberMethodFinder mSubscriberMethodFinder = new VSubscriberMethodFinder();
    private Map<Class<?>, List<VSubscription>> mSubscriptionsByEventType = new HashMap<>();
    private Map<Object, List<Class<?>>> typesBySubsciber = new HashMap<>();
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private final Map<Class<?>, Object> stickyEvents = new ConcurrentHashMap<>();
    private final Map<Class<?>, Object> specifyParams = new ConcurrentHashMap<>();

    public static VoiceAssistantBus getDefault(){
        return Holder.instance;
    }

    private static final class Holder {
        private static final VoiceAssistantBus instance = new VoiceAssistantBus();
    }

    /**
     * 注册订阅对象
     */
    public void register(Object subcriber) {
        Class<?> subscriberClass = subcriber.getClass();
        List<VSubscriberMethod> subscriberMethods = mSubscriberMethodFinder.findSubscriberMethods(subscriberClass);
        if(subscriberMethods != null){
            subscribe(subcriber, subscriberMethods);
        }else {
            throw new VoiceAssistantException("your subscriber have not method by @Subscribe modify, so can't register");
        }
    }

    private void subscribe(Object subscriber, List<VSubscriberMethod> subscriberMethods){

        for(int i=0;i<subscriberMethods.size();i++){
            Class<?> eventClass = subscriberMethods.get(i).eventType;
            List<VSubscription> subscriptions = mSubscriptionsByEventType.get(eventClass);
            if(subscriptions == null){
                subscriptions = new ArrayList<>();
            }
            VSubscription subscription = new VSubscription(subscriber, subscriberMethods.get(i));
            subscriptions.add(subscription);
            mSubscriptionsByEventType.put(eventClass, subscriptions);

            List<Class<?>> eventClasses = typesBySubsciber.get(subscriber);
            if(eventClasses == null){
                eventClasses = new ArrayList<>();
                typesBySubsciber.put(subscriber, eventClasses);
            }
            eventClasses.add(eventClass);

            if(subscriberMethods.get(i).sticky){
                Object event = stickyEvents.get(eventClass);
                Object specifyParam = specifyParams.get(eventClass);
                if(event != null) {
                    postToMainThreadSticky(event, subscription, specifyParam);
                }
            }
        }

    }

    /**
     * 注册过的对象一定要解注册，否则会造成内存泄漏
     * @param subscriber
     */
    public void unregister(Object subscriber){
        List<Class<?>> eventClasses = typesBySubsciber.get(subscriber);
        if(eventClasses == null){
            throw new VoiceAssistantException("VoiceAssistantBus have not register this subscriber which Class is " + subscriber.getClass());
        }
        for(Class<?> eventClass : eventClasses){
            List<VSubscription> subscriptions = mSubscriptionsByEventType.get(eventClass);
            if(subscriptions != null){
                int size = subscriptions.size();
                for(int i=0;i<size;i++){
                    VSubscription subscription = subscriptions.get(i);
                    if(subscription.subscriber == subscriber){
                        subscriptions.remove(i);
                        i--;
                        size--;
                    }
                }
            }
        }
        typesBySubsciber.remove(subscriber);
    }

    /**
     * 相较于 EventBus，这里多了可变参数，可用来发送到指定的类或方法等。
     * 若可变参数是空的，则直接调用Event事件类型对应的所有方法
     * 若可变参数不为空时支持两种类型：
     *   ① 参数类型是Class，则事件只会分发到这些类中
     *   ② 参数类型是String，则事件只会分发到指定字面量的方法上
     * @param event
     * @param args
     * @param <T>
     */
    public <T> void post(Object event, T... args) {
        Class<?> eventClass = event.getClass();
        List<VSubscription> subscriptions = mSubscriptionsByEventType.get(eventClass);
        if(subscriptions == null){
            return ;
        }
        for(VSubscription subscription : subscriptions){
            postToMainThread(event, subscription, args);
        }
    }

    /**
     * 黏性事件
     * @param event
     * @param args
     * @param <T>
     */
    public <T> void postSticky(Object event, T... args){
        synchronized (stickyEvents){
            stickyEvents.put(event.getClass(), event);
            if(args != null && args.length > 0){
                specifyParams.put(event.getClass(), args);
            }
        }
        post(event, args);
    }

    public <T> void removeStickyEvent(Class<T> eventClass){
        synchronized (stickyEvents){
            stickyEvents.remove(eventClass);
            specifyParams.remove(eventClass);
        }
    }
    public void removeAllStickyEvents() {
        synchronized (stickyEvents) {
            stickyEvents.clear();
            specifyParams.clear();
        }
    }


    private <T> void postToMainThreadSticky(final Object event, final VSubscription subscription, final T... args){
        T arg = args[0];
        if(arg instanceof String[]){
            postToMainThread(event, subscription, (String[])arg);
        } else if(arg instanceof Class<?>[]){
            postToMainThread(event, subscription, (Class<?>[])arg);
        } else {
            postToMainThread(event, subscription);
        }
    }

    private <T> void postToMainThread(final Object event, final VSubscription subscription, final T... args){
        if(Thread.currentThread() != Looper.getMainLooper().getThread()){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    postToSubscription(event ,subscription, args);
                }
            });
        }else {
            postToSubscription(event, subscription, args);
        }
    }

    private <T> void postToSubscription(Object event, VSubscription subscription, T... args){
        if(args == null || args.length == 0){
            invokeSubscriberMethod(subscription, event);
            return ;
        }
        for(int i=0;i<args.length;i++){
            T arg = args[i];
            if(arg instanceof Class<?>){
                invokeSubscriberMethodByClass((Class<?>)arg, subscription, event);
            }
            if(arg instanceof String){
                invokeSubscriberMethodByLiteral((String)arg, subscription, event);
            }
        }
    }

    private void invokeSubscriberMethodByLiteral(String specifyMethod, VSubscription subscription, Object event){
        if(specifyMethod.equals(subscription.subscriberMethod.specifyLiteral)){
            invokeSubscriberMethod(subscription, event);
        }
    }

    private void invokeSubscriberMethodByClass(Class<?> subscriberClass, VSubscription subscription, Object event){
        if(subscriberClass == subscription.subscriberMethod.subscriberClass){
            invokeSubscriberMethod(subscription, event);
        }
    }

    private void invokeSubscriberMethod(VSubscription subscription, Object event){
        try {
            subscription.subscriberMethod.method.invoke(subscription.subscriber, event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
