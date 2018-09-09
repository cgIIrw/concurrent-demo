package com.cg.lock_demo;

/**
 * 用来彰显if else if和连续if的区别
 */
public class IfElseIfDemo {
    public static void main(String[] args) {
        System.out.println(add(4));
    }

    public static int add(int x) {
        if (x > 3) {
            x = x - 2;
        } else if (x < 3) {
            x = x + 10;
        }
        return x;
    }
}
