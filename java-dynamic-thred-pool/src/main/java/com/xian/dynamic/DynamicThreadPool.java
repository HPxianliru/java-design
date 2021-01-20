package com.xian.dynamic;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 9:52 下午 2021/1/14
 */
public class DynamicThreadPool {

    /**
     * 默认核心线程数：5
     */
    private static final int CORE_POOL_SIZE = 5;
    /**
     * 默认最大线程数：10
     */
    private static final int MAX_POOL_SIZE = 10;
    /**
     * 默认队列容量：100
     */
    private static final int QUEUE_CAPACITY = 100;
    /**
     * 默认额外空闲线程存活时间
     */
    private static final Long KEEP_ALIVE_TIME = 10L;


    /**
     * 线程池
     */
    private ThreadPoolExecutor threadPool ;

    public ThreadPoolExecutor getThreadPool(){
        return this.threadPool;
    }

    //构造默认参数的线程池
    private DynamicThreadPool(){
        this.threadPool = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new FixArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }




    /**
     * Lazy load单例线程池
     * 利用JVM底层保证单例
     */
    private static class Singleton{
        private static DynamicThreadPool instance;

        static{
            instance = new DynamicThreadPool();
        }

        public static DynamicThreadPool getInstance(){
            return instance;
        }
    }

    /**
     * 单例
     * @return
     */
    public static DynamicThreadPool getDynamicThreadPool() {
        return Singleton.getInstance();
    }

    /**
     * 初始化的便捷方法
     */
    public static void init(){
        getDynamicThreadPool();
    }

    /**
     * 动态设置核心线程数
     */
    public Boolean setCorePoolSize(int corePoolSize){
        try{
            //动态设置核心线程数
            this.getThreadPool()
                    .setCorePoolSize(corePoolSize);
            System.out.println("设置核心线程数成功,设置之后的核心线程数： {}"+this.getThreadPool().getCorePoolSize());
            return  Boolean.TRUE;
        }catch (Exception e){
            //设置失败
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    /**
     *
     * 设置最大线程数
     */
    public Boolean setMaximumPoolSize(int maximumPoolSize){
        try{
            //动态设置最大线程数
            this.getThreadPool()
                    .setMaximumPoolSize(maximumPoolSize);
            System.out.println("设置核心线程数成功,设置之后的最大线程数： {}"+this.getThreadPool().getMaximumPoolSize());
            return  Boolean.TRUE;
        }catch (Exception e){
            //设置失败
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    /**
     *
     * 设置最大线程数
     */
    public Boolean setKeepAliveTime(
            long keepAliveTime,TimeUnit timeUnit){
        try{
            //动态设置空闲线程存活时间
            this.getThreadPool()
                    .setKeepAliveTime(keepAliveTime,timeUnit);

            System.out.println("设置线程活跃时间成功,设置之后的活跃时间： {}"+
                    this.getThreadPool().getKeepAliveTime(timeUnit));
            return  Boolean.TRUE;
        }catch (Exception e){
            //设置失败
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
    /**
     * 设置线程池的回绝策略
     */
    public Boolean setRejectHander(RejectedExecutionHandler
                                                   rejectedExecutionHandler){
        try{
            this.getThreadPool()
                    .setRejectedExecutionHandler(rejectedExecutionHandler);
            return Boolean.TRUE;
        }catch (Exception e){
            e.printStackTrace();;
            return Boolean.FALSE;
        }
    }

    /**
     * 获取回绝策略
     * @return
     */
    public RejectedExecutionHandler getRejectHander(){
        return this.getThreadPool().getRejectedExecutionHandler();
    }
}
