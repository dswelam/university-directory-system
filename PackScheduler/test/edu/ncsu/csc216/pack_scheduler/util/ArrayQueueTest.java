package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the ArrayQueue class.
 * 
 * @author Dania Swelam
 */
public class ArrayQueueTest {
    
	/** Field for list queue */
	private Queue<Integer> queue;

    /**
     * Initializes an ArrayQueue with a capacity of 10.
     */
    @BeforeEach
    public void setUp() {
        queue = new ArrayQueue<>(10);
    }

    /**
     * Tests the enqueue method by adding elements to the queue.
     */
    @Test
    public void testEnqueue() {
        queue.enqueue(1);
        assertEquals(1, queue.size());
        queue.enqueue(2);
        assertEquals(2, queue.size());
    }

    /**
     * Tests the dequeue method by removing elements from the queue.
     * Also tests the exception when dequeuing from an empty queue.
     */
    @Test
    public void testDequeue() {
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertThrows(NoSuchElementException.class, () -> queue.dequeue());
    }

    /**
     * Tests the isEmpty method to check if the queue is empty.
     */
    @Test
    public void testIsEmpty() {
        assertTrue(queue.isEmpty());
        queue.enqueue(1);
        assertFalse(queue.isEmpty());
    }

    /**
     * Tests the size method to get the number of elements in the queue.
     */
    @Test
    public void testSize() {
        assertEquals(0, queue.size());
        queue.enqueue(1);
        assertEquals(1, queue.size());
        queue.enqueue(2);
        assertEquals(2, queue.size());
    }

    /**
     * Tests the setCapacity method to set the capacity of the queue.
     * Also tests the exception when setting capacity to less than the current size.
     */
    @Test
    public void testSetCapacity() {
        queue.enqueue(1);
        queue.enqueue(2);
        assertThrows(IllegalArgumentException.class, () -> queue.setCapacity(1));
        queue.setCapacity(2);
        assertEquals(2, queue.size());
    }

    /**
     * Tests the exception when exceeding the queue's capacity.
     */
    @Test
    public void testCapacityExceeded() {
        queue = new ArrayQueue<>(2);
        queue.enqueue(1);
        queue.enqueue(2);
        assertThrows(IllegalArgumentException.class, () -> queue.enqueue(3));
    }
    
}
