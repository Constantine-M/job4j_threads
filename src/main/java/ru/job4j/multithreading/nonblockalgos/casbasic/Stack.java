package ru.job4j.multithreading.nonblockalgos.casbasic;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Операции write и read по отдельности атомарны.
 * Но во многих случаях нам нужны действия
 * check-then-act. Чтобы  этого добиться,
 * нужно делать синхронизацию.
 *
 * <p>Синхронизация блокирует выполнение нитей,
 * то есть программа из многопоточной
 * превращается в однопоточную.
 *
 * <p>Процессоры на уровне ядра поддерживают
 * операцию compare-and-swap. Эта операция
 * атомарная. В Java есть структуры данных,
 * которые реализуют этот механизм.
 *
 * <p>Чтобы сделать этот класс потокобезопасным,
 * мы будем использовать так называемые
 * атомики из пакета {@link java.util.concurrent.atomic}.
 *
 * <p>Метод {@link AtomicReference#compareAndSet}
 * атомарный. Это значит если две нити прочитали
 * одно и то же значение ref, то первый
 * вызов compareAndSet даст true, а второй
 * вызов вернет false. Вторая нить будет заново
 * читать ref и выполнять операцию compareAndSet.
 * Обе нити не блокируются, а выполняются параллельно.
 */
@ThreadSafe
public class Stack<T> {

    private AtomicReference<Node<T>> head = new AtomicReference<>();

    public void push(T value) {
        Node<T> temp = new Node<>(value);
        Node<T> ref;
        do {
            ref = head.get();
            temp.next = ref;
        } while (!head.compareAndSet(ref, temp));
    }

    public T poll() {
        Node<T> ref;
        Node<T> temp;
        do {
            ref = head.get();
            if (ref == null) {
                throw new IllegalStateException("Stack is empty");
            }
            temp = ref.next;
        } while (!head.compareAndSet(ref, temp));
        ref.next = null;
        return ref.value;
    }

    private static final class Node<T> {
        final T value;

        Node<T> next;

        public Node(final T value) {
            this.value = value;
        }
    }
}
