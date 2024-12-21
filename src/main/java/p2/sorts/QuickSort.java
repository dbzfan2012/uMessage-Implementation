package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Arrays;
import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        quickSort(array, comparator, 0, array.length - 1);
    }

    public static <E> void quickSort(E[] array, Comparator<E> comparator, int begin, int end) {
        int moveBegin = begin + 1;
        int moveEnd = end;
        if (begin < end) {
            while (moveBegin != moveEnd) { //pivot is at beginning
                if (comparator.compare(array[moveBegin], array[begin]) < 0) {
                    moveBegin++;
                } else {
                    E toSwap = array[moveEnd];
                    array[moveEnd] = array[moveBegin];
                    array[moveBegin] = toSwap;
                    moveEnd--;
                }
            }
            int swapIdx = moveEnd;
            if (comparator.compare(array[moveEnd], array[begin]) > 0) {
                swapIdx--;
            }
            E toSwap = array[swapIdx];
            array[swapIdx] = array[begin];
            array[begin] = toSwap;
            quickSort(array, comparator, begin, swapIdx - 1); //lessThanPivot
            quickSort(array, comparator, swapIdx + 1, end); //greaterThanPivot
        }
    }
}
