package lab06;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList <E> implements List, Iterable<E>{

    private int size;
    private Node head;
    private Node tail;


    public void DoublyLinkedList(){
        size = 0;
        head = null;
        tail = null;
    }

    /**
     * Inserts the specified element at the beginning of the list. O(1) for a
     * doubly-linked list.
     *
     * @param element
     */
    @Override
    public void addFirst(Object element) {
        Node newNode = new Node();
        newNode.data = (E) element;
        newNode.previous = null;
        if(size == 0){ // If the list doesn't have anything yet
            newNode.next = null;
            head = newNode;
            tail = newNode;
        }else{
            head.previous = newNode;
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    /**
     * Inserts the specified element at the end of the list. O(1) for a
     * doubly-linked list.
     *
     * @param o
     */
    @Override
    public void addLast(Object o) {
        if (size == 0) { // If we try to addLast on an empty LinkedList
            addFirst(o);
        } else {
            Node newNode = new Node();
            newNode.data = (E) o;
            newNode.next = null;
            newNode.previous = tail;
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    /**
     * Inserts the specified element at the specified position in the list. Throws
     * IndexOutOfBoundsException if index is out of range (index < 0 || index >
     * size()) O(N) for a doubly-linked list.
     *
     * @param index
     * @param element
     */
    @Override
    public void add(int index, Object element) throws IndexOutOfBoundsException {
        if(index > size || index < 0) {
            System.out.println("Index is out of bounds of the DoublyLinkedList size");
            throw new IndexOutOfBoundsException();
        }else if(index == 0) {
            addFirst(element);
        }else if(index == size){
            addLast(element);
        }else{
            Node newNode = new Node();
            newNode.data = (E) element;
            Node currentNode = head;
            int currentIdx = 0;
            while(currentIdx < index-1){ // Get the node where you want to add
                currentNode = currentNode.next;
                currentIdx++;
            }

            // Set the newNode's ties with adjacent nodes
            newNode.previous = currentNode;
            newNode.next     = currentNode.next;

            // Reset surrounding ties to newNode
            newNode.previous.next = newNode;
            newNode.next.previous = newNode;

            size++;
        }
    }

    /**
     * Returns the first element in the list. Throws NoSuchElementException if the
     * list is empty. O(1) for a doubly-linked list.
     */
    @Override
    public E getFirst() throws NoSuchElementException {
        if(head == null){
            System.out.println("Linked List is empty - no head to return");
            throw new NoSuchElementException();
        }
        return head.data;
    }

    /**
     * Returns the last element in the list. Throws NoSuchElementException if the
     * list is empty. O(1) for a doubly-linked list.
     */
    @Override
    public E getLast() throws NoSuchElementException {
        if(tail == null){
            System.out.println("Linked List has no last element - no tail to return");
            throw new NoSuchElementException();
        }
        return tail.data;
    }

    /**
     * Returns the element at the specified position in the list. Throws
     * IndexOutOfBoundsException if index is out of range (index < 0 || index >=
     * size()) O(N) for a doubly-linked list.
     *
     * @param index
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index >= size || index < 0) {
            System.out.println("Index out of range for List");
            throw new NoSuchElementException();
        }
        if (index == 0) {
            return getFirst();
        } else if (index == size - 1) {
            return getLast();
        } else {
            Node temp = head;
            for (int x = 0; x < index; x++) {
                temp = temp.next;
            }
            return temp.data;
        }
    }

    /**
     * Removes and returns the first element from the list. Throws
     * NoSuchElementException if the list is empty. O(1) for a doubly-linked list.
     */
    @Override
    public E removeFirst() throws NoSuchElementException {
        if(size == 0){
            System.out.println("Linked List has no elements");
            throw new NoSuchElementException();
        }else if(size == 1){
            E returnData = head.data;
            clear();
            return returnData;
        }else {
            E returnData = head.data;
            Node newHead = head.next; // Get the next Node
            newHead.previous = null;
            head = newHead; // Set
            size--;
            return returnData;
        }
    }

    /**
     * Removes and returns the last element from the list. Throws
     * NoSuchElementException if the list is empty. O(1) for a doubly-linked list.
     */
    @Override
    public E removeLast() throws NoSuchElementException {
        if(size == 0){
            System.out.println("Linked List has no elements");
            throw new NoSuchElementException();
        }else if(size == 1) {
            E returnData = head.data;
            clear();
            return returnData;
        }else{
            E returnData = tail.data;
            Node newTail = tail.previous;
            newTail.next = null; // Remove connection to the old tail
            tail = newTail;
            size--;
            return returnData;
        }
    }

    /**
     * Removes and returns the element at the specified position in the list. Throws
     * IndexOutOfBoundsException if index is out of range (index < 0 || index >=
     * size()) O(N) for a doubly-linked list.
     *
     * @param index
     */
    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if(index < 0 || index >= size){
            System.out.println("Index out of bounds for current list");
            throw new IndexOutOfBoundsException();
        }else if(index == 0){
            return removeFirst();
        }else if(index == size-1){
            return removeLast();
        }else{
            Node temp = head;
            int currentIdx = 0;
            while(index > currentIdx){
                temp = temp.next;
                currentIdx++;
            }
            temp.previous.next = temp.next;
            temp.next.previous = temp.previous;
            size--;
            return temp.data;
        }
    }

    /**
     * Returns the index of the first occurrence of the specified element in the
     * list, or -1 if this list does not contain the element. O(N) for a
     * doubly-linked list.
     *
     * @param element
     */
    @Override
    public int indexOf(Object element) {
        Node temp = head;
        for(int x = 0; x < size; x++){
            if(temp.data == (E) element){
                return x;
            }
            temp = temp.next;
        }
        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified element in this
     * list, or -1 if this list does not contain the element. O(N) for a
     * doubly-linked list.
     *
     * @param element
     */
    @Override
    public int lastIndexOf(Object element) {
        Node temp = tail;
        for(int x = size-1; x >= 0; x--){
            if(temp.data == (E) element){
                return x;
            }
            temp = temp.previous;
        }
        return -1;
    }

    /**
     * Returns the number of elements in this list. O(1) for a doubly-linked list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns true if this collection contains no elements. O(1) for a
     * doubly-linked list.
     */
    @Override
    public boolean isEmpty() {
        if(size == 0){
            return true;
        }
        return false;
    }

    /**
     * Removes all of the elements from this list. O(1) for a doubly-linked list.
     */
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence (from first to last element). O(N) for a doubly-linked list.
     */
    @Override
    public E[] toArray() {
        if(size == 0){
            return (E[]) new Object[0];
        }
        E[] array = (E[]) new Object[size];
        Node temp = head;
        array[0] = temp.data;
        for(int x = 1; x < size; x++) {
            temp = temp.next; // Index of temp.next is x
            array[x] = temp.data;
        }
        return array;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<E> iterator() {
        return new iteratorClass();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */

    public class iteratorClass implements Iterator<E> {
        private int position = 0;
        private boolean canRemove = false;


        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
           return(position < size);
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public E next() throws NoSuchElementException{
            if(this.hasNext()){
                Node temp = head;
                for(int x = 0; x < position; x++){
                    temp = temp.next;
                }
                canRemove = true;
                position++;
                return temp.data;
            }else{
                System.out.println("No next element to access - error thrown");
                throw new NoSuchElementException();
            }
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.
         * <p>
         * The behavior of an iterator is unspecified if the underlying collection
         * is modified while the iteration is in progress in any way other than by
         * calling this method, unless an overriding class has specified a
         * concurrent modification policy.
         * <p>
         * The behavior of an iterator is unspecified if this method is called
         * after a call to the {@link #forEachRemaining forEachRemaining} method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         * @implSpec The default implementation throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         */
        @Override
        public void remove() {
            if(!canRemove){
                System.out.println("No call to next has been made - IllegalStateException thrown");
                throw new IllegalStateException();
            }else{
                DoublyLinkedList.this.remove(position-1);
                position--;
                canRemove = false;
            }
        }
    }


    private class Node{
        E data;
        Node previous;
        Node next;

    }






}
