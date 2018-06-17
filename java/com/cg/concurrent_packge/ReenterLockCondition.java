package com.cg.concurrent_packge;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReenterLockCondition implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    @Override
    public void run() {
        lock.lock();
        try {
            condition.await();
            Thread.sleep(5000);
            System.out.println("Thread is going on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLockCondition tl = new ReenterLockCondition();
        Thread t1 = new Thread(tl);
        t1.start();
        Thread.sleep(3000);

        lock.lock();
        condition.signal();
        System.out.println("main-------------");
        long before = System.currentTimeMillis();
        Thread.sleep(4000);
        long after = System.currentTimeMillis();
        System.out.println("main+++++++++++++: " + (after - before));
        lock.unlock();
    }
}
