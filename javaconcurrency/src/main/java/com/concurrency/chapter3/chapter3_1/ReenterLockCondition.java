package com.concurrency.chapter3.chapter3_1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shun on 2017/9/6.
 */
public class ReenterLockCondition implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println("Thread " + Thread.currentThread().getName() + "获取锁成功，并等待唤醒");
            condition.await();
            System.out.println("Thread is going on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLockCondition reenterLockCondition = new ReenterLockCondition();
        Thread thread = new Thread(reenterLockCondition);
        thread.start();
        Thread.sleep(500);
        lock.lock();
        System.out.println("Thread " + Thread.currentThread().getName() + "：获得锁，唤醒等待线程");
        condition.signal();
        lock.unlock();
        System.out.println("Thread " + Thread.currentThread().getName() + ": 释放锁");
    }
}
