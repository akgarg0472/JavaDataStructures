package com.akgarg.datastructures;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Stack implementation using array and Java Generics.
 * <p>
 * Stack follows <strong>LIFO</strong> (Last In First Out) methodology.
 * Insertion and deletion both are performed at one end <strong>top</strong> end.
 * <p>Insertion operation is called as <strong>push</strong> and deletion operation is called as <strong>pop</strong> operation.</p>
 * <p>
 * Please note that this implementation of Stack is not synchronized.
 *
 * @author Akhilesh Garg
 */
public class Stack<E> implements Iterable<E> {

    // Default capacity of stack
    private static final int DEFAULT_CAPACITY = 10;

    // Array to store the elements of the stack
    private Object[] array;

    // Current total number of elements in the stack
    private int size;

    // Current capacity of the stack
    private int capacity;


    /**
     * Creates an empty stack and initialize all required fields to their respective default values.
     */
    public Stack() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
    }


    /**
     * Method to insert a new element at the top end of the stack.
     *
     * @param element element to append at end of stack
     * @return element added in stack
     */
    public E add(E element) {
        if (size() == capacity() - 1) {
            this.array = getNewStackArray();
        }

        this.array[size] = element;
        this.size++;

        return element;
    }


    /**
     * Method to insert new element in this stack at given index position.
     *
     * @param index   index position to insert new element
     * @param element element to add at index position
     * @throws IndexOutOfBoundsException if provided index is not valid for this stack
     */
    public void add(int index, E element) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }

        if (size() == this.capacity - 1) {
            this.array = getNewStackArray();
        }

        for (int i = size(); i > index; i--) {
            this.array[i] = this.array[i - 1];
        }

        this.array[index] = element;
        this.size++;
    }


    /**
     * Method to insert new element at the top end of this stack.
     *
     * @param element element to insert in the stack
     */
    public E push(E element) {
        return add(element);
    }


    /**
     * Method to remove the element present at top of the stack.
     *
     * @return element at the top of the stack
     * @throws EmptyStackException if this stack is empty
     */
    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        return remove(size - 1);
    }


    /**
     * Removes the element at specific position from the stack.
     *
     * @param index index of element to remove
     * @return element which was removed from stack
     * @throws IndexOutOfBoundsException if index provided is invalid
     * @throws EmptyStackException       if stack is empty
     */
    public E remove(int index) {
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        if (index < 0 || index > size() - 1) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }

        E data = (E) this.array[index];

        for (int i = index; i < size() - 1; i++) {
            this.array[i] = this.array[i + 1];
        }

        this.array[size] = null;
        size--;
        return data;
    }


    /**
     * Returns the element present at the specific index.
     *
     * @param index index whose element is to retrieve
     * @return element present at index provided
     * @throws IndexOutOfBoundsException if index is invalid for this stack
     * @throws EmptyStackException       if stack is empty
     */
    public E elementAt(int index) {
        return remove(index);
    }


    /**
     * Method to return the element present at the top of the stack without deleting the element from stack.
     *
     * @return element presented at the top of the stack
     * @throws EmptyStackException if stack is empty
     */
    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (E) this.array[size - 1];
    }


    /**
     * Method to return the first element available in the stack.
     *
     * @return first element of the stack
     * @throws NoSuchElementException if stack is empty
     */
    public E firstElement() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return (E) this.array[0];
    }


    /**
     * Method to return the last element available in the stack.
     *
     * @return last element of the stack
     * @throws NoSuchElementException if stack is empty
     */
    public E lastElement() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return (E) this.array[size - 1];
    }


    /**
     * Method to check if stack is empty or not.
     *
     * @return true if stack is empty else false
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }


    /**
     * Method to retrieve element of the specific index in the stack.
     *
     * @param index index of element to retrieve
     * @return element present at given index
     * @throws IndexOutOfBoundsException if index is invalid for the stack
     */
    public E get(int index) {
        if (index < 0 || index > size() - 1) {
            throw new IndexOutOfBoundsException("Index " + index + " out of range");
        }

        if (this.array[index] == null) {
            return null;
        }

        return (E) this.array[index];
    }


    /**
     * Method to remove the all elements from the stack and set the size of stack to 0.
     */
    public void clear() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        System.gc();
    }


    /**
     * Method to remove all elements from the stack and set the size of array to 0.
     * <p>This method is similar to {@link #clear()} method defined in .</p>
     */
    public void removeAllElements() {
        clear();
    }


    /**
     * Returns an array containing all elements of the stack.
     *
     * @return array representation of stack
     */
    public E[] toArray() {
        return (E[]) this.array;
    }


    /**
     * Method to retrieve the total number of elements in the stack.
     *
     * @return current size of stack
     */
    public int size() {
        return this.size;
    }


    /**
     * Method to retrieve the current capacity of the stack.
     *
     * @return current capacity of stack
     */
    public int capacity() {
        return this.capacity;
    }


    /**
     * Private method to get new array of double size of previous size with all elements of previous stack array when previous array is completely filled.
     *
     * @return new array to store stack elements
     */
    private Object[] getNewStackArray() {
        int newCapacity = 2 * this.capacity;
        this.capacity = newCapacity;
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(this.array, 0, newArray, 0, this.array.length);

        return newArray;
    }


    /**
     * Method to return the string representation of Stack.
     * <p>String returned is enclosed in square brackets ("[]").
     * Adjacent elements are separated by comma (", ").</p>
     * <p>If stack is empty then only double brackets will be returned.</p>
     *
     * @return String representation of the Stack
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size(); i++) {
            if (array[i] == null) {
                sb.append("null");
            } else {
                sb.append(array[i]);
            }
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");

        return sb.toString();
    }


    /**
     * Method returns the iterator over this stack.
     *
     * @return iterator over stack
     */
    @Override
    public Iterator<E> iterator() {
        return new StackIterator();
    }


    /**
     * Private class to implement iterator for this stack.
     */
    private class StackIterator implements Iterator<E> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public E next() {
            return (E) array[index++];
        }
    }
}