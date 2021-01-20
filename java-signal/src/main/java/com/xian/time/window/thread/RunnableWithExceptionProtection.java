package com.xian.time.window.thread;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 10:50 下午 2020/12/31
 */
public class RunnableWithExceptionProtection<T> implements Runnable {
    private Runnable run;
    private CallbackWhenException callback;
    private T data;

    public RunnableWithExceptionProtection(Runnable run, CallbackWhenException callback,T data) {
        this.run = run;
        this.callback = callback;
        this.data = data;
    }

    public void run() {
        try {
            run.run();
        } catch (Throwable t) {
            callback.handle(t,data);
        }
    }

    public interface CallbackWhenException<T> {
        void handle(Throwable t,T data);
    }
}
