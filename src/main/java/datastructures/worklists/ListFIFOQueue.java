package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {

    private LinkedNode front;
    private LinkedNode back;
    private int size;

    // Creates a new ListFIFOQueue
    public ListFIFOQueue() {
        this.front = null;
        this.size = 0;
    }

    // Adds an item to the back of the queue
    @Override
    public void add(E work) {
        if (this.front == null) {
            this.front = new LinkedNode(work);
            this.back = this.front;
        }
        else {
            this.back.next = new LinkedNode(work);
            this.back = this.back.next;
        }
        this.size++;
    }

    // Returns the front-most item in the queue
    // An exception is thrown if the queue is empty
    @Override
    public E peek() {
        if (this.size == 0) {
            throw new NoSuchElementException("Queue is empty!");
        }
        return this.front.value;
    }

    // Removes the front-most item in the queue and returns it
    // An exception is thrown if the queue is empty
    @Override
    public E next() {
        if (this.size == 0) {
            throw new NoSuchElementException("Queue is empty!");
        }
        E data = this.front.value;
        this.front = this.front.next;
        this.size--;
        return data;
    }

    // Returns the size of the queue
    @Override
    public int size() {
        return this.size;
    }

    // Completely resets the queue
    @Override
    public void clear() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    public class LinkedNode {
        LinkedNode next;
        E value;
        public LinkedNode(LinkedNode next, E value) {
            this.value = value;
            this.next = next;
        }

        public LinkedNode(E value) {
            this.value = value;
            this.next = null;
        }
    }
}
