package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.ListIterator;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the custom LinkedList implementation.
 * 
 * @author Dania Swelam
 */
public class LinkedListTest {

	/** Field for list of objects */
    private LinkedList<String> list;

    /**
     * Sets up a new list before each test.
     * 
     * @throws Exception if test throws exception.
     */
    @Before
    public void setUp() throws Exception {
        list = new LinkedList<>();
    }

    /**
     * Tests add method.
     */
    @Test
    public void testAdd() {
        list.add(0, "Apple");
        list.add(1, "Banana");
        list.add(2, "Cherry");

        assertEquals(3, list.size());
        assertEquals("Apple", list.get(0));
        assertEquals("Banana", list.get(1));
        assertEquals("Cherry", list.get(2));

        try {
            list.add(1, "Apple");
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            list.add(4, "Date");
            fail();
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        try {
            list.add(1, null);
            fail();
        } catch (NullPointerException e) {
            // expected
        }
    }

    /** 
     * Test for remove method
     */
    @Test
    public void testRemove() {
        list.add(0, "Apple");
        list.add(1, "Banana");
        list.add(2, "Cherry");

        assertEquals("Banana", list.remove(1));
        assertEquals(2, list.size());
        assertEquals("Apple", list.get(0));
        assertEquals("Cherry", list.get(1));

        try {
            list.remove(2);
            fail();
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    /**
     * Test for set method
     */
    @Test
    public void testSet() {
        list.add(0, "Apple");
        list.add(1, "Banana");
        list.add(2, "Cherry");

        list.set(1, "Date");

        assertEquals("Date", list.get(1));

        try {
            list.set(1, "Apple");
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            list.set(3, "Elderberry");
            fail();
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        try {
            list.set(1, null);
            fail();
        } catch (NullPointerException e) {
            // expected
        }
    }

    /**
     * Test for iterator
     */
    @Test
    public void testIterator() {
        list.add(0, "Apple");
        list.add(1, "Banana");
        list.add(2, "Cherry");

        ListIterator<String> it = list.listIterator();

        assertTrue(it.hasNext());
        assertEquals("Apple", it.next());
        assertEquals("Banana", it.next());
        assertEquals("Cherry", it.next());
        assertFalse(it.hasNext());

        assertTrue(it.hasPrevious());
        assertEquals("Cherry", it.previous());
        assertEquals("Banana", it.previous());
        assertEquals("Apple", it.previous());
        assertFalse(it.hasPrevious());

        try {
            it.next();
            it.remove();
            it.remove();
            fail();
        } catch (IllegalStateException e) {
            // expected
        }
    }
}
