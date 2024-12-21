package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;

    Comparator<E> comparer;

    public MinFourHeap(Comparator<E> comparer) {
        this.data = (E[])new Object[10];
        this.size = 0;
        this.comparer = comparer;
    }

    @Override
    public boolean hasWork() {
        return this.size != 0;
    }

    @Override
    public void add(E work) {
        if (this.size == this.data.length) {
            this.data = expandArray(data);
        }
        this.data[this.size] = work;
        if (this.size != 0) {
            int parentIdx = (int) (Math.ceil(this.size / 4f) - 1);
            // val < parent
            if(comparer.compare(work, this.data[parentIdx]) < 0) {
                percolateUp(this.size);
            }
        }
        this.size++;
    }

    private E[] expandArray(E[] array) {
        E[] newArray = (E[])new Object[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }

    private void percolateUp(int i) {
        int parentIdx = (int) (Math.ceil(i / 4f) - 1);
        E val = this.data[i];
        while (i > 0 && comparer.compare(this.data[i], this.data[parentIdx]) < 0) {
            this.data[i] = this.data[parentIdx];
            this.data[parentIdx] = val;
            i = parentIdx;
            parentIdx = (int) (Math.ceil(i / 4f) - 1);
        }
    }

    @Override
    public E peek() {
        if (this.size == 0) {
            throw new NoSuchElementException("The heap is empty!");
        }
        return this.data[0];
    }

    @Override
    public E next() {
        E value = peek();
        this.data[0] = this.data[this.size - 1];
        this.size--;
        percolateDown(0);
        return value;
    }

    private void percolateDown(int i) {
        int first = (i * 4) + 1;
        int second = (i * 4) + 2;
        int third = (i * 4) + 3;
        int fourth = (i * 4) + 4;
        E val = this.data[i];
        while (first <= this.size) { //until location is a leaf
            //get toSwap variable to be smallest child
            int toSwap = first;
            if (fourth < this.size) {
                if (comparer.compare(this.data[toSwap], this.data[fourth]) > 0) {
                    toSwap = fourth;
                }
            }
            if (third < this.size) {
                if (comparer.compare(this.data[toSwap], this.data[third]) > 0) {
                    toSwap = third;
                }
            }
            if (second < this.size) {
                if (comparer.compare(this.data[toSwap], this.data[second]) > 0) {
                    toSwap = second;
                }
            } //toSwap is now the smallest existing child
            if (comparer.compare(this.data[toSwap], val) < 0) {
                this.data[i] = this.data[toSwap];
                this.data[toSwap] = val;
                i = toSwap;
                first = (i * 4) + 1;
                second = (i * 4) + 2;
                third = (i * 4) + 3;
                fourth = (i * 4) + 4;
            }
            else {
                break;
            }
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.data = (E[])new Object[10];
        this.size = 0;
    }
}
