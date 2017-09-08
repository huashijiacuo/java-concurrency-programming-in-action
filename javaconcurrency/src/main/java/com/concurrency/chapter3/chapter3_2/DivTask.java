package com.concurrency.chapter3.chapter3_2;

/**
 * Created by shun on 2017/9/7.
 */
public class DivTask implements Runnable {
    int a, b;
    public DivTask(int a, int b) {
        this.a = a;
        this.b = b;
    }
    @Override
    public void run() {
        double re = a / b;
        System.out.println(re);
    }
}
