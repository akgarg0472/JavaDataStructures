package com.akgarg.datastructures;

import java.util.ListIterator;

/**
 * Generic Double Linked List implementation with basic methods
 * like insertion, deletion, insertion and deletion at certain position and few more.
 * <p>This implementation is not synchronized (means no thread safety).</p>
 *
 * @param <E> the type of elements to store in this collection (class type only)
 * @author Akhilesh Garg
 */
public class DoubleLinkedList<E> implements Iterable<E> {

    // reference to hold the first node of the Linked List
    private Node<E> start;

    // reference pointer to keep track of the last node of the linked list
    private Node<E> pointer;

    // variable to hold the current size of the Linked List
    private int size;


    /**
     * Default Constructor to initialize required fields to their default values.
     */
    @SuppressWarnings("unused")
    public DoubleLinkedList() {
        start = null;
        pointer = null;
        size = 0;
    }


    /**
     * Constructor to initialize new Linked List with an existing Linked List.
     */
    @SuppressWarnings("unused")
    public DoubleLinkedList(DoubleLinkedList<E> linkedList) {
        this.start = linkedList.start;
        this.pointer = linkedList.pointer;
        this.size = linkedList.size();
    }


    /**
     * Append the element at the end of the Linked List.
     * This method is equivalent to {@link #addLast} method.
     *
     * @param element element to add in Linked List.
     */
    @SuppressWarnings("unused")
    public void add(E element) {
        addLast(element);
    }


    /**
     * Insert the specific element at the specific index in Linked List.
     *
     * @param index   index position to add new element.
     * @param element element to be added in the linked list.
     * @return true if element is successfully added otherwise returns false.
     */
    @SuppressWarnings("unused")
    public boolean add(int index, E element) {
        if (index < 0 || index > size - 1 || element == null) {
            return false;
        }

        if (index == size - 1) {
            return addLast(element);
        }

        if (index == 0) {
            return addFirst(element);
        }

        int count = 0;
        Node<E> node = start;

        while (count != index) {
            count++;
            node = node.getNext();
        }

        Node<E> newNode = new Node<>();
        newNode.setData(element);

        node.getPrevious().setNext(newNode);
        newNode.setNext(node);
        node.setPrevious(newNode);
        size++;

        return true;
    }


    /**
     * Method to retrieve and remove the head(first element) of the linked list.
     * This method is equivalent to {@link #removeFirst}.
     *
     * @return head element of the linked list.
     * @throws IllegalStateException if linked list is empty.
     */
    @SuppressWarnings("unused")
    public E remove() {
        return removeFirst();
    }


    /**
     * Method to remove the element from specified index in linked list.
     *
     * @param index index position of element to delete.
     * @return index element data which is deleted.
     * @throws IllegalStateException if linked list is empty.
     */
    @SuppressWarnings("unused")
    public E remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IllegalStateException("Invalid index value " + index);
        }

        if (index == size - 1) {
            return removeLast();
        }

        if (index == 0) {
            if (size == 1) {
                return removeSingle();
            }

            return removeFirst();
        }

        Node<E> node = start;
        int count = 0;

        while (count != index) {
            count++;
            node = node.getNext();
        }

        node.getPrevious().setNext(node.getNext());
        node.getNext().setPrevious(node.getPrevious());
        size--;

        return node.getData();
    }


    /**
     * Method to retrieve and remove the head of linked list.
     * This method is equivalent to {@link #remove}.
     *
     * @return Head element of the linked list.
     * @throws IllegalStateException if linked list is empty.
     */
    public E removeFirst() {
        if (start == null) {
            throw new IllegalStateException("Linked List is empty");
        }

        if (size == 1) {
            return removeSingle();
        }

        Node<E> node = start;
        start = start.getNext();
        start.setPrevious(null);
        size--;

        return node.getData();
    }


    /**
     * Method to retrieve and remove the last element from the linked list.
     *
     * @return Last element of the linked list.
     * @throws IllegalStateException if linked list is empty.
     */
    public E removeLast() {
        if (start == null) {
            throw new IllegalStateException("Linked List is empty");
        }

        if (size == 1) {
            return removeSingle();
        }

        E data = pointer.getData();
        pointer = pointer.getPrevious();
        pointer.setNext(null);
        size--;
        return data;
    }


    /**
     * Private method to remove the element from linked list only
     * when there is only a single element in the linked list.
     *
     * @return element removed from the linked list.
     */
    private E removeSingle() {
        E data = start.getData();
        start = null;
        pointer = null;
        size--;

        return data;
    }


    /**
     * Method to retrieve the element from linked list of specified index position.
     *
     * @param index index position to remove element.
     * @return deleted element of the specified index position.
     * @throws IndexOutOfBoundsException if index is not valid.
     */
    public E get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }

        int count = 0;
        Node<E> node = start;

        while (count != index) {
            count++;
            node = node.getNext();
        }

        return node.getData();
    }


    /**
     * Method to insert new element in the beginning of the linked list.
     *
     * @param element element to insert in the linked list.
     * @return true if insertion is successful or returns false.
     */
    public boolean addFirst(E element) {
        if (element == null) {
            return false;
        }

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
     * Method to insert new element at the end of the linked list.
     *
     * @param element element to insert in the linked list.
     * @return true if insertion is successful or returns false.
     */
    public boolean addLast(E element) {
        if (element == null) {
            return false;
        }

        if (start == null) {
            start = new Node<>(element);
            pointer = start;
            size++;
            return true;
        }

        Node<E> node = new Node<>(element);
        node.setPrevious(pointer);
        pointer.setNext(node);
        pointer = node;
        size++;
        return true;
    }


    /**
     * Method to retrieve the first element of linked list.
     *
     * @return first element of the linked list.
     * @throws IllegalStateException if linked list is empty.
     */
    @SuppressWarnings("unused")
    public E getFirst() {
        if (start == null) {
            throw new IllegalStateException("Linked List is Empty");
        }

        return start.getData();
    }


    /**
     * Method to retrieve the last element of linked list.
     *
     * @return last element of the linked list.
     * @throws IllegalStateException if linked list is empty.
     */
    @SuppressWarnings("unused")
    public E getLast() {
        if (pointer == null) {
            throw new IllegalStateException("Linked List is Empty");
        }

        return pointer.getData();
    }


    /**
     * Method to know if linked list is empty or not.
     *
     * @return true if linked list is empty otherwise returns false.
     */
    public boolean isEmpty() {
        return size() == 0;
    }


    /**
     * Method to fetch the current total number of elements available in the linked list.
     *
     * @return current size of the linked list.
     */
    public int size() {
        return this.size;
    }


    /**
     * Method to remove all elements from the linked list.
     */
    @SuppressWarnings("unused")
    public boolean clear() {
        if (start == null) {
            return false;
        }

        if (size == 1) {
            removeSingle();
        }

        Node<E> node = start;

        while (node != null) {
            node.setPrevious(null);
            node.setData(null);
            node = node.getNext();
            size--;
        }
        return true;
    }


    /**
     * Method to return the string representation of linked list.
     * String returned is enclosed in square brackets ("[]").
     * Adjacent elements are separated by comma (", ").
     * <p>If list is empty then only double brackets will be returned.</p>
     *
     * @return String representation of this linked list.
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < size; i++) {
            sb.append(get(i).toString());
            sb.append(", ");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");

        return sb.toString();
    }


    /**
     * Method used to return an iterator over the elements of this linked list.
     *
     * @return iterator over linked list.
     */
    @Override
    public ListIterator<E> iterator() {
        return new LinkedListIterator();
    }

    /**
     * Class used for the representation of each node of linked list.
     * <p>This class contains the data field, field for previous node address and field for next node address along with getters and setters for each field.</p>
     */
    private static class Node<E> {
        private Node<E> previous;
        private Node<E> next;
        private E data;

        Node() {
            this.data = null;
            this.next = null;
            this.previous = null;
        }

        Node(E data) {
            this.data = data;
            previous = null;
            next = null;
        }

        Node<E> getPrevious() {
            return previous;
        }

        void setPrevious(Node<E> previous) {
            this.previous = previous;
        }

        Node<E> getNext() {
            return next;
        }

        void setNext(Node<E> next) {
            this.next = next;
        }

        E getData() {
            return data;
        }

        void setData(E data) {
            this.data = data;
        }
    }

    /**
     * Class used to implement iterator functionality for this linked list.
     */
    private class LinkedListIterator implements ListIterator<E> {
        private Node<E> iterateForward = start;
        private Node<E> iterateBackward = pointer;

        @Override
        public boolean hasNext() {
            return iterateForward != null;
        }

        @Override
        public E next() {
            E data = iterateForward.getData();
            iterateForward = iterateForward.getNext();
            return data;
        }

        @Override
        public boolean hasPrevious() {
            return iterateBackward != null;
        }

        @Override
        public E previous() {
            E data = iterateBackward.getData();
            iterateBackward = iterateBackward.getPrevious();
            return data;
        }

        @Override
        public int nextIndex() {
            // for future implementation
            return 0;
        }

        @Override
        public int previousIndex() {
            // for future implementation
            return 0;
        }

        @Override
        public void remove() {
            // for future implementation
        }

        @Override
        public void set(E e) {
            // for future implementation
        }

        @Override
        public void add(E e) {
            // for future implementation
        }
    }
}