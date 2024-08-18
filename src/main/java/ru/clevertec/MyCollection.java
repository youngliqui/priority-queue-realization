package ru.clevertec;

public interface MyCollection<E> {
    int size();

    boolean add(E e);

    boolean isEmpty();
}
