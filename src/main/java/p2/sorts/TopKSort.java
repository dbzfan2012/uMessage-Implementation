package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }


    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        k = Math.min(array.length, Math.max(1, k)); //ensures k is between 1 and array length
        MinFourHeap<E> newHeap = buildHeap(array, comparator, k);
        for (int i = 0; i < array.length; i++) {
            if (newHeap.size() == 0) {
                array[i] = null;
            }
            else {
                array[i] = newHeap.next();
            }
        }
    }

    public static <E> MinFourHeap<E> buildHeap(E[] array, Comparator<E> comparator, int k) {
        MinFourHeap<E> newHeap = new MinFourHeap<>(comparator);
        for (E e : array) {
            if (newHeap.size() == k) {
                if (comparator.compare(e, newHeap.peek()) > 0) {
                    newHeap.next();
                    newHeap.add(e);
                }
            }
            else {
                newHeap.add(e);
            }
        }
        return newHeap;
    }

}
