package com.concurrency.chapter3.chapter3_1;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shun on 2017/9/6.
 */
public class IntLock implements Runnable {
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();

    int lock;
    /**
     * 控制加锁顺序，方便构造死锁
     */
    public  IntLock(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                lock1.lockInterruptibly();
                System.out.println("线程" + Thread.currentThread().getId() + ":持有锁lock1");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                }
                lock2.lockInterruptibly();
                System.out.println("线程" + Thread.currentThread().getId() + ":持有锁lock2");
            } else {
                lock2.lockInterruptibly();
                System.out.println("线程" + Thread.currentThread().getId() + ":持有锁lock2");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                }
                lock1.lockInterruptibly();
                System.out.println("线程" + Thread.currentThread().getId() + ":持有锁lock1");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
                System.out.println("线程" + Thread.currentThread().getId() + ":释放锁lock1");
            }
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
                System.out.println("线程" + Thread.currentThread().getId() + ":释放锁lock2");
            }
            System.out.println(Thread.currentThread().getId() + ":线程退出");
        }
    }

    public static void main(String [] args) throws InterruptedException{
        IntLock r1 = new IntLock(1);
        Thread t1 = new Thread(r1);

        IntLock r2 = new IntLock(2);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        Thread.sleep(1000);
        t2.interrupt();
        System.out.println("线程" + t2.getId() + ":中断");
    }
}
