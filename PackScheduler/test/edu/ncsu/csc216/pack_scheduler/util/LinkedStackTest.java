package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the LinkedStack class.
 * 
 * @author Dania Swelam
 */
public class LinkedStackTest {
	
	/** Field for stack list. */
    private Stack<Integer> stack;

    /**
     * Sets up the test environment before each test.
     * Initializes a LinkedStack with a capacity of 10.
     */
    @BeforeEach
    public void setUp() {
        stack = new LinkedStack<>(10);
    }

    /**
     * Tests the push method by adding elements to the stack.
     */
    @Test
    public void testPush() {
        stack.push(1);
        assertEquals(1, stack.size());
        stack.push(2);
        assertEquals(2, stack.size());
    }

    /**
     * Tests the pop method by removing elements from the stack.
     * Also tests the exception when popping from an empty stack.
     */
    @Test
    public void testPop() {
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertThrows(EmptyStackException.class, () -> stack.pop());
    }

    /**
     * Tests the isEmpty method to check if the stack is empty.
     */
    @Test
    public void testIsEmpty() {
        assertTrue(stack.isEmpty());
        stack.push(1);
        assertFalse(stack.isEmpty());
    }

    /**
     * Tests the size method to get the number of elements in the stack.
     */
    @Test
    public void testSize() {
        assertEquals(0, stack.size());
        stack.push(1);
        assertEquals(1, stack.size());
        stack.push(2);
        assertEquals(2, stack.size());
    }

    /**
     * Tests the setCapacity method to set the capacity of the stack.
     * Also tests the exception when setting capacity to less than the current size.
     */
    @Test
    public void testSetCapacity() {
        stack.push(1);
        stack.push(2);
        assertThrows(IllegalArgumentException.class, () -> stack.setCapacity(1));
        stack.setCapacity(2);
        assertEquals(2, stack.size());
    }

    /**
     * Tests the exception when exceeding the stack's capacity.
     */
    @Test
    public void testCapacityExceeded() {
        stack = new LinkedStack<>(2);
        stack.push(1);
        stack.push(2);
        assertThrows(IllegalArgumentException.class, () -> stack.push(3));
    }
    
}
