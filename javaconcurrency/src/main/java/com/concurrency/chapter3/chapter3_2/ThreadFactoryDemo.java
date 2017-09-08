package com.concurrency.chapter3.chapter3_2;

import java.util.concurrent.*;

/**
 * Created by shun on 2017/9/7.
 */
public class ThreadFactoryDemo {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolDemo.MyTask task = new ThreadPoolDemo.MyTask();
        ExecutorService es = new ThreadPoolExecutor(5, 10, 10L, TimeUnit.SECONDS,
                /*new SynchronousQueue<Runnable>(),*/
                new ArrayBlockingQueue<Runnable>(10),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setDaemon(true);
                        System.out.println("creat " + r);
                        return t;
                    }
                });
        for (int i = 0; i < 50; i++) {
            es.submit(task);
        }
        Thread.sleep(1000);

    }
}
