package com.concurrency.chapter3.chapter3_1;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by shun on 2017/9/6.
 */
public class ReadWriteLockDemo {
    private static Lock lock = new ReentrantLock();
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();
    private int value;

    public Object handleRead(Lock lock) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1000);
            return value;
        } finally {
            lock.unlock();
        }
    }

    public void handleWrite(Lock lock, int index) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1000);
            value = index;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final ReadWriteLockDemo demo = new ReadWriteLockDemo();
        Runnable readRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int readValue = (int) demo.handleRead(readLock);
//                    int readValue = (int) demo.handleRead(lock);
                    System.out.println(Thread.currentThread().getName() + ": readValue = " + readValue);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable writeRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int writeValue = new Random().nextInt();
                    demo.handleWrite(writeLock, writeValue);
//                    demo.handleWrite(lock, writeValue);
                    System.out.println(Thread.currentThread().getName() + ": writeValue = " + writeValue);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 18; i++) {
            new Thread(readRunnable, "Thread-read-" + i).start();
        }

        for (int i = 18; i < 20; i++) {
            new Thread(writeRunnable, "Thread-write-" + i).start();
        }
    }
}
