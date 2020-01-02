
package com.hexin.znkflib.support.network;

import android.util.Log;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 描述：程序使用的线程池,程序中使用的所有线程都应该通过这个线程池来执行
 * 
 * @author zhanglei@myhexin.com
 */
public class ThreadPools {

    private static final String                TAG                       = "HexinThreadPool";
    private static final String                THEADPOOL_NAME = "pool_foreground";
    /**
     * 线程池中线程的最大个数，作为SDK从不应该耗费太多资源考虑，5个应该能满足需求
     */
    private static final int                   THREAD_POOL_SIZE          = 5;
    private static ScheduledThreadPoolExecutor mScheduledExecutor;

    /**
     * 得到线程池实例
     */
    public synchronized static ScheduledThreadPoolExecutor getThreadPool() {
        if (mScheduledExecutor == null || mScheduledExecutor.isShutdown()) {
            mScheduledExecutor = new ScheduledThreadPoolExecutor(THREAD_POOL_SIZE, new ThreadFactory() {

                private final AtomicInteger mCount = new AtomicInteger(1);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, THEADPOOL_NAME + "-child-" + mCount.getAndIncrement());
                }
            }, new ThreadPoolExecutor.DiscardPolicy());
            mScheduledExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
            mScheduledExecutor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
        }
        mScheduledExecutor.purge();
        return mScheduledExecutor;

    }

    /**
     * 停止线程池中的所有线程，并销毁线程池
     */
    public synchronized static void destroyThreadPool() {
        if (mScheduledExecutor != null && !mScheduledExecutor.isShutdown()) {
            mScheduledExecutor.shutdownNow();
            Log.d(TAG, "HexinThreadPool_destroyThreadPool");
        }
        mScheduledExecutor = null;
    }
    
    /**
     * 停止一个任务
     */
    public static void cancelTaskFuture(ScheduledFuture<?> taskFuture,boolean mayInterruptIfRunning) {
        if (taskFuture != null) {
            boolean cancel = taskFuture.cancel(mayInterruptIfRunning);
            Log.d(TAG, "HexinThreadPool_cancelTaskFuture "+taskFuture+",cancel="+cancel);
        }
    }

}
