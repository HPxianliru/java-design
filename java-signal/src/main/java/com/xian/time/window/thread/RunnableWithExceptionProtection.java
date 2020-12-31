package com.xian.time.window.thread;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 10:50 下午 2020/12/31
 */
public class RunnableWithExceptionProtection implements Runnable {
    private Runnable run;
    private CallbackWhenException callback;

    public RunnableWithExceptionProtection(Runnable run, CallbackWhenException callback) {
        this.run = run;
        this.callback = callback;
    }

    public void run() {
        try {
            run.run();
        } catch (Throwable t) {
            callback.handle(t);
        }
    }

    public interface CallbackWhenException {
        void handle(Throwable t);
    }
}
