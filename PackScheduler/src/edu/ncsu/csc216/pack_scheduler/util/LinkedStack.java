package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * LinkedStack implementation of the Stack interface.
 * Uses a LinkedAbstractList to store elements.
 * 
 * @param <E> the type of elements in this stack
 * @author Dania Swelam
 */
public class LinkedStack<E> implements Stack<E> {
    
	/** Field for stack list. */
	private LinkedAbstractList<E> stack;
    
    /**
     * Constructs a LinkedStack with the specified capacity.
     * 
     * @param capacity the capacity of the stack
     */
    public LinkedStack(int capacity) {
        stack = new LinkedAbstractList<>(capacity);
        setCapacity(capacity);
    }

    @Override
    public void push(E element) {
        if (stack.size() >= stack.getCapacity()) {
            throw new IllegalArgumentException();
        }
        stack.add(stack.size(), element);
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
        stack.setCapacity(capacity);
    }
}
