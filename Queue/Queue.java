package com.akgarg.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Queue implementation using array and Java Generics.
 * By default, queue follow FIFO (First In First Out) but in this implementation
 * there are two methods which can be used to perform insertion at head side of queue and
 * deletion at end of the queue.
 * Please note that this implementation of Queue is not synchronized.
 *
 * @author Akhilesh Garg
 */
public class Queue<E> implements Iterable<E> {

    // default capacity of Queue if initial capacity is not specified by the user
    private static final int DEFAULT_SIZE = 10;

    // Object array to store all elements of queue
    private Object[] queue;

    // variable to hold the current capacity and is used for dynamic growing of queue according to requirement
    private int capacity;

    // holds the current number of elements in the queue
    private int size;


    /**
     * Default constructor of Queue, initialize all required fields to their default values.
     */
    public Queue() {
        queue = new Object[DEFAULT_SIZE];
        capacity = DEFAULT_SIZE;
        this.size = 0;
    }


    /**
     * Constructor used to initialize the fields and capacity according to the given user input.
     *
     * @param initialCapacity capacity of the queue.
     * @throws IllegalArgumentException if initialCapacity is less than 1.
     */
    public Queue(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Invalid capacity " + initialCapacity);
        }

        queue = new Object[initialCapacity];
        this.size = 0;
        capacity = initialCapacity;
    }


    /**
     * Method to add new element at the end of the queue.
     *
     * @param element element to append in the queue.
     * @return true on successful insertion.
     * @throws NullPointerException if element provided is null.
     */
    public boolean add(E element) {
        if (size == capacity) {
            queue = getNewArray(size);
            capacity = queue.length;
        }

        if (element == null) {
            throw new NullPointerException("null insertion is not allowed");
        }

        queue[size] = element;
        size++;
        return true;
    }


    /**
     * Method to insert new element at the specified position in the queue.
     *
     * @param index   index position where to insert new element in queue.
     * @param element element what is going to be stored in queue.
     * @throws NullPointerException if element provided is null.
     */
    public boolean add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Invalid index " + index);
        }

        if (element == null) {
            throw new NullPointerException("null insertion is not allowed");
        }

        if (size == capacity) {
            queue = getNewArray(size);
            capacity = queue.length;
        }

        int count = 0;

        while (count != index) {
            count++;
        }

        for (int i = size + 1; i >= count; i--) {
            queue[i + 1] = queue[i];
        }

        queue[count] = element;
        size++;

        return true;
    }


    /**
     * Method used to insert new element at the end of the queue.
     *
     * @param element element to append in the queue.
     * @return boolean value according to success or failure of insertion.
     */
    public boolean offer(E element) {
        if (size == capacity) {
            queue = getNewArray(size);
            capacity = queue.length;
        }

        if (element == null) {
            return false;
        }

        queue[size] = element;
        size++;
        return true;
    }


    /**
     * Method to retrieve the head element of the queue without removing the head.
     *
     * @return head element of the queue.
     * @throws NoSuchElementException if queue is empty.
     */
    public E element() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        return (E) queue[0];
    }


    /**
     * Method to retrieve the head element of the queue without removing the head.
     *
     * @return head element of the queue or return null if queue is empty.
     */
    public E peek() {
        if (size == 0) {
            return null;
        }

        return (E) queue[0];
    }


    /**
     * Method to remove the head of the queue.
     *
     * @return removed head element of queue.
     * @throws IllegalStateException if queue is empty.
     */
    public E remove() {
        if (size == 0) {
            throw new IllegalStateException("Queue is Empty");
        }

        E object = (E) queue[0];
        for (int i = 1; i < size; i++) {
            queue[i - 1] = queue[i];
        }

        queue[size] = null;
        size--;

        return object;
    }


    /**
     * Method to remove the element of specific index position from queue.
     *
     * @param index index position of element to remove.
     * @return element which is removed from queue.
     * @throws IndexOutOfBoundsException if (index<0 || index > size-1) of queue.
     */
    public E remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("index out of range " + index);
        }

        int count = 0;

        while (count != index) {
            count++;
        }

        E object = (E) queue[count];

        for (int i = index; i < size; i++) {
            queue[i] = queue[i + 1];
        }

        queue[size] = null;
        size--;

        return object;
    }


    /**
     * Method to remove the head of the queue.
     *
     * @return removed head element of queue or return null if queue is empty.
     */
    public E poll() {
        if (size == 0) {
            return null;
        }

        E object = (E) queue[0];
        for (int i = 1; i < size; i++) {
            queue[i - 1] = queue[i];
        }
        queue[size] = null;

        size--;

        return object;
    }


    /**
     * Method to retrieve the current total number of elements in queue.
     *
     * @return current size of queue.
     */
    public int size() {
        return this.size;
    }


    /**
     * Method to retrieve the current capacity of the queue.
     *
     * @return current capacity of queue.
     */
    public int capacity() {
        return this.capacity;
    }


    /**
     * Method to determine if queue is empty or not.
     *
     * @return true if queue is empty or false if queue is not empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Method to retrieve the element of the specific index in the queue.
     *
     * @param index index position of element to retrieve.
     * @return element at specified index position.
     * @throws IndexOutOfBoundsException if index range in invalid (index<0 || index>size-1).
     */
    public E get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        return (E) queue[index];
    }


    /**
     * Method to insert new element at the beginning of the queue.
     *
     * @param element element to insert in the queue.
     * @return boolean value according to success or failure of the insertion operation.
     */
    public boolean enQueue(E element) {
        if (element == null) {
            return false;
        }

        if (size == capacity) {
            queue = getNewArray(size + size / 2);
            capacity = queue.length;
        }

        return add(0, element);
    }


    /**
     * Method to remove the last element from the queue.
     *
     * @return element removed from the queue.
     * @throws IllegalStateException if queue is empty.
     */
    public E deQueue() {
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }

        return remove(size - 1);
    }


    /**
     * Private method to dynamically grow queue if queue gets overflow.
     *
     * @param size size of the current queue array.
     * @return new Object[] array of size (previousSize + previousSize/2)
     */
    private Object[] getNewArray(int size) {
        Object[] newArray = new Object[size + size / 2];
        for (int i = 0; i < this.size; i++) {
            newArray[i] = this.queue[i];
        }
        return newArray;
    }


    /**
     * Method to return the string representation of queue.
     * String returned is enclosed in square brackets ("[]").
     * Adjacent elements are seperated by comma (", ").
     * If queue is empty then only double brackets ("[]") will be returned.
     *
     * @return String representation of this queue.
     */
    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(queue[i].toString());
            sb.append(", ");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");

        return sb.toString();
    }


    /**
     * Method to get the iterator object for this queue.
     *
     * @return iterator object for this queue.
     */
    @Override
    public Iterator<E> iterator() {
        return new QueueIterator();
    }


    /**
     * class to enable queue capability for enhanced for loop and iterator iteration.
     * Used by iterator() method to get the iterator of this queue.
     */
    private class QueueIterator implements Iterator<E> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index != size;
        }

        @Override
        public E next() {
            return (E) queue[index++];
        }
    }
}