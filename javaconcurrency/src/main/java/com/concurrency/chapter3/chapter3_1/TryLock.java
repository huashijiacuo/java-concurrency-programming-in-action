package com.concurrency.chapter3.chapter3_1;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shun on 2017/9/6.
 */
public class TryLock implements Runnable {
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    int lock;
    public TryLock(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        if (lock == 1){
            while (true) {
                if (lock1.tryLock()) {
                    try {
                        try {
                            System.out.println(Thread.currentThread().getId() + ":获取到锁1，睡眠500ms");
                            Thread.sleep(500);
                        } catch (InterruptedException e) {

                        }
                        if (lock2.tryLock()) {
                            try {
                                System.out.println(Thread.currentThread().getId() + ":获取到锁2");
                                System.out.println(Thread.currentThread().getId() + ":My Job done");
                                return;
                            } finally {
                                System.out.println(Thread.currentThread().getId() + ":释放锁2");
                                lock2.unlock();
                            }
                        }
                    } finally {
                        System.out.println(Thread.currentThread().getId() + ":释放锁1");
                        lock1.unlock();
                    }
                }
            }
        } else {
            while (true) {
                if (lock2.tryLock()) {
                    try {
                        try {
                            System.out.println(Thread.currentThread().getId() + ":获取到锁2，睡眠500ms");
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }
                        if (lock1.tryLock()) {
                            try {
                                System.out.println(Thread.currentThread().getId() + ":获取到锁1");
                                System.out.println(Thread.currentThread().getId() + ":My Job done");
                                return;
                            } finally {
                                System.out.println(Thread.currentThread().getId() + ":释放锁1");
                                lock1.unlock();
                            }
                        }
                    } finally {
                        System.out.println(Thread.currentThread().getId() + ":释放锁2");
                        lock2.unlock();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        TryLock r1 = new TryLock(1);
        TryLock r2 = new TryLock(2);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
    }
}
