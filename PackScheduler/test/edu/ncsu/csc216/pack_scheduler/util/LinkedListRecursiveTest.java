package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the LinkedListRecursive class.
 * 
 * @author Dania Swelam
 */
public class LinkedListRecursiveTest {
   
	/** List instance to use for testing */
    private LinkedListRecursive<String> list;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    public void setUp() {
        list = new LinkedListRecursive<>();
    }

    /**
     * Tests the size() method.
     */
    @Test
    public void testSize() {
        assertEquals(0, list.size());
        list.add("Apple");
        assertEquals(1, list.size());
        list.add("Banana");
        assertEquals(2, list.size());
    }

    /**
     * Tests the isEmpty() method.
     */
    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.add("Apple");
        assertFalse(list.isEmpty());
    }

    /**
     * Tests the contains() method.
     */
    @Test
    public void testContains() {
        assertFalse(list.contains("Apple"));
        list.add("Apple");
        assertTrue(list.contains("Apple"));
        assertFalse(list.contains("Banana"));
    }

    /**
     * Tests the add(E) method.
     */
    @Test
    public void testAddE() {
        list.add("Apple");
        assertTrue(list.contains("Apple"));
        assertEquals(1, list.size());

        assertThrows(IllegalArgumentException.class, () -> list.add("Apple"));
    }

    /**
     * Tests the add(int, E) method.
     */
    @Test
    public void testAddIntE() {
        list.add("Apple");
        list.add(1, "Banana");
        list.add(1, "Cherry");
        assertEquals(3, list.size());
        assertEquals("Apple", list.get(0));
        assertEquals("Cherry", list.get(1));
        assertEquals("Banana", list.get(2));

        assertThrows(IllegalArgumentException.class, () -> list.add(1, "Apple"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(4, "Date"));
        assertThrows(NullPointerException.class, () -> list.add(1, null));
    }

    /**
     * Tests the get(int) method.
     */
    @Test
    public void testGet() {
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        assertEquals("Apple", list.get(0));
        assertEquals("Banana", list.get(1));
        assertEquals("Cherry", list.get(2));

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
    }

    /**
     * Tests the remove(int) method.
     */
    @Test
    public void testRemoveInt() {
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        assertEquals("Banana", list.remove(1));
        assertEquals(2, list.size());
        assertEquals("Apple", list.get(0));
        assertEquals("Cherry", list.get(1));

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(2));
    }

    /**
     * Tests the remove(E) method.
     */
    @Test
    public void testRemoveE() {
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        assertTrue(list.remove("Banana"));
        assertFalse(list.contains("Banana"));
        assertEquals(2, list.size());

        assertFalse(list.remove("Date"));
    }

    /**
     * Tests the set(int, E) method.
     */
    @Test
    public void testSet() {
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        assertEquals("Banana", list.set(1, "Date"));
        assertEquals("Date", list.get(1));

        assertThrows(IllegalArgumentException.class, () -> list.set(1, "Apple"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(3, "Elderberry"));
        assertThrows(NullPointerException.class, () -> list.set(1, null));
    }
}
