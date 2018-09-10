package com.cg.concurrent_packge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * CopyOnWrite示例
 */
public class CopyOnWriteDemo {
    public static void main(String[] args) {
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        ArrayList arrayList = new ArrayList<>();
        Map cmap =new CopyOnWriteMap();
        cmap.put(1, "a");
        new Thread(() ->
        {
            cmap.put(1, "new a");
            System.out.println(cmap.get(1));
        }).start();
        System.out.println(cmap.get(1));
    }
}

class CopyOnWriteMap<K, V> extends HashMap<K, V> implements Cloneable {
    private volatile Map<K, V> internalMap;
    private final ReentrantLock lock = new ReentrantLock();

    public CopyOnWriteMap() {
        internalMap = new HashMap<K, V>();
    }

    public V put(K key, V value) {
        lock.lock();
        try {
            Map<K, V> newMap = new HashMap<K, V>(internalMap);

            Thread.sleep(3000);

            V val = newMap.put(key, value);
            internalMap = newMap;
            return val;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public V get(Object key) {
        return internalMap.get(key);
    }

    public void putAll(Map<? extends K, ? extends V> newData) {
        lock.lock();
        try {
            Map<K, V> newMap = new HashMap<K, V>(internalMap);
            newMap.putAll(newData);
            internalMap = newMap;
        } finally {
            lock.unlock();
        }
    }
}
