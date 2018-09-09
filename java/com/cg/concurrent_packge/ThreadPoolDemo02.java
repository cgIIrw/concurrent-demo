package com.cg.concurrent_packge;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * 本实例用来研究线程池的ctl以及锁状态，线程池状态
 */
public class ThreadPoolDemo02 {

    // 线程任务
    public static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()
                    + ":Thread ID" + Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadPoolDemo.MyTask task = new ThreadPoolDemo.MyTask();
        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            es.submit(task);
        }

        final int COUNT_BITS = Integer.SIZE - 3;
        System.out.println(Integer.toBinaryString(-1 << Integer.SIZE - 3));
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString((1 << COUNT_BITS) - 1));

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                LockSupport.park();
            }
        });

        // 没有启动的线程，他的中断标志不会因为调用了interrupt方法而改变
        t.interrupt();
        t.start();
        System.out.println(t.isInterrupted());
    }
}
