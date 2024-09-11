package ru.clevertec.implementation;

import ru.clevertec.interfaces.MyQueue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class MyPriorityQueue<E> implements MyQueue<E> {
    private static final int INITIAL_CAPACITY = 8;
    private E[] array;
    private int size;
    private final Comparator<? super E> comparator;

    public MyPriorityQueue() {
        comparator = (Comparator<? super E>) Comparator.naturalOrder();
        array = (E[]) new Object[INITIAL_CAPACITY];
    }

    public MyPriorityQueue(Comparator<? super E> comparator) {
        this.comparator = comparator;
        array = (E[]) new Object[INITIAL_CAPACITY];
    }

    @Override
    public boolean add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("the element can not be null");
        }
        if (size == array.length) {
            resize();
        }

        int newElementIndex = size;
        array[newElementIndex] = element;
        size++;
        siftUp(newElementIndex, element);

        return true;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E peek() {
        return size != 0 ? array[0] : null;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }
        E head = array[0];
        E lastElement = array[size - 1];
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
        return Arrays.stream(array).collect(Collectors.toList());
    }

    private void resize() {
        int newCapacity = array.length * 2;
        E[] newArray = (E[]) new Object[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    private void siftUp(int index, E element) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            E parent = array[parentIndex];

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

    private void siftDown(int index, E insertedElement) {
        array[index] = insertedElement;
        int half = size / 2;

        while (index < half) {
            int leftChildIndex = index * 2 + 1;
            int rightChildIndex = leftChildIndex + 1;

            if (rightChildIndex < size && shouldSwapWithRightChild(index, leftChildIndex, rightChildIndex)) {
                index = swapAndReturnNewIndex(index, rightChildIndex);

            } else if (shouldSwapWithLeftChild(index, leftChildIndex)) {
                index = swapAndReturnNewIndex(index, leftChildIndex);
            }
        }
    }

    private boolean shouldSwapWithRightChild(int index, int leftChildIndex, int rightChildIndex) {
        E leftChild = array[leftChildIndex];
        E rightChild = array[rightChildIndex];
        return compare(rightChild, leftChild) < 0 && compare(rightChild, array[index]) < 0;
    }

    private boolean shouldSwapWithLeftChild(int index, int leftChildIndex) {
        E leftChild = array[leftChildIndex];
        return compare(leftChild, array[index]) < 0;
    }

    private int swapAndReturnNewIndex(int index, int childIndex) {
        swap(index, childIndex);
        return childIndex;
    }

    private int compare(E first, E second) {
        return comparator.compare(first, second);
    }

    private void swap(int firstIndex, int secondIndex) {
        E temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }
}
