package com.concurrency.chapter2;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by shun on 2017/9/6.
 */
public class ArrayListMultiThread {
    static ArrayList<Integer> arrayList = new ArrayList<Integer>(10);
    public static class AddThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 1000000; i++) {
                arrayList.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread());
        Thread t2 = new Thread(new AddThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("arrayList.size() = " + arrayList.size());
    }
}
