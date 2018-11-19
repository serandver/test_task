package com.custom.collection;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LazyTimeExpiringCollectionTest {

    private LazyTimeExpiringCollection lazyTimeExpiringCollection;

    @Test
    public void shouldAddNewElement() {
        lazyTimeExpiringCollection = new LazyTimeExpiringCollection();
        Object object = new Object();
        lazyTimeExpiringCollection.add(object);

        assertTrue(lazyTimeExpiringCollection.contains(object));

        int expectedSize = 1;
        int actualSize = lazyTimeExpiringCollection.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void shouldReturnCorrectSize() {
        lazyTimeExpiringCollection = new LazyTimeExpiringCollection();
        Object object1 = new Object();
        Object object2 = new Object();
        Object object3 = new Object();
        lazyTimeExpiringCollection.add(object1);
        lazyTimeExpiringCollection.add(object2);
        lazyTimeExpiringCollection.add(object3);
        int expectedSize = 3;
        int actualSize = lazyTimeExpiringCollection.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void shouldRemoveOldElements() {
        lazyTimeExpiringCollection = new LazyTimeExpiringCollection();
        Object object1 = new Object();
        Object object2 = new Object();
        lazyTimeExpiringCollection.add(object1);
        assertTrue(lazyTimeExpiringCollection.contains(object1));
        assertTrue(lazyTimeExpiringCollection.size() == 1);

        waitFor(3000);
        lazyTimeExpiringCollection.add(object2);
        assertTrue(lazyTimeExpiringCollection.contains(object2));
        assertTrue(lazyTimeExpiringCollection.size() == 2);

        waitFor(3000);
        assertFalse(lazyTimeExpiringCollection.contains(object1));
        assertTrue(lazyTimeExpiringCollection.contains(object2));
        assertTrue(lazyTimeExpiringCollection.size() == 1);

        waitFor(3000);
        assertTrue(lazyTimeExpiringCollection.isEmpty());
    }

    private void waitFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnTrueWhenIsEmpty() {
        lazyTimeExpiringCollection = new LazyTimeExpiringCollection();
        assertTrue(lazyTimeExpiringCollection.isEmpty());
    }

    @Test
    public void shouldReturnTrueWhenContainsObject() {
        lazyTimeExpiringCollection = new LazyTimeExpiringCollection();
        Object object = new Object();
        lazyTimeExpiringCollection.add(object);

        assertTrue(lazyTimeExpiringCollection.contains(object));
    }

    @Test
    public void shouldReturnArrayOfCurrentValues() {
        lazyTimeExpiringCollection = new LazyTimeExpiringCollection();
        Object object1 = new Object();
        Object object2 = new Object();
        Object object3 = new Object();
        Object[] expected = {object1, object2, object3};

        lazyTimeExpiringCollection.add(object1);
        lazyTimeExpiringCollection.add(object2);
        lazyTimeExpiringCollection.add(object3);

        Object[] actual = lazyTimeExpiringCollection.toArray();

        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void shouldRemoveObject() {
        lazyTimeExpiringCollection = new LazyTimeExpiringCollection();
        Object object = new Object();
        lazyTimeExpiringCollection.add(object);

        assertTrue(lazyTimeExpiringCollection.contains(object));

        lazyTimeExpiringCollection.remove(object);
        assertTrue(lazyTimeExpiringCollection.isEmpty());
    }

    @Test
    public void shouldClearCollection() {
        lazyTimeExpiringCollection = new LazyTimeExpiringCollection();
        Object object1 = new Object();
        Object object2 = new Object();
        lazyTimeExpiringCollection.add(object1);
        lazyTimeExpiringCollection.add(object2);

        assertTrue(lazyTimeExpiringCollection.contains(object1));
        assertTrue(lazyTimeExpiringCollection.contains(object2));

        lazyTimeExpiringCollection.clear();

        assertTrue(lazyTimeExpiringCollection.isEmpty());
    }

    @Test
    public void shouldOkRemoveAll() {
        lazyTimeExpiringCollection = new LazyTimeExpiringCollection();
        Object object1 = new Object();
        Object object2 = new Object();
        Object object3 = new Object();

        lazyTimeExpiringCollection.add(object1);
        lazyTimeExpiringCollection.add(object2);
        lazyTimeExpiringCollection.add(object3);

        Object[] expected = {object1};
        List<Object> objectsForRemoving = Arrays.asList(object1, object2);
        lazyTimeExpiringCollection.removeAll(objectsForRemoving);

        assertTrue(lazyTimeExpiringCollection.size() == 1);
        assertTrue(lazyTimeExpiringCollection.contains(object3));
        assertFalse(lazyTimeExpiringCollection.contains(object1));
        assertFalse(lazyTimeExpiringCollection.contains(object2));
    }
}
