package com.concurrency.chapter2;

/**
 * Created by shun on 2017/9/6.
 */
public class BadLockOnIneger implements Runnable {
    public static Integer i = 0;
    static BadLockOnIneger instance = new BadLockOnIneger();

    @Override
    public void run() {
        for (int j = 0; j < 1000; j++) {
            synchronized (this) {
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
