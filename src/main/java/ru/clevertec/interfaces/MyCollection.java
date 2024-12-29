package ru.clevertec.interfaces;

public interface MyCollection<E> {
    int size();

    boolean add(E e);

    boolean isEmpty();
}
