package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * ArrayQueue implementation of the Queue interface.
 * Uses an ArrayList to store elements.
 * 
 * @param <E> the type of elements in this queue
 * @author Dania Swelam
 */
public class ArrayQueue<E> implements Queue<E> {
    
	/** Field for list of queue */
	private ArrayList<E> queue;
	/** Field for capacity of queue */
    private int capacity;

    /**
     * Constructs an ArrayQueue with the specified capacity.
     * 
     * @param capacity the capacity of the queue
     */
    public ArrayQueue(int capacity) {
        queue = new ArrayList<>();
        setCapacity(capacity);
    }

    @Override
    public void enqueue(E element) {
        if (queue.size() >= capacity) {
            throw new IllegalArgumentException();
        }
        queue.add(element);
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
        if (capacity < 0 || capacity < queue.size()) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
    }
    
}
