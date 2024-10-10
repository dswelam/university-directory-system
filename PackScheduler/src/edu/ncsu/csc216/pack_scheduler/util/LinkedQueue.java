package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * LinkedQueue implementation of the Queue interface.
 * Uses a LinkedAbstractList to store elements.
 * 
 * @param <E> the type of elements in this queue
 * @author Dania Swelam
 */
public class LinkedQueue<E> implements Queue<E> {
    
	/** Field for list queue */
	private LinkedAbstractList<E> queue;

    /**
     * Constructs a LinkedQueue with the specified capacity.
     * 
     * @param capacity the capacity of the queue
     */
    public LinkedQueue(int capacity) {
        queue = new LinkedAbstractList<>(capacity);
        setCapacity(capacity);
    }

    @Override
    public void enqueue(E element) {
        if (queue.size() >= queue.getCapacity()) {
            throw new IllegalArgumentException();
        }
        queue.add(queue.size(), element);
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return queue.remove(0);
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public void setCapacity(int capacity) {
        queue.setCapacity(capacity);
    }
    
    /**
     * Checks if the queue contains the specified element.
     * 
     * @param element the element to check for
     * @return true if the queue contains the element, false otherwise
     */
    public boolean contains(E element) {
        return queue.contains(element);
    }
    
    /**
     * Removes the first occurrence of the specified element from this queue,
     * if it is present. If this queue does not contain the element, it is unchanged.
     * 
     * @param element the element to remove
     * @return true if the element was removed, false otherwise
     */
    public boolean remove(E element) {
        return queue.remove(element);
    }
    
}
