package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Custom implementation of a recursive linked list that doesn’t allow for null elements or duplicate elements.
 *
 * @param <E> the type of elements in this list
 * @author Dania Swelam
 */
public class LinkedListRecursive<E> {
    /** Front of the list */
    private ListNode front;
    /** Size of the list */
    private int size;

    /**
     * Constructor for initializing the linked list.
     */
    public LinkedListRecursive() {
        front = null;
        size = 0;
    }

    /**
     * Returns the size of the list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the list is empty, false otherwise.
     *
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if the list contains the specified element.
     *
     * @param element the element to check for
     * @return true if the element is in the list, false otherwise
     */
    public boolean contains(E element) {
        if (front == null) {
            return false;
        }
        return front.contains(element);
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param element the element to add
     * @return true if the element is added, false otherwise
     * @throws IllegalArgumentException if the element is a duplicate
     */
    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException("Null elements are not allowed.");
        }
        if (contains(element)) {
            throw new IllegalArgumentException("Duplicate elements are not allowed.");
        }
        if (front == null) {
            front = new ListNode(element, null);
        } else {
            front.add(element);
        }
        size++;
        return true;
    }

    /**
     * Adds an element at a specified index.
     *
     * @param idx     the index to add the element at
     * @param element the element to add
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws IllegalArgumentException  if the element is a duplicate
     * @throws NullPointerException      if the element is null
     */
    public void add(int idx, E element) {
        if (element == null) {
            throw new NullPointerException("Null elements are not allowed.");
        }
        if (contains(element)) {
            throw new IllegalArgumentException("Duplicate elements are not allowed.");
        }
        if (idx < 0 || idx > size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        if (idx == 0) {
            front = new ListNode(element, front);
        } else {
            front.add(idx, element);
        }
        size++;
    }

    /**
     * Gets the element at a specified index.
     *
     * @param idx the index of the element to get
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public E get(int idx) {
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        return front.get(idx);
    }

    /**
     * Removes the element at a specified index.
     *
     * @param idx the index of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public E remove(int idx) {
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        E removed;
        if (idx == 0) {
            removed = front.data;
            front = front.next;
        } else {
            removed = front.remove(idx);
        }
        size--;
        return removed;
    }

    /**
     * Removes the specified element from the list.
     *
     * @param element the element to remove
     * @return true if the element is removed, false otherwise
     */
    public boolean remove(E element) {
        if (element == null) {
            return false;
        }
        if (front == null) {
            return false;
        }
        if (front.data.equals(element)) {
            front = front.next;
            size--;
            return true;
        }
        boolean removed = front.remove(element);
        if (removed) {
            size--;
        }
        return removed;
    }

    /**
     * Sets the element at a specified index.
     *
     * @param idx     the index to set the element at
     * @param element the element to set
     * @return the old element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws NullPointerException      if the element is null
     * @throws IllegalArgumentException  if the element is a duplicate
     */
    public E set(int idx, E element) {
        if (element == null) {
            throw new NullPointerException("Null elements are not allowed.");
        }
        if (contains(element)) {
            throw new IllegalArgumentException("Duplicate elements are not allowed.");
        }
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        return front.set(idx, element);
    }

    /**
     * Inner class for the nodes in the linked list.
     */
    private class ListNode {
        /** Data in the node */
        public E data;
        /** Next node in the list */
        public ListNode next;

        /**
         * Constructor for creating a node with data.
         *
         * @param data the data in the node
         * @param next the next node in the list
         */
        public ListNode(E data, ListNode next) {
            this.data = data;
            this.next = next;
        }

        /**
         * Checks if the list contains the specified element.
         *
         * @param element the element to check for
         * @return true if the element is in the list, false otherwise
         */
        public boolean contains(E element) {
            if (data.equals(element)) {
                return true;
            }
            if (next == null) {
                return false;
            }
            return next.contains(element);
        }

        /**
         * Adds an element to the end of the list.
         *
         * @param element the element to add
         */
        public void add(E element) {
            if (next == null) {
                next = new ListNode(element, null);
            } else {
                next.add(element);
            }
        }

        /**
         * Adds an element at a specified index.
         *
         * @param idx     the index to add the element at
         * @param element the element to add
         */
        public void add(int idx, E element) {
            if (idx == 1) {
                next = new ListNode(element, next);
            } else {
                next.add(idx - 1, element);
            }
        }

        /**
         * Gets the element at a specified index.
         *
         * @param idx the index of the element to get
         * @return the element at the specified index
         */
        public E get(int idx) {
            if (idx == 0) {
                return data;
            }
            return next.get(idx - 1);
        }

        /**
         * Removes the element at a specified index.
         *
         * @param idx the index of the element to remove
         * @return the removed element
         */
        public E remove(int idx) {
            if (idx == 1) {
                E removed = next.data;
                next = next.next;
                return removed;
            }
            return next.remove(idx - 1);
        }

        /**
         * Removes the specified element from the list.
         *
         * @param element the element to remove
         * @return true if the element is removed, false otherwise
         */
        public boolean remove(E element) {
            if (next == null) {
                return false;
            }
            if (next.data.equals(element)) {
                next = next.next;
                return true;
            }
            return next.remove(element);
        }

        /**
         * Sets the element at a specified index.
         *
         * @param idx     the index to set the element at
         * @param element the element to set
         * @return the old element at the specified index
         */
        public E set(int idx, E element) {
            if (idx == 0) {
                E oldData = data;
                data = element;
                return oldData;
            }
            return next.set(idx - 1, element);
        }
    }
    
}
