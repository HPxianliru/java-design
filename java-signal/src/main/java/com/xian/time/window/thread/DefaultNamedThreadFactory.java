package com.xian.time.window.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 10:50 下午 2020/12/31
 */
public class DefaultNamedThreadFactory implements ThreadFactory {
    private static final AtomicInteger BOOT_SERVICE_SEQ = new AtomicInteger(0);
    private final AtomicInteger threadSeq = new AtomicInteger(0);
    private final String namePrefix;
    public DefaultNamedThreadFactory(String name) {
        namePrefix = "time_window-" + BOOT_SERVICE_SEQ.incrementAndGet() + "-" + name + "-";
    }


    public Thread newThread(Runnable r) {
        Thread t = new Thread(r,namePrefix + threadSeq.getAndIncrement());
        t.setDaemon(true);
        return t;
    }
}
