package com.cg.concurrent_packge;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in " + getName());
                LockSupport.park();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // LockSupport.unpark(t1); 位置A
        t1.start();
        LockSupport.unpark(t1); // 位置B 注意这一行出现的地方(虽然不能保证unpark()发生在park()之前)
        Thread.sleep(100);
        t2.start();
        // LockSupport.unpark(t1); 位置C

        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}
