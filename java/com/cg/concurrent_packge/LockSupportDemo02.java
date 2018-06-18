package com.cg.concurrent_packge;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo02 {

    public static Object u = new Object();
    static ChangeObjectThread02 t1 = new ChangeObjectThread02("t1");
    static ChangeObjectThread02 t2 = new ChangeObjectThread02("t2");

    public static class ChangeObjectThread02 extends Thread {
        public ChangeObjectThread02(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in " + getName());
                LockSupport.park();
                if (Thread.interrupted()) {
                    System.out.println(getName() + " interrupted");
                }
            }
            System.out.println(getName() + " finished");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();

        LockSupport.unpark(t2);
    }
}
