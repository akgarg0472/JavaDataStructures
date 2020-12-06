package com.akgarg.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Generic Single Linked List implementation with basic methods
 * like insertion, deletion, insertion and deletion at certain position and few more.
 * This implementation is not synchronized (means no thread safety)
 *
 * @param <E> the type of elements to store in this collection (class type only)
 * @author Akhilesh Garg
 */
public class SingleLinkedList<E> implements Iterable<E> {

    // Integer variable to hold the current size of the list
    private int size;

    // Node reference to hold the first node trace of list
    private Node<E> start = null;

    // Node reference to keep track of last node of list
    private Node<E> pointer = null;


    /**
     * Method to append the element at the end of the list
     *
     * @param element element to add in the list
     * @return true
     */
    public boolean add(E element) {
        if (start == null) {
            start = new Node<>(element);
            pointer = start;
        } else {
            pointer.setNext(new Node<>(element));
            pointer = pointer.getNext();
        }
        size++;
        return true;
    }


    /**
     * Method to insert the element at specified position in the Linked List
     *
     * @param index   position where to insert the new element
     * @param element element to be inserted in list
     * @return true if element is successfully inserted
     * @throws IndexOutOfBoundsException (if index<0 || index>=size)
     */
    public boolean add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }

        if (index == 0) {
            return addFirst(element);
        }

        if (index == size) {
            return addLast(element);
        }

        Node<E> current = null;
        Node<E> prev = null;
        int count = 0;

        if (start != null) {
            current = start;
            prev = start;
        }

        while (count != index) {
            current = current != null ? current.getNext() : null;

            if (count != 0) {
                prev = prev != null ? prev.getNext() : null;
            }
            count++;
        }

        size++;
        Node<E> newNode = new Node<>(element);
        if (prev != null) {
            prev.setNext(newNode);
        }
        newNode.setNext(current);

        return true;
    }


    /**
     * Method to add new element at beginning of the linked list
     *
     * @param element element to insert in the beginning
     * @return true
     */
    public boolean addFirst(E element) {
        if (start == null) {
            start = new Node<>(element);
            pointer = start;
            size++;
            return true;
        }

        Node<E> node = new Node<>(element);
        node.setNext(start);
        start = node;
        size++;
        return true;
    }


    /**
     * Method to add new element at the end of the Linked List
     *
     * @param element element to add in the list
     * @return true
     */
    public boolean addLast(E element) {
        if (start == null) {
            start = new Node<>(element);
            pointer = start;
            size++;
            return true;
        }

        pointer.setNext(new Node<>(element));
        pointer = pointer.getNext();
        size++;
        return true;
    }


    /**
     * Method to retrieve the element at certain index from Linked List
     *
     * @param index index of element to return
     * @return the element of the specified index
     * @throws NoSuchElementException    (if Linked List is empty)
     * @throws IndexOutOfBoundsException (if index<0 || index>=size())
     */
    public E get(int index) {
        if (size == 0) {
            throw new NoSuchElementException("Empty Linked List");
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }

        Node<E> node = start;
        int count = 0;

        while (count != index) {
            node = node.getNext();
            count++;
            if (node == null) {
                throw new IllegalArgumentException("Invalid index " + index);
            }
        }

        return node.getData();
    }


    /**
     * method to remove the first element from the list
     *
     * @return element removed from list
     * @throws NoSuchElementException (if linked list is empty)
     */
    public E remove() {
        if (start == null) {
            throw new NoSuchElementException("Linked List in Empty");
        }

        Node<E> element = start;
        start = start.getNext();
        size--;
        return element.getData();
    }


    /**
     * Method to remove an element from specific position from list.
     *
     * @param index position of element to delete
     * @return returns the element which is deleted
     * @throws NoSuchElementException    if list is empty
     * @throws IndexOutOfBoundsException if (index<0 || index>=size())
     */
    public E remove(int index) {
        if (size == 0) {
            throw new NoSuchElementException("Linked List is Empty");
        }

        if (index > size - 1) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }

        if (index == 0) {
            return remove();
        }

        if (index == size - 1) {
            int count = 0;
            Node<E> tempCurrent = start;
            Node<E> tempPrev = start;

            while (count != size - 1) {
                tempCurrent = tempCurrent.getNext();
                if (count != 0) {
                    tempPrev = tempPrev.getNext();
                }
                count++;
            }

            tempPrev.setNext(null);
            pointer = tempPrev;
            size--;

            return tempCurrent.getData();
        }

        Node<E> current;
        Node<E> prev;

        if (start != null) {
            current = start;
            prev = start;
        } else {
            return null;
        }

        int count = 0;  // variable to trace node location to delete

        while (count != index) {
            current = current.getNext();
            if (count != 0) {
                prev = prev.getNext();
            }
            count++;
        }

        Node<E> node = current;
        prev.setNext(current.getNext());
        size--;

        return node.getData();
    }


    /**
     * method to replace the element at the given index position with given element
     *
     * @param index   index position to replace element
     * @param element element to be stored.
     * @return previous data at the index position.
     * @throws NoSuchElementException    if linked list is empty
     * @throws IndexOutOfBoundsException if(index < 0 || index >= size)
     */
    public E set(int index, E element) {
        if (start == null) {
            throw new NoSuchElementException("Linked List is empty");
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }

        Node<E> node = start;
        int count = 0;

        while (count != index) {
            node = node.getNext();
            count++;
        }
        E previousElement = node.getData();
        node.setData(element);

        return previousElement;
    }


    /**
     * Returns the first element in the linked list
     *
     * @return returns the first element of the list.
     * @throws NoSuchElementException if linked list is empty.
     */
    public E getFirst() {
        if (start == null) {
            throw new NoSuchElementException("List is empty");
        }

        return start.getData();
    }


    /**
     * Returns the last element in the linked list
     *
     * @return returns the last element of the list.
     * @throws NoSuchElementException if linked list is empty.
     */
    public E getLast() {
        if (pointer == null) {
            throw new NoSuchElementException("List is empty");
        }
        return pointer.getData();
    }


    /**
     * Returns if linked list is empty or not.
     *
     * @return false if linked list is not empty,
     * true if linked list is empty.
     */
    public boolean isEmpty() {
        return size() == 0;
    }


    /**
     * Returns the total number of elements currently in the list
     */
    public int size() {
        return this.size;
    }


    /**
     * Method to return the string representation of linked list.
     * String returned is enclosed in square brackets ("[]").
     * Adjacent elements are seperated by comma (", ").
     * If list is empty then only double brackets will be returned.
     *
     * @return String representation of the linked list
     */
    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size(); i++) {
            result.append(get(i).toString());
            result.append(", ");
        }

        result.deleteCharAt(result.length() - 1);
        result.deleteCharAt(result.length() - 1);
        result.append("]");

        return result.toString();
    }


    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    /**
     * class representing each node of the linked list.
     * It contains data variable and certain methods which are used by Linked List
     * class to maintain the linked list.
     * Please don't modify this file
     */
    private static class Node<T> {
        private Node<T> next = null;
        private T data;

        Node(T data) {
            this.data = data;
        }

        T getData() {
            return this.data;
        }

        void setData(T data) {
            this.data = data;
        }

        Node<T> getNext() {
            return this.next;
        }

        void setNext(Node<T> node) {
            this.next = node;
        }
    }

    /**
     * class used to implement the iterator.
     */
    private class LinkedListIterator implements Iterator<E> {
        private Node<E> node = start;

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public E next() {
            Node<E> temp = node;
            node = node.getNext();
            return temp.getData();
        }
    }
}