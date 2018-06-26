package com.cg.lock_demo;

public class ThreadLocalDemo {
    public static void main(String[] args) {
        final ThreadLocal<Integer> local = new ThreadLocal<>();
        local.set(100);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " local: " + local.get());
            }
        });
        t.start();
        System.out.println("Main local: " + local.get());
    }
}