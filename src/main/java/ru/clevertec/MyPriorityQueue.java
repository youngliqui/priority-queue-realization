package ru.clevertec;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class MyPriorityQueue<E> implements MyQueue<E> {
    private static final int INITIAL_CAPACITY = 8;
    private Object[] array;
    private int size;
    private final Comparator<? super E> comparator;

    public MyPriorityQueue() {
        comparator = null;
        array = new Object[INITIAL_CAPACITY];
    }

    public MyPriorityQueue(Comparator<? super E> comparator) {
        this.comparator = comparator;
        array = new Object[INITIAL_CAPACITY];
    }

    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new IllegalArgumentException();
        }
        if (size == array.length) {
            resize();
        }
        if (size == 0) {
            array[0] = e;
        } else {
            siftUp(size, e);
        }
        size++;
        return true;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E peek() {
        return size != 0 ? (E) array[0] : null;
    }

    @Override
    public E pool() {
        if (size == 0) {
            return null;
        }
        E head = (E) array[0];
        E lastElement = (E) array[size - 1];
        array[size - 1] = null;
        size--;

        siftDown(0, lastElement);

        return head;
    }

    @Override
    public int size() {
        return size;
    }

    public List<E> toList() {
        return (List<E>) Arrays.stream(array).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private void resize() {
        int newCapacity = array.length * 2;
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size());
        array = newArray;
    }

    private void siftUp(int index, E element) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            E parent = (E) array[parentIndex];

            if (compare(element, parent) < 0) {
                array[index] = array[parentIndex];
                array[parentIndex] = element;
                index = parentIndex;
            } else {
                array[index] = element;
                break;
            }
        }
    }

    private void siftDown(int index, E inserted) {
        array[index] = inserted;
        int half = size / 2;

        while (index < half) {
            int leftChildIndex = index * 2 + 1;
            int rightChildIndex = leftChildIndex + 1;
            E leftChild = (E) array[leftChildIndex];
            if (rightChildIndex < size && compare((E) array[rightChildIndex], leftChild) < 0) {
                E rightChild = (E) array[rightChildIndex];
                if (compare(rightChild, (E) array[index]) < 0) {
                    swap(index, rightChildIndex);
                    index = rightChildIndex;
                }
            } else if (compare(leftChild, (E) array[index]) < 0) {
                swap(index, leftChildIndex);
                index = leftChildIndex;
            } else {
                break;
            }
        }
    }

    private int compare(E first, E second) {
        if (comparator != null) {
            return comparator.compare(first, second);
        }
        if (first instanceof Comparable) {
            return ((Comparable<E>) first).compareTo(second);
        }
        throw new IllegalArgumentException("Elements must be comparable or a comparator must be provided");
    }

    private void swap(int firstIndex, int secondIndex) {
        Object temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }
}
