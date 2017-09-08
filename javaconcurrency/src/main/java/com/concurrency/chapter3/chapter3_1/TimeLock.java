package com.concurrency.chapter3.chapter3_1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shun on 2017/9/6.
 */
public class TimeLock implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                System.out.println("线程" + Thread.currentThread().getId() + ":获得锁，并睡眠6秒");
                Thread.sleep(6000);
            } else {
                System.out.println("线程" + Thread.currentThread().getId() +":Get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("线程" + Thread.currentThread().getId() +":中断");
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println("线程" + Thread.currentThread().getId() +":释放锁");
            }
        }
    }

    public static void main(String[] args) {
        TimeLock tl = new TimeLock();
        Thread t1 = new Thread(tl);
        Thread t2 = new Thread(tl);
        t1.start();
        t2.start();
    }
}
