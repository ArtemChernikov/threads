package ru.job4j.linked;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 24.11.2022
 */
public final class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }
}
