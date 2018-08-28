package com.cg.concurrent_packge;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试shutdown以及shutdownNow中断效果
 */
public class ThreadPoolDemo03 {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Runnable task01 = () -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        pool.execute(task01);
//        pool.shutdown();
        pool.shutdownNow();
    }
}
