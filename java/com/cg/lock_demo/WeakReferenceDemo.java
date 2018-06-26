package com.cg.lock_demo;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class WeakReferenceDemo {
    public static void main(String[] args) {
        WeakReference<Fruit> fruitWeakReference = new WeakReference<>(new Fruit());
        Fruit f = fruitWeakReference.get();

        if (fruitWeakReference.get() != null) {
            System.out.println("Before GC, this is the result");
        }

        System.gc();

        if (fruitWeakReference.get() != null) {
            System.out.println("After GC, fruitWeakReference.get() is not null");
        } else {
            System.out.println("After GC, fruitWeakReference.get() is null");
        }
    }
}

class Fruit {

}
