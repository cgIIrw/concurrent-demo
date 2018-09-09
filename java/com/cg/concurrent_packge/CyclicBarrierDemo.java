package com.cg.concurrent_packge;

import java.util.Random;
import java.util.concurrent.*;

/**
 * CyclicBarrier实例
 */
public class CyclicBarrierDemo {

    public static final int N = 10;
    public static CyclicBarrier cyclic = new CyclicBarrier(N, new CyclicBarrierTask(N));
    public static void main(String[] args) {

        ExecutorService pool = Executors.newFixedThreadPool(10,
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "AwaitTask");
                    }
                });

        for (int i = 0; i < N; ++i) {
            pool.submit(new AwaitTask(i));
        }

        pool.shutdown();
    }

    public static class AwaitTask implements Runnable {

        private int i;
        public AwaitTask(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                doWork();
                cyclic.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        void doWork() {
            try {
                Thread.sleep(Math.abs(new Random().nextInt() % 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + (i+1) + " ：结束");
        }
    }

    public static class CyclicBarrierTask implements Runnable {
        int N;

        public CyclicBarrierTask(int N) {
            this.N = N;
        }

        @Override
        public void run() {
            System.out.println(N + "个AwaitTask结束!");
        }
    }
}
