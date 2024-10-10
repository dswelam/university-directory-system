package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the LinkedQueue class.
 * 
 * @author Dania Swelam
 */
public class LinkedQueueTest {
    
	/** Field for queue list */
	private Queue<Integer> queue;

	/**
	 * Test setup method
	 */
    @BeforeEach
    public void setUp() {
        queue = new LinkedQueue<>(10);
    }

    /**
     * Tests the enqueue method
     */
    @Test
    public void testEnqueue() {
        queue.enqueue(1);
        assertEquals(1, queue.size());
        queue.enqueue(2);
        assertEquals(2, queue.size());
    }

    /**
     * Tests the dequeue method
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
     * Tests the isEmpty method
     */
    @Test
    public void testIsEmpty() {
        assertTrue(queue.isEmpty());
        queue.enqueue(1);
        assertFalse(queue.isEmpty());
    }

    /**
     * Tests the size method
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
     * Tests the setCapacity method
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
        queue = new LinkedQueue<>(2);
        queue.enqueue(1);
        queue.enqueue(2);
        assertThrows(IllegalArgumentException.class, () -> queue.enqueue(3));
    }
}
