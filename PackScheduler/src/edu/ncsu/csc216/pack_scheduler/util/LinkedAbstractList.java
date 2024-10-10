package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Linked list implementation of an abstract list.
 *
 * @param <E> the type of elements in this list
 * @author Dania Swelam
 */
public class LinkedAbstractList<E> extends AbstractList<E> {

    /** The first node in the list */
    private ListNode front;
    /** The last node in the list */
    private ListNode back;
    /** The size of the list */
    private int size;
    /** The maximum number of elements the list can hold */
    private int capacity;

    /**
     * Constructs an empty list with the specified capacity.
     *
     * @param capacity the maximum number of elements the list can hold
     * @throws IllegalArgumentException if the specified capacity is negative
     */
    public LinkedAbstractList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException();
        }
        this.front = null;
        this.back = null;
        this.size = 0;
        this.capacity = capacity;
    }

    /**
     * Returns the number of elements in this list.
     * 
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Sets the capacity of the list.
     * 
     * @param capacity the new capacity
     * @throws IllegalArgumentException if the specified capacity is negative or
     *                                  less than the current size
     */
    public void setCapacity(int capacity) {
        if (capacity < 0 || capacity < size) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
    }

    /**
     * Gets the capacity of the list.
     * 
     * @return the capacity of the list
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns the element at the specified position in this list.
     * 
     * @param index the index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        ListNode current = front;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Replaces the element at the specified position in this list with the specified element.
     * 
     * @param index the index of the element to replace
     * @param element the element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if the specified element already exists in the list
     */
    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (element == null) {
            throw new NullPointerException();
        }
        if (contains(element)) {
            throw new IllegalArgumentException();
        }
        ListNode current = front;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        E oldData = current.data;
        current.data = element;
        return oldData;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * 
     * @param index the index at which the specified element is to be inserted
     * @param element the element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if the specified element already exists in the list or the list is at capacity
     */
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (element == null) {
            throw new NullPointerException();
        }
        if (contains(element)) {
            throw new IllegalArgumentException();
        }
        if (size >= capacity) {
            throw new IllegalArgumentException();
        }

        ListNode newNode;
        if (index == 0) {
            newNode = new ListNode(element, front);
            front = newNode;
            if (size == 0) {
                back = front;
            }
        } else if (index == size) {
            newNode = new ListNode(element);
            back.next = newNode;
            back = newNode;
        } else {
            ListNode current = front;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            newNode = new ListNode(element, current.next);
            current.next = newNode;
        }
        size++;
    }

    /**
     * Removes the element at the specified position in this list.
     * 
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        E data;
        if (index == 0) {
            data = front.data;
            front = front.next;
            if (size == 1) {
                back = null;
            }
        } else {
            ListNode current = front;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            data = current.next.data;
            current.next = current.next.next;
            if (index == size - 1) {
                back = current;
            }
        }
        size--;
        return data;
    }

    /**
     * Returns true if this list contains the specified element.
     * 
     * @param element element whose presence in this list is to be tested
     * @return true if this list contains the specified element
     */
    @Override
    public boolean contains(Object element) {
        ListNode current = front;
        while (current != null) {
            if (current.data.equals(element)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * ListNode class represents a node in the linked list.
     */
    private class ListNode {
        /** The data stored in the node */
        public E data;
        /** The next node in the list */
        public ListNode next;

        /**
         * Constructs a new node with the specified data.
         *
         * @param data the data to be stored in the node
         */
        public ListNode(E data) {
            this.data = data;
            this.next = null;
        }

        /**
         * Constructs a new node with the specified data and next node.
         *
         * @param data the data to be stored in the node
         * @param next the next node in the list
         */
        public ListNode(E data, ListNode next) {
            this.data = data;
            this.next = next;
        }
    }
}
