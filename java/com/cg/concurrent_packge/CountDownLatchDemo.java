package com.cg.concurrent_packge;

/**
 * CountDownLatch演示
 */
import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo implements Runnable {

    static final CountDownLatch latch = new CountDownLatch(6);
    static final CountDownLatchDemo demo = new CountDownLatchDemo();

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()
                -> {
            try {
                Thread.sleep(4000);
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }, "Await02");

        t.start();

        ExecutorService exec = Executors.newFixedThreadPool(6);
        for (int i = 0; i < 6; i++) {
            exec.submit(demo);
        }

        Thread.sleep(3000);
        latch.await();
        System.out.println(Thread.currentThread().getName());
        exec.shutdown();
    }

    @Override
    public void run() {
        try {
            Thread.sleep( 3000);
            System.out.println(Thread.currentThread().getName());
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
