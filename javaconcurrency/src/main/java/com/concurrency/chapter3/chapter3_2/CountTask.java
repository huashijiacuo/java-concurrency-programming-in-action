package com.concurrency.chapter3.chapter3_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.*;

/**
 * Created by shun on 2017/9/8.
 */
public class CountTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 1000;
    private long start;
    private long end;

    public CountTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Long compute() {
        long sum = 0;
        boolean canCompute = (end -start) < THRESHOLD;
        if (canCompute) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
//            System.out.println("start = " + start +"; end = " + end + "; sum = " + sum);
        } else {
            // 分成100个小任务
            long step = (end - start) / 10;
            boolean lastFlag = false;
            ArrayList<CountTask> subTasks = new ArrayList<CountTask>();
            long pos = start;
            for (int i = 0; i < 100; i++) {
                long lastOne = pos + step;
                if (lastOne > end) {
                    lastOne = end;
                    lastFlag = true;
                }
                CountTask subTask = new CountTask(pos, lastOne);
                pos += step + 1;
                subTasks.add(subTask);
                subTask.fork();
                if (lastFlag)
                    break;
            }
            for ( CountTask task : subTasks) {
                sum += task.join();
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        long start = -2345678945L;
        long end = 2345678945L;
        long time = System.currentTimeMillis();
        testForkJoin(start, end);
//        testNoForkJoin(start, end);
        System.out.println("Time = " + (System.currentTimeMillis() - time));
    }

    public static void testForkJoin(long start, long end) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(start, end);
        ForkJoinTask<Long> resutl = forkJoinPool.submit(task);
        try {
            long res = resutl.get();
            System.out.println("sum = " + res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void testNoForkJoin(long start, long end) {
        long sum  = 0;
        for (long i = start; i <= end; i++) {
            sum += i;
        }
        System.out.println("sum = " + sum);
    }
}
