package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Objects;

/**
 * ArrayList class constructs a class full of custom methods for an ArrayList
 * that extends the AbstractList.
 * 
 * @param <E> elements that construct the ArrayList.
 * @author Dania Swelam
 */
public class ArrayList<E> extends AbstractList<E> {

    /** Field for the initial size of the constructed ArrayList */
    private static final int INIT_SIZE = 10;
    /** Array to store the elements of the ArrayList */
    private E[] list;
    /** The current size of the ArrayList */
    private int size;

    /**
     * Constructor for the ArrayList class.
     */
    @SuppressWarnings("unchecked")
    public ArrayList() {
        list = (E[]) new Object[INIT_SIZE];
        size = 0;
    }

    /**
     * Adds an element at the specified index in the ArrayList.
     * 
     * @param index   The index at which the element is to be added.
     * @param element The element to be added to the list.
     * @throws IllegalArgumentException  if the element is a duplicate.
     * @throws NullPointerException      if the element is null.
     * @throws IndexOutOfBoundsException if the index is less than 0 or greater than the size of the list.
     */
    @Override
    public void add(int index, E element) {
        if (element == null)
            throw new NullPointerException();
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        for (int i = 0; i < size; i++) {
            if (list[i].equals(element))
                throw new IllegalArgumentException();
        }
        if (size == list.length)
            growArray();
        System.arraycopy(list, index, list, index + 1, size - index);
        list[index] = element;
        size++;
    }

    /**
     * Helper method to grow the array when it reaches its capacity.
     */
    private void growArray() {
        if (size == list.length) {
            list = Arrays.copyOf(list, list.length * 2);
        }
    }

    /**
     * Removes the element at the specified index in the ArrayList.
     * 
     * @param index The index of the element to be removed.
     * @return The removed element.
     * @throws IndexOutOfBoundsException if the index is less than 0 or greater than or equal to the size of the list.
     */
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        E removedElement = list[index];
        System.arraycopy(list, index + 1, list, index, size - index - 1);
        list[--size] = null;
        return removedElement;
    }

    /**
     * Sets the element at the specified index in the ArrayList.
     * 
     * @param index   The index at which the element is to be set.
     * @param element The element to be set at the specified index.
     * @return The element previously at the specified index.
     * @throws IllegalArgumentException  if the element is a duplicate.
     * @throws NullPointerException      if the element is null.
     * @throws IndexOutOfBoundsException if the index is less than 0 or greater than or equal to the size of the list.
     */
    @Override
    public E set(int index, E element) {
        if (element == null)
            throw new NullPointerException();
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        for (int i = 0; i < size; i++) {
            if (list[i].equals(element))
                throw new IllegalArgumentException();
        }
        E oldElement = list[index];
        list[index] = element;
        return oldElement;
    }

    /**
     * Gets the element at the specified index in the ArrayList.
     * 
     * @param index The index of the element to get.
     * @return The element at the specified index.
     * @throws IndexOutOfBoundsException if the index is less than 0 or greater than or equal to the size of the list.
     */
    @Override
    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        return list[index];
    }

    /**
     * Returns the size of the ArrayList.
     * 
     * @return The size of the ArrayList.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Generates a hash code for the ArrayList.
     * 
     * @return The hash code of the ArrayList.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.deepHashCode(list);
        result = prime * result + size;
        return result;
    }

    /**
     * Compares this ArrayList to another object for equality.
     * 
     * @param obj The object to compare to.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        if (!super.equals(obj))
            return false;
        ArrayList<?> other = (ArrayList<?>) obj;
        if (size != other.size)
            return false;
        for (int i = 0; i < size; i++) {
            if (!Objects.equals(list[i], other.list[i]))
                return false;
        }
        return true;
    }
    
}
