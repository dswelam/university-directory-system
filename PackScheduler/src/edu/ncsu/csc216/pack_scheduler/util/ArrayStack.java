package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * ArrayStack implementation of the Stack interface.
 * Uses an ArrayList to store elements.
 * 
 * @param <E> the type of elements in this stack
 * @author Dania Swelam
 */
public class ArrayStack<E> implements Stack<E> {
    
	/** Field for stack creation */
	private ArrayList<E> stack;
	/** Field for capacity of ArrayStack */
    private int capacity;

    /**
     * Constructs an ArrayStack with the specified capacity.
     * 
     * @param capacity the capacity of the stack
     */
    public ArrayStack(int capacity) {
        stack = new ArrayList<>();
        setCapacity(capacity);
    }

    @Override
    public void push(E element) {
        if (stack.size() >= capacity) {
            throw new IllegalArgumentException();
        }
        stack.add(element);
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.remove(stack.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public void setCapacity(int capacity) {
        if (capacity < 0 || capacity < stack.size()) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
    }
}
