package com.concurrency.chapter3.chapter3_2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shun on 2017/9/7.
 */
public class ThreadPoolDemo {
    public static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ": Thread ID:" + Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyTask task = new MyTask();
        ExecutorService es  = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            es.submit(task);
        }
        Thread.sleep(5000);
        es.shutdown();
    }
}
