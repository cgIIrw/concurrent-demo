package com.cg.com.cg.patterns;

import java.util.ArrayDeque;
import java.util.Queue;

public class BoundedBufferDemo {

    public static void main(String[] args) {
        MyQueue<String> queue = new MyQueue<>(5);
        new Producer(queue).start();
        new Consumer(queue).start();

    }

    static class MyQueue<E> {
        private Queue<E> queue = null;
        private int lengh;

        public MyQueue(int lengh) {
            this.lengh = lengh;
            queue = new ArrayDeque<>();
        }

        public synchronized void put(E e) throws InterruptedException {
            while (queue.size() == lengh) {
                wait();
            }

            queue.add(e);
            notifyAll();
        }

        public synchronized E take() throws InterruptedException {
            while (queue.isEmpty()) {
                wait();
            }

            E e = queue.poll();
            notifyAll();
            return e;
        }
    }

    static class Producer extends Thread {
        MyQueue<String> queue;

        public Producer(MyQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            int num = 0;
            while (true) {
                String task = String.valueOf(num);
                try {
                    queue.put(task);
                    System.out.println("producer task" + task);
                    num++;
                    Thread.sleep((int) (Math.random() * 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer extends Thread {
        MyQueue<String> queue;

        public Consumer(MyQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String task = queue.take();
                    System.out.println("handle task" + task);
                    Thread.sleep((int) (Math.random() * 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
