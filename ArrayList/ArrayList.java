package com.akgarg.datastructures;

import java.util.Iterator;


/**
 * Generic Double ArrayList implementation with basic methods
 * like insertion, deletion, insertion, deletion and value update at certain position & few more generally used methods.
 * <p>This implementation is not synchronized (means no safety in multithreading environment).</p>
 *
 * @param <E> the type of elements to store in this ArrayList (class type only)
 * @author Akhilesh Garg
 */
@SuppressWarnings({"unused", "unchecked"})
public class ArrayList<E> implements Iterable<E> {

    private int size;       // variable to hold the current size of the ArrayList
    private int capacity;   // variable to hold the current capacity of the ArrayList (always greater than size)
    private static final int DEFAULT_CAPACITY = 10;    // variable for default capacity of the ArrayList if user is not providing default capacity
    private Object[] array;     // Object array to store generic types of values


    /**
     * Default constructor to initialize all required fields to their respective default values.
     */
    public ArrayList() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
    }


    /**
     * Constructor to set the initial capacity of the ArrayList and all other values to their respective default values.
     *
     * @param initialCapacity initial capacity of the ArrayList.
     * @throws IllegalArgumentException if initial capacity is less than 1.
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.array = new Object[initialCapacity];
            this.capacity = initialCapacity;
            this.size = 0;
        } else {
            throw new IllegalArgumentException("Invalid initial capacity " + initialCapacity);
        }
    }


    /**
     * Method to add an element at the end of the ArrayList.
     *
     * @param element element to be append at the end of the ArrayList.
     */
    public void add(E element) {
        if (this.size == capacity) {
            this.array = getNewArray();
        }
        this.array[size] = element;
        this.size++;
    }


    /**
     * Method to add new element in the ArrayList at specific index position.
     *
     * @param index   index position of new element.
     * @param element element to add in ArrayList.
     * @throws IndexOutOfBoundsException if input index position is less than 0 or index value is higher than size of ArrayList.
     */
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of range");
        }

        if (this.size == capacity) {
            this.array = getNewArray();
        }

        int count = 0;

        while (count != index) {
            count++;
        }

        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }

        array[count] = element;
        this.size++;
    }


    /**
     * Private method to obtain new array when size of ArrayList becomes equal to it's capacity.
     *
     * @return New array of larger than previous capacity with all elements.
     */
    private Object[] getNewArray() {
        this.capacity = (int) (this.capacity * 1.5 + 1);
        Object[] newArray = new Object[capacity];
        if (this.size >= 0) {
            System.arraycopy(this.array, 0, newArray, 0, this.size);
        }
        return newArray;
    }


    /**
     * Method to update the value of specified index with new value.
     *
     * @param index   index position of value to update.
     * @param element new element to place at index provided.
     * @return previous value at given index.
     * @throws IndexOutOfBoundsException if index provided is less than 0 or greater than size-1.
     */
    public E set(int index, E element) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Invalid Index " + index);
        }

        E data = (E) this.array[index];
        this.array[index] = element;

        return data;
    }


    /**
     * Method to get the array representation of the ArrayList
     *
     * @return Array containing ArrayList data.
     */
    public Object[] toArray() {
        return this.array;
    }


    /**
     * Method to retrieve the element at specified index in the ArrayList.
     *
     * @param index index to fetch the respective index value.
     * @return return the value at specified index.
     * @throws IndexOutOfBoundsException if index provided is less than 0 or greater than size-1.
     */
    public E get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        return (E) array[index];
    }


    /**
     * Method to get the index of the specific element.
     *
     * @param object object whose index is to find.
     * @return index position if element is present in the ArrayList or returns -1 if element is not in ArrayList.
     */
    public int indexOf(Object object) {
        if (object == null) {
            for (int i = 0; i < size(); i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        }

        for (int i = 0; i < size(); i++) {
            if (array[i] != null && array[i].equals(object)) {
                return i;
            }
        }

        return -1;
    }


    /**
     * Method to determine if an element is available in ArrayList or not.
     *
     * @param object object to check if this is available in ArrayList or not.
     * @return true or false according to the element present or not in ArrayList.
     */
    public boolean contains(Object object) {
        int index = -1;

        if (object == null) {
            for (int i = 0; i < size(); i++) {
                if (array[i] == null) {
                    index = i;
                    break;
                }
            }
        }

        for (int i = 0; i < size(); i++) {
            if (array[i] != null) {
                if (array[i].equals(object)) {
                    index = i;
                    break;
                }
            }
        }

        return index >= 0;
    }


    /**
     * Method to get the last occurrence index of provided element.
     *
     * @param object object whose last occurrence index is to find.
     * @return last occurrence index of object if object is present or return -1 if element not available in ArrayList.
     */
    public int lastIndexOf(Object object) {
        if (object == null) {
            for (int i = this.size - 1; i >= 0; i--) {
                if (array[i] == null) {
                    return i;
                }
            }
        }

        for (int i = this.size - 1; i >= 0; i--) {
            if (array[i] != null && array[i].equals(object)) {
                return i;
            }
        }

        return -1;
    }


    /**
     * Method to check if ArrayList is empty or not.
     *
     * @return true if ArrayList is empty else returns false.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }


    /**
     * Method to remove all elements from the ArrayList.
     */
    public void clear() {
        this.size = 0;
        this.array = null;
        this.array = new Object[DEFAULT_CAPACITY];
        this.capacity = DEFAULT_CAPACITY;
    }


    /**
     * Method to retrieve the current total number of elements in the ArrayList.
     *
     * @return size of the ArrayList
     */
    public int size() {
        return this.size;
    }


    /**
     * Method to return the string representation of ArrayList.
     * String returned is enclosed in square brackets ("[]").
     * Adjacent elements are separated by comma (", ").
     * If list is empty then only double brackets will be returned.
     *
     * @return String representation of the ArrayList
     */
    @Override
    public String toString() {
        if (this.size == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (array[i] == null) {
                sb.append("null, ");
                continue;
            }
            sb.append(array[i].toString());
            sb.append(", ");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }


    /**
     * Method to get the iterator over this ArrayList.
     */
    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }


    /**
     * Class used to implement the iterator.
     */
    private class ArrayListIterator implements Iterator<E> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return this.index < size;
        }

        @Override
        public E next() {
            return (E) array[index++];
        }
    }
}