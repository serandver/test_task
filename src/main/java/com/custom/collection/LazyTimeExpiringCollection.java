package com.custom.collection;

import java.util.*;
import java.util.stream.IntStream;

public class LazyTimeExpiringCollection implements Collection {

    private long DEFAULT_TIME_TO_LIVE_IN_NANOSECONDS = 5000000000L;
    private long timeToLiveInNanoseconds;
    private SortedMap<Long, Object> storage = Collections.synchronizedSortedMap(new TreeMap<Long, Object>());

    public LazyTimeExpiringCollection() {
        timeToLiveInNanoseconds = DEFAULT_TIME_TO_LIVE_IN_NANOSECONDS;
    }

    public LazyTimeExpiringCollection(long nanoseconds) {
        timeToLiveInNanoseconds = nanoseconds;
    }

    public int size() {
        cleanOldElements();
        return storage.values().size();
    }

    private void cleanOldElements() {
        final SortedMap<Long, Object> snapshot = storage;
        long currentTime = System.nanoTime();
        storage = snapshot.tailMap(currentTime);
    }

    public boolean isEmpty() {
        cleanOldElements();
        return storage.isEmpty();
    }

    public boolean contains(Object o) {
        cleanOldElements();
        return storage.values().contains(o);
    }

    public Iterator iterator() {
        cleanOldElements();
        return storage.values().iterator();
    }

    public Object[] toArray() {
        cleanOldElements();
        return storage.values().toArray();
    }

    public boolean add(Object o) {
        cleanOldElements();
        storage.put((System.nanoTime() + timeToLiveInNanoseconds), o);
        delay();
        return true;
    }

    private void delay() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean remove(Object o) {
        cleanOldElements();
        return storage.values().remove(o);
    }

    public boolean addAll(Collection c) {
        cleanOldElements();
        Object[] temp = c.toArray();
        IntStream.range(0, temp.length).forEachOrdered(i -> storage.put((System.nanoTime() + timeToLiveInNanoseconds), temp[i]));
        return true;
    }

    public void clear() {
        storage.clear();
    }

    public boolean retainAll(Collection c) {
        cleanOldElements();
        return storage.values().retainAll(c);
    }

    public boolean removeAll(Collection c) {
        cleanOldElements();
        return storage.values().removeAll(c);
    }

    public boolean containsAll(Collection c) {
        cleanOldElements();
        return storage.values().containsAll(c);
    }

    public Object[] toArray(Object[] a) {
        return storage.values().toArray(a);
    }
}
