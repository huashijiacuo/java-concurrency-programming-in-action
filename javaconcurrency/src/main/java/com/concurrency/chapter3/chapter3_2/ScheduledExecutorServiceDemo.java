package com.concurrency.chapter3.chapter3_2;

import com.sun.org.omg.CORBA.ExceptionDescription;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by shun on 2017/9/7.
 */
public class ScheduledExecutorServiceDemo {
    private static int count = 0;
    public static void main(String[] args) throws Exception {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
        // 如果前面的任务没有完成，则调度也不会启动
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(8000);
                    System.out.println(System.currentTimeMillis() / 1000);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 2, TimeUnit.SECONDS);
    }
}
