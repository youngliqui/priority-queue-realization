package ru.clevertec.interfaces;

public interface MyQueue<E> extends MyCollection<E> {
    /**
     * Inserts the specified item into the queue.
     *
     * @param e - the element to add
     * @return true - if the operation is successful
     */
    boolean add(E e);

    /**
     * Retrieves but does not delete the head of the queue or returns null if the queue is empty
     *
     * @return the head of the queue or null if the queue is empty
     */
    E peek();

    /**
     * Retrieves and deletes the head of the queue or returns null if the queue is empty
     *
     * @return the head of the queue or null if the queue is empty
     */
    E poll();
}
