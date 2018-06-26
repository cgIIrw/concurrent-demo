package com.cg.concurrent_packge;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {

    public static void main(String[] args) throws InterruptedException {
        MyThreadLocal<Create50MB> local = new MyThreadLocal<>();
        System.out.println(local);


        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 1,
                TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 5; i++) {
            final int[] a = new int[1];
            final ThreadLocal[] finallocal = new MyThreadLocal[1];
            finallocal[0] = local;
            a[0] = i;
            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    finallocal[0].set(new Create50MB());
                    System.out.println("add i = " + a[0]);
                }
            });
        }

        Thread.sleep(10000);
        local = null;
    }

    static class Create50MB {
        private byte[] bytes = new byte[1024 * 1024 * 50];
    }

    static class MyThreadLocal<T> extends ThreadLocal {
        private byte[] bytes = new byte[1024 * 1024 * 500];
    }
}


