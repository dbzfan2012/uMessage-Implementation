package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {

    private E[] array;
    private int index;
    private int size;

    // Creates a new ArrayStack
    public ArrayStack() {
        this.array = (E[])new Object[10];
        this.index = 0;
        this.size = 0;
    }

    // Adds a new object to the stack
    @Override
    public void add(E work) {
        if (this.index == this.array.length) {
            this.array = expandArray(this.array);
        }
        this.array[this.index] = work;
        this.index++;
        this.size++;
    }

    // A new array is created that is twice the size of the original array with the same values
    private E[] expandArray(E[] array) {
        E[] newArray = (E[])new Object[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }

    // The next item to be removed off the stack is returned
    // An exception is thrown if the stack is empty
    @Override
    public E peek() {
        if (this.size == 0) {
            throw new NoSuchElementException("Stack is empty!");
        }
        return this.array[this.index - 1];
    }

    // The top item of the stack is removed and its value is returned
    // An exception is thrown if the stack is empty
    @Override
    public E next() {
        if (this.size == 0) {
            throw new NoSuchElementException("Stack is empty!");
        }
        E data = this.array[this.index - 1];
        this.array[this.index - 1] = null;
        this.index--;
        this.size--;
        return data;
    }

    // Returns the number of elements in the stack
    @Override
    public int size() {
        return this.size;
    }

    // Completely empties out the stack
    @Override
    public void clear() {
        this.array = (E[])new Object[10];
        this.index = 0;
        this.size = 0;
    }
}
