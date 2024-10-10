package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Interface representing a Queue data structure.
 *
 * @param <E> the type of elements in this queue
 * @author Dania Swelam
 */
public interface Queue<E> {

    /**
     * Adds the element to the back of the queue.
     * 
     * @param element the element to add
     * @throws IllegalArgumentException if the queue's capacity has been reached
     */
    void enqueue(E element);

    /**
     * Removes and returns the element at the front of the queue.
     * 
     * @return the element removed from the front of the queue
     */
    E dequeue();

    /**
     * Returns true if the queue is empty.
     * 
     * @return true if the queue is empty; false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the queue.
     * 
     * @return the number of elements in the queue
     */
    int size();

    /**
     * Sets the queue's capacity.
     * 
     * @param capacity the new capacity of the queue
     * @throws IllegalArgumentException if the capacity is negative or less than the number of elements in the queue
     */
    void setCapacity(int capacity);
}
