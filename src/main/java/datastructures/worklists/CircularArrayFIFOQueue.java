package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    private E[] array;
    private int index;
    private int remove;
    private int size;
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.array = (E[])new Comparable[capacity];
        this.index = 0;
        this.remove = 0;
        this.size = 0;
    }

    // A new object is added to the back of the queue
    // An exception is thrown if the queue exceeds capacity
    @Override
    public void add(E work) {
        if (this.size == this.array.length) {
            throw new IllegalStateException("The queue exceeded capacity!");
        }
        this.index = this.index % this.array.length;
        this.array[this.index] = work;
        this.index++;
        this.size++;
    }

    // The front-most item in the queue is returned
    // An exception is thrown if the queue is empty
    @Override
    public E peek() {
        if (this.size == 0) {
            throw new NoSuchElementException("The queue is empty!");
        }
        this.remove = this.remove % this.array.length;
        return this.array[this.remove];
    }

    // The item at a given index from the queue is returned
    // An exception is thrown if the queue is empty or the index is invalid
    @Override
    public E peek(int i) {
        if (this.size == 0) {
            throw new NoSuchElementException("The queue is empty!");
        }
        int newI = (i + this.remove) % this.array.length;
        if (newI >= this.array.length || newI < 0) {
            throw new IndexOutOfBoundsException("The index is invalid!");
        }
        return this.array[newI];
    }

    // Removes the front-most item in the queue and returns it
    // An exception is thrown if the queue is empty
    @Override
    public E next() {
        if (this.size == 0) {
            throw new NoSuchElementException("The queue is empty!");
        }
        this.remove = this.remove % this.array.length;
        E value = this.array[this.remove];
        //this.array[this.remove] = null;
        this.remove++;
        this.size--;
        return value;
    }

    // Updates the queue at the given index with a given value
    // An exception is thrown if the index is invalid
    @Override
    public void update(int i, E value) {
        if (this.size == 0) {
            throw new NoSuchElementException("The queue is empty!");
        }
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("This is out of bounds of the array");
        }
        int newI = (i + this.remove) % this.array.length;
        if (newI >= this.array.length || newI < 0 || this.array[newI] == null) {
            throw new IndexOutOfBoundsException("The index is invalid!");
        }
        this.array[newI] = value;
    }

    // Returns the size of the array
    @Override
    public int size() {
        return this.size;
    }

    // Completely resets the queue
    @Override
    public void clear() {
        int capacity = this.array.length;
        this.array = (E[])new Object[capacity];
        this.index = 0;
        this.remove = 0;
        this.size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        if (this.size != 0 && other.size() != 0) {
            int limit = Math.min(this.size, other.size());
            int j = this.remove % this.size;
            Iterator<E> iter = other.iterator();
            for (int i = 0; i < limit; i++) {
                E value = iter.next();
                int currCompare = this.array[j].compareTo(value);
                if (currCompare != 0) {
                    return currCompare;
                }
                j++;
            }
        }
        return this.size - other.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            // Uncomment the line below for p2 when you implement equals
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            return this.compareTo(other) == 0;
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        return hashCode(this.remove % this.array.length);
    }

    public int hashCode(int i) {
        if (i >= this.size) {
            return i;
        }
        int result = this.array[i % this.array.length].hashCode();
        return 31 * i * result + i + hashCode(i + 1) * 37;
    }
}
