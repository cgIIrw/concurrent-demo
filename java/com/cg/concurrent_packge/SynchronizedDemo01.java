package com.cg.concurrent_packge;

import java.util.Scanner;

/**
 * 这个例子主要用来说明即便IO阻塞，JVM线程状态仍然属于RUNNABLE状态；
 * t2线程在获取内置锁之前，会做优化的工作，不会立即阻塞；
 */
public class SynchronizedDemo01 {

    public static void main(String[] args) {
        byte[] lock = new byte[0];

        Runnable task = () -> {
            {
                synchronized (lock) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("waiting enter sth: ");
                    String s = scanner.next();
                    System.out.println(Thread.currentThread().getName() + ": " + s);
                }
            }
        };

        Thread t1 = new Thread(task, "t1");
        Thread t2 = new Thread(task, "t2");
        t1.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t1.getState());
        t2.start();

        System.out.println(t2.getState());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t2.getState());
    }
}
