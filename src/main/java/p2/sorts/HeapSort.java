package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        MinFourHeap<E> heap = buildHeap(array, comparator);
        for (int i = 0; i < array.length; i++) {
            E item = heap.next();
            array[i] = item;
        }
    }

    public static <E> MinFourHeap<E> buildHeap(E[] array, Comparator<E> comparator) {
        MinFourHeap<E> newHeap = new MinFourHeap<>(comparator);
        for (E e : array) {
            newHeap.add(e);
        }
        return newHeap;
    }
}
