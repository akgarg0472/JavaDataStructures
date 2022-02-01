package com.akgarg.datastructures;

import java.util.*;

/**
 * Generic PriorityQueue implementation with basic methods
 * like insertion, deletion, traversal & few more generally used methods like
 * size, isEmpty etc.
 * <p>
 * This implementation is not synchronized.
 * </p>
 *
 * @param <E> the type of elements to store in PriorityQueue (class type only)
 * @author Akhilesh Garg
 */
@SuppressWarnings("unused")
public class PriorityQueue<E extends Comparable<E>> implements Iterable<E> {

    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    private static final int MAX_QUEUE_CAPACITY = Integer.MAX_VALUE - 8;

    /**
     * Comparator to use with the PriorityQueue to order elements
     */
    private final Comparator<? super E> comparator;

    /**
     * Object array to store the actual values in Queue.
     * <p>
     * Stores the values in balanced binary tree structure making insertion and
     * removal of elements efficient.
     * </p>
     */
    private Object[] queue;

    /**
     * Current size of the PriorityQueue
     */
    private int size;

    /**
     * Constructor to create a PriorityQueue with all values set to their initial
     * values.
     */
    public PriorityQueue() {
        this(DEFAULT_INITIAL_CAPACITY, null);
    }

    /**
     * Constructor to set the initial capacity of the PriorityQueue with other
     * values to their default value.
     *
     * @param initialCapacity initial capacity of the ArrayList.
     * @throws IllegalArgumentException if initial capacity is less than 1.
     */
    public PriorityQueue(int initialCapacity) {
        this(initialCapacity, null);
    }

    /**
     * Constructor to set the comparator of the PriorityQueue with other values to
     * their default value.
     *
     * @param comparator comparator to use for ordering of elements.
     * @see Comparator
     */
    public PriorityQueue(Comparator<? super E> comparator) {
        this(DEFAULT_INITIAL_CAPACITY, comparator);
    }

    /**
     * Constructor to set the initial capacity & comparator of the PriorityQueue
     *
     * @param initialCapacity Initial capacity of the PriorityQueue
     * @param comparator      Comparator to use for ordering of elements.
     * @throws IllegalArgumentException if initialCapacity is invalid
     * @see Comparator
     */
    public PriorityQueue(int initialCapacity, Comparator<? super E> comparator) {
        if (initialCapacity < 1 || initialCapacity > MAX_QUEUE_CAPACITY) {
            throw new IllegalArgumentException("Size of queue is invalid: " + initialCapacity);
        }

        this.queue = new Object[initialCapacity];
        this.size = 0;
        this.comparator = comparator;
    }

    /**
     * Inserts specified element in this PriorityQueue
     *
     * @param e element to be inserted
     * @return true
     * @throws NullPointerException if specified element is null
     * @see PriorityQueue#offer(Comparable)
     */
    public boolean add(E e) {
        return this.offer(e);
    }

    /**
     * Inserts specified element in this PriorityQueue
     *
     * @param e element to be inserted
     * @return true
     * @throws NullPointerException if specified element is null
     */
    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException("Null insertion is not allowed");
        }

        if (this.size >= queue.length) {
            growPriorityQueue();
        }

        insertElement(size, e);
        size++;
        return true;
    }

    /**
     * Inserts element at specified position in this PriorityQueue.
     * <p>
     * If PriorityQueue is empty then it inserts element at head position and
     * returns back. But if this PriorityQueue has some elements then it insert
     * element at the end of queue and then moves element to its actual position.
     * </p>
     *
     * @param position position where to insert element
     * @param element  element to be inserted in this PriorityQueue
     */
    @SuppressWarnings("unchecked")
    private void insertElement(int position, E element) {
        if (position == 0) {
            queue[position] = element;
            return;
        }

        queue[position] = element;

        while (position > 0) {
            int parentPosition = (position - 1) >> 1;
            E parentElement = (E) queue[parentPosition];

            if (compare(element, parentElement) < 0) {
                swapQueueElements(position, parentPosition);
                position = parentPosition;
            } else {
                break;
            }
        }
    }

    /**
     * Creates a new PriorityQueue of new capacity when current PriorityQueue
     * exceeds its capacity and replace current PriorityQueue with new PriorityQueue
     *
     * @throws OutOfMemoryError if JVM runs out of memory for this PriorityQueue
     */
    private void growPriorityQueue() {
        int currentCapacity = queue.length;
        int newCapacity = currentCapacity < 64 ? (currentCapacity << 1) : currentCapacity + (currentCapacity >> 1);

        if (newCapacity > MAX_QUEUE_CAPACITY) {
            throw new OutOfMemoryError("JVM Running out of Memory");
        }

        Object[] newQueue = new Object[newCapacity];
        System.arraycopy(queue, 0, newQueue, 0, queue.length);
        queue = newQueue;
    }

    /**
     * Returns the element present at the head after removing it from PriorityQueue
     *
     * @return element present at the head or null is PriorityQueue is empty
     */
    public E poll() {
        return this.isEmpty() ? null : removeAt(0);
    }

    /**
     * Removes the specified element from PriorityQueue if present
     *
     * @param o element to remove from PriorityQueue
     * @return true if element is removed or false otherwise
     * @see PriorityQueue#indexOf(Object)
     */
    public boolean remove(Object o) {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        int elementIndex = this.indexOf(o);
        removeAt(elementIndex);
        return elementIndex != -1;
    }

    /**
     * Returns the element present at the head after removing it from PriorityQueue
     *
     * @return element present at the head
     * @throws NoSuchElementException if PriorityQueue is empty
     */
    public E remove() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        return removeAt(0);
    }

    /**
     * Method to remove the element of specified index
     *
     * @param index index of element to remove
     * @return element of specified index or null if index is out of bounds
     */
    @SuppressWarnings("unchecked")
    private E removeAt(int index) {
        if (index < 0) {
            return null;
        }

        E removedElement = (E) queue[index];
        this.size--;

        if (this.size > 0) {
            swapQueueElements(this.size, index);
            queue[size] = null;
            int pointer = index;

            while (pointer < this.size) {
                E element = (E) queue[pointer];
                E leftChild;
                E rightChild;
                int leftChildPosition = (pointer << 1) + 1;
                int rightChildPosition = (pointer << 1) + 2;
                int minMaxElementPosition = pointer;

                if (leftChildPosition < this.size) {
                    leftChild = (E) queue[leftChildPosition];

                    if (compare(element, leftChild) > 0) {
                        minMaxElementPosition = leftChildPosition;
                    }
                }

                if (rightChildPosition < this.size) {
                    rightChild = (E) queue[rightChildPosition];
                    element = (E) queue[minMaxElementPosition];

                    if (compare(element, rightChild) > 0) {
                        minMaxElementPosition = rightChildPosition;
                    }
                }

                if (minMaxElementPosition != pointer) {
                    swapQueueElements(minMaxElementPosition, pointer);
                    pointer = minMaxElementPosition;
                } else {
                    break;
                }
            }
        }

        return removedElement;
    }

    /**
     * Returns the element present at the head without removing it from
     * PriorityQueue
     *
     * @return element at the head of PriorityQueue or null if PriorityQueue is
     *         empty
     */
    @SuppressWarnings("unchecked")
    public E peek() {
        return this.isEmpty() ? null : (E) queue[0];
    }

    /**
     * Returns the element present at the head without removing it from
     * PriorityQueue
     *
     * @return element present at the head
     * @throws NoSuchElementException if PriorityQueue is empty
     */
    @SuppressWarnings("unchecked")
    public E element() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        return (E) queue[0];
    }

    /**
     * Swaps the elements of specified indices in this PriorityQueue.
     *
     * @param parentPosition index position of first element
     * @param childPosition  index position of second element.
     * @throws IllegalArgumentException if any of index position is beyond the
     *                                  bounds of this PriorityQueue
     */
    @SuppressWarnings("unchecked")
    private void swapQueueElements(int parentPosition, int childPosition) {
        if (parentPosition > this.size || childPosition > this.size) {
            throw new IllegalArgumentException();
        }

        E element = (E) queue[childPosition];
        queue[childPosition] = queue[parentPosition];
        queue[parentPosition] = element;
    }

    /**
     * Remove all elements from the PriorityQueue and makes this PriorityQueue empty
     */
    public void clear() {
        queue = new Object[DEFAULT_INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Returns the comparator used to order elements in this PriorityQueue
     *
     * @return Comparator used to order elements in PriorityQueue or null if no
     *         comparator is used.
     */
    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    /**
     * Determines whether specified element is present in this PriorityQueue or not
     *
     * @param e element to check if present or not in this PriorityQueue
     * @return true if element is present or false otherwise
     */
    public boolean contains(Object e) {
        return this.indexOf(e) != -1;
    }

    /**
     * Returns the index of specified element in this PriorityQueue
     *
     * @param o element to find index of
     * @return index of element if present or -1 otherwise
     */
    private int indexOf(Object o) {
        int index = -1;

        for (E element : this) {
            index++;
            if (element == o || element.equals(o)) {
                return index;
            }
        }

        return -1;
    }

    /**
     * Returns the current size of PriorityQueue
     */
    public int size() {
        return this.size;
    }

    /**
     * Checks if this PriorityQueue is empty or not
     *
     * @return true if PriorityQueue is empty or false otherwise
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Method to compare two elements
     *
     * @param first  element with which other element is compared
     * @param second second element which is compared with first element
     * @return -1, 0 or 1 according to comparator (if available) or element's
     *         comparable method
     * @see Comparable
     * @see Comparator
     */
    private int compare(E first, E second) {
        return this.comparator == null ? first.compareTo(second) : comparator.compare(first, second);
    }

    /**
     * Returns the iterator for this PriorityQueue
     *
     * @return iterator object for this PriorityQueue
     * @see Iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new PriorityQueueIterator();
    }

    /**
     * Checks if current PriorityQueue is equal to the specified PriorityQueue
     *
     * @return true if specified PQ is equal to this PQ or false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PriorityQueue<?> that = (PriorityQueue<?>) o;
        return size == that.size && Arrays.equals(queue, that.queue) && comparator.equals(that.comparator);
    }

    /**
     * Returns the hashcode value of this PriorityQueue
     *
     * @return hashcode of this PQ
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(size, comparator);
        result = 31 * result + Arrays.hashCode(queue);
        return result;
    }

    /**
     * Returns the string representation of PriorityQueue.
     * <p>
     * String implementation doesn't guarantee the correct order of elements in this
     * PQ.
     * </p>
     * <p>
     * String returned is enclosed in square brackets ("[]").
     * Adjacent elements are separated by comma (", ").
     * If list is empty then only double brackets will be returned.
     * </p>
     *
     * @return String representation of PriorityQueue
     */
    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "[]";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("[");

        for (E element : this) {
            builder.append(element).append(", ");
        }

        builder.deleteCharAt(builder.length() - 1);
        builder.deleteCharAt(builder.length() - 1);
        builder.append("]");
        return builder.toString();
    }

    /**
     * Class used to implement the iterator for this PriorityQueue.
     *
     * @see Iterator
     */
    private final class PriorityQueueIterator implements Iterator<E> {
        private int iteratorIndex = 0;

        @Override
        public boolean hasNext() {
            return this.iteratorIndex != size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            return (E) queue[this.iteratorIndex++];
        }
    }

}