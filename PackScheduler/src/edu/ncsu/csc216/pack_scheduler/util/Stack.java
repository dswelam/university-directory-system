package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Interface that implements ArrayStack and LinkedStack.
 * 
 * @param <E> the type of elements in this stack
 * @author Dania Swelam
 */
public interface Stack<E> {
	
	/**
     * Adds the element to the top of the stack.
     * 
     * @param element the element to add
     * @throws IllegalArgumentException if the stack's capacity has been reached
     */
    void push(E element);

    /**
     * Removes and returns the element at the top of the stack.
     * 
     * @return the element removed from the top of the stack
     */
    E pop();

    /**
     * Returns true if the stack is empty.
     * 
     * @return true if the stack is empty; false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the stack.
     * 
     * @return the number of elements in the stack
     */
    int size();

    /**
     * Sets the stack's capacity.
     * 
     * @param capacity the new capacity of the stack
     * @throws IllegalArgumentException if the capacity is negative or less than the number of elements in the stack
     */
    void setCapacity(int capacity);
    
}
