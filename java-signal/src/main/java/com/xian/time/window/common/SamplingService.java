package com.xian.time.window.common;


import com.xian.time.window.SamplData;
import com.xian.time.window.thread.DefaultNamedThreadFactory;
import com.xian.time.window.thread.RunnableWithExceptionProtection;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @Description
 * @Author: xlr
 * @Date: Created in 10:50 下午 2020/12/31
 */
public class SamplingService{

    private volatile boolean on = false;
    private volatile AtomicInteger samplingFactorHolder = new AtomicInteger(0);
    private volatile ScheduledFuture<?> scheduledFuture;

    private volatile Integer SAMPLE_N_PER_3_SECS = 3;
    private static SamplingService INSTANCE;
    static {
        INSTANCE = new SamplingService();
    }

    public void boot() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
        if (SAMPLE_N_PER_3_SECS > 0) {
            on = true;
            this.resetSamplingFactor();
            SamplData data = new SamplData();
            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(new DefaultNamedThreadFactory("SamplingService"));
            scheduledFuture = service.scheduleAtFixedRate(new RunnableWithExceptionProtection<SamplData>( new Runnable() {

                public void run() {
                    resetSamplingFactor();
                }
            }, new RunnableWithExceptionProtection.CallbackWhenException<SamplData>() {

                public void handle(Throwable t,SamplData samplData) {
                    System.out.println("unexpected exception."+ t.toString());
                }
            },data), 0, 3, TimeUnit.SECONDS);
            System.out.println("sampling mechanism started. Sample {} traces in 3 seconds."+ SAMPLE_N_PER_3_SECS);
        }
    }


    public boolean trySampling() {
        if (on) {
            int factor = samplingFactorHolder.get();
            if (factor < SAMPLE_N_PER_3_SECS) {
                boolean success = samplingFactorHolder.compareAndSet(factor, factor + 1);
                return success;
            } else {
                return false;
            }
        }
        return true;
    }

    public void forceSampled() {
        if (on) {
            samplingFactorHolder.incrementAndGet();
        }
    }

    public static SamplingService getInstance(){
        return INSTANCE;
    }

    private void resetSamplingFactor() {
        samplingFactorHolder = new AtomicInteger(0);
    }
}
