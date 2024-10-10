package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Custom implementation of a linked list that doesn’t allow for null elements or duplicate elements.
 * 
 * @param <E> the type of elements in this list
 * @author Dania Swelam
 */
public class LinkedList<E> extends AbstractSequentialList<E> {

    /** Front of the list */
    private ListNode front;
    /** Back of the list */
    private ListNode back;
    /** Size of the list */
    private int size;

    /**
     * Constructor for initializing the linked list.
     */
    public LinkedList() {
        front = new ListNode(null);
        back = new ListNode(null);
        front.next = back;
        back.prev = front;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new LinkedListIterator(index);
    }

    @Override
    public void add(int index, E element) {
        if (element == null) {
            throw new NullPointerException("Null elements are not allowed.");
        }
        if (contains(element)) {
            throw new IllegalArgumentException("Duplicate elements are not allowed.");
        }
        super.add(index, element);
    }

    @Override
    public E set(int index, E element) {
        if (element == null) {
            throw new NullPointerException("Null elements are not allowed.");
        }
        if (contains(element)) {
            throw new IllegalArgumentException("Duplicate elements are not allowed.");
        }
        return super.set(index, element);
    }

    /**
     * Inner class for the nodes in the linked list.
     */
    private class ListNode {
        /** Data in the node */
        public E data;
        /** Next node in the list */
        public ListNode next;
        /** Previous node in the list */
        public ListNode prev;

        /**
         * Constructor for creating a node with data.
         * 
         * @param data the data in the node
         */
        public ListNode(E data) {
            this(data, null, null);
        }

        /**
         * Constructor for creating a node with data and pointers to next and previous nodes.
         * 
         * @param data the data in the node
         * @param prev the previous node in the list
         * @param next the next node in the list
         */
        public ListNode(E data, ListNode prev, ListNode next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * Inner class for the iterator for the linked list.
     */
    private class LinkedListIterator implements ListIterator<E> {

        /** Previous node in the list */
        private ListNode previous;
        /** Next node in the list */
        private ListNode next;
        /** Index of the previous node */
        private int previousIndex;
        /** Index of the next node */
        private int nextIndex;
        /** Last retrieved node */
        private ListNode lastRetrieved;

        /**
         * Constructor for initializing the iterator at a specific index.
         * 
         * @param index the index to initialize the iterator at
         */
        public LinkedListIterator(int index) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("Index out of bounds.");
            }
            previous = front;
            next = front.next;
            for (int i = 0; i < index; i++) {
                previous = next;
                next = next.next;
            }
            previousIndex = index - 1;
            nextIndex = index;
            lastRetrieved = null;
        }

        @Override
        public boolean hasNext() {
            return next != back;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No next element.");
            }
            lastRetrieved = next;
            previous = next;
            next = next.next;
            previousIndex++;
            nextIndex++;
            return lastRetrieved.data;
        }

        @Override
        public boolean hasPrevious() {
            return previous != front;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException("No previous element.");
            }
            lastRetrieved = previous;
            next = previous;
            previous = previous.prev;
            previousIndex--;
            nextIndex--;
            return lastRetrieved.data;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return previousIndex;
        }

        @Override
        public void remove() {
            if (lastRetrieved == null) {
                throw new IllegalStateException("No element to remove.");
            }
            lastRetrieved.prev.next = lastRetrieved.next;
            lastRetrieved.next.prev = lastRetrieved.prev;
            size--;
            if (lastRetrieved == next) {
                next = next.next;
            } else {
                previous = previous.prev;
            }
            lastRetrieved = null;
        }

        @Override
        public void set(E e) {
            if (lastRetrieved == null) {
                throw new IllegalStateException("No element to set.");
            }
            if (e == null) {
                throw new NullPointerException("Null elements are not allowed.");
            }
            if (contains(e)) {
                throw new IllegalArgumentException("Duplicate elements are not allowed.");
            }
            lastRetrieved.data = e;
        }

        @Override
        public void add(E e) {
            if (e == null) {
                throw new NullPointerException("Null elements are not allowed.");
            }
            if (contains(e)) {
                throw new IllegalArgumentException("Duplicate elements are not allowed.");
            }
            ListNode newNode = new ListNode(e, previous, next);
            previous.next = newNode;
            next.prev = newNode;
            previous = newNode;
            previousIndex++;
            nextIndex++;
            size++;
            lastRetrieved = null;
        }
    }
}
