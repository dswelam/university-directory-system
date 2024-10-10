package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the LinkedAbstractList class.
 * 
 * @author Dania Swelam
 */
class LinkedAbstractListTest {

	/** Test list */
    private LinkedAbstractList<String> list;

    /**
     * Sets up the LinkedAbstractList before each test.
     * 
     * @throws Exception if there is an error during setup
     */
    @BeforeEach
    public void setUp() throws Exception {
        list = new LinkedAbstractList<>(10);
    }

    /**
     * Tests the size() method.
     */
    @Test
    void testSize() {
        list.add(0, "apple");
        list.add(1, "banana");
        assertEquals(2, list.size());

        list.remove(0);
        assertEquals(1, list.size());

        list.set(0, "cherry");
        assertEquals(1, list.size());
    }

    /**
     * Tests the LinkedAbstractList constructor.
     */
    @Test
    void testLinkedAbstractList() {
        assertThrows(IllegalArgumentException.class, () -> new LinkedAbstractList<>(-1));
        LinkedAbstractList<String> newList = new LinkedAbstractList<>(5);
        assertEquals(0, newList.size());
    }

    /**
     * Tests the setCapacity() method.
     */
    @Test
    void testSetCapacity() {
        LinkedAbstractList<String> newList = new LinkedAbstractList<>(5);
        newList.add(0, "apple");
        newList.add(1, "banana");

        assertThrows(IllegalArgumentException.class, () -> newList.setCapacity(1));

        newList.setCapacity(3);
        assertDoesNotThrow(() -> newList.add(2, "cherry"));
    }

    /**
     * Tests the get(int) method.
     */
    @Test
    void testGetInt() {
        list.add(0, "apple");
        list.add(1, "banana");
        
        assertEquals("apple", list.get(0));
        assertEquals("banana", list.get(1));

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
    }

    /**
     * Tests the add(int, E) method.
     */
    @Test
    void testAddIntE() {
        list.add(0, "apple");
        assertEquals("apple", list.get(0));

        list.add(1, "banana");
        assertEquals("banana", list.get(1));

        list.add(1, "cherry");
        assertEquals("cherry", list.get(1));
        assertEquals("banana", list.get(2));

        assertThrows(NullPointerException.class, () -> list.add(1, null));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "date"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(4, "date"));
        assertThrows(IllegalArgumentException.class, () -> list.add(1, "apple"));

        for (int i = 0; i < 7; i++) {
            list.add(i + 3, "fruit" + i);
        }

        assertThrows(IllegalArgumentException.class, () -> list.add(10, "overflow"));
    }

    /**
     * Tests the remove(int) method.
     */
    @Test
    void testRemoveInt() {
        list.add(0, "apple");
        list.add(1, "banana");
        list.add(2, "cherry");

        assertEquals("banana", list.remove(1));
        assertEquals("cherry", list.get(1));

        assertEquals("apple", list.remove(0));
        assertEquals("cherry", list.get(0));

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }

    /**
     * Tests the set(int, E) method.
     */
    @Test
    void testSetIntE() {
        list.add(0, "apple");
        list.add(1, "banana");

        assertEquals("banana", list.set(1, "cherry"));
        assertEquals("cherry", list.get(1));

        assertThrows(NullPointerException.class, () -> list.set(1, null));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "date"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(2, "date"));
        assertThrows(IllegalArgumentException.class, () -> list.set(0, "cherry"));
    }

    /**
     * Tests the contains(Object) method.
     */
    @Test
    void testContainsObject() {
        list.add(0, "apple");
        list.add(1, "banana");

        assertTrue(list.contains("apple"));
        assertTrue(list.contains("banana"));
        assertFalse(list.contains("cherry"));
        
        list.remove(0);
        assertFalse(list.contains("apple"));
    }
    
    /**
     * Tests the LinkedAbstractList.ListNode indirectly by manipulating the LinkedAbstractList.
     */
    @Test
    void testListNodeIndirectly() {
        // This test will exercise ListNode by manipulating LinkedAbstractList methods
        list.add(0, "apple");
        list.add(1, "banana");
        list.add(2, "cherry");

        // Check initial state
        assertEquals("apple", list.get(0));
        assertEquals("banana", list.get(1));
        assertEquals("cherry", list.get(2));

        // Modify list
        list.remove(1); // remove "banana"
        assertEquals(2, list.size());
        assertEquals("apple", list.get(0));
        assertEquals("cherry", list.get(1));

        list.set(1, "date");
        assertEquals("date", list.get(1));
    }
}
