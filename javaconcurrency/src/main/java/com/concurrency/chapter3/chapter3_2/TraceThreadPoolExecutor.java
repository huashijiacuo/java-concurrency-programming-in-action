package com.concurrency.chapter3.chapter3_2;

import java.util.concurrent.*;

/**
 * Created by shun on 2017/9/7.
 */
public class TraceThreadPoolExecutor extends ThreadPoolExecutor {
    public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
                                   long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void execute(Runnable task) {
        super.execute(Wrap(task, clientTrace(), Thread.currentThread().getName()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(Wrap(task, clientTrace(), Thread.currentThread().getName()));
    }

    private Exception clientTrace() {
        return new Exception("client stack trace");
    }

    private Runnable Wrap(final Runnable task, final Exception clientStack, String clientThreadName) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                } catch (Exception e) {
                    clientStack.printStackTrace();
                    throw e;
                }
            }
        };
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pools = new TraceThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS,
               new SynchronousQueue<Runnable>());
        /**
         * 错误的堆栈信息可以看到是在哪里提交的
         */
        for (int i = 0; i < 5; i++) {
            pools.execute(new DivTask(100, i));
        }
        Thread.sleep(5000);
        pools.shutdown();
    }
}
