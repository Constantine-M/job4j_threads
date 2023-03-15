package ru.job4j.basic.sync;

/**
 * Данный класс описывает узел
 * односвязного списка.
 *
 * Чтобы сделать этот класс
 * иммутабельным (immutable),
 * достаточно поля обозначать
 * final и убрать методы, которые
 * влияли бы на состояние объекта.
 *
 * Инициализировать поля мы будем
 * через конструктор. Сеттеры
 * нужно убрать.
 *
 * Класс должен быть объявлен
 * как final.
 *
 * @param <T> любой объект.
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
