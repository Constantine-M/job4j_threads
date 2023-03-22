package ru.job4j.basic.sync.sharedresources.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("this")
    private final ConcurrentMap<Integer, Account> accounts = new ConcurrentHashMap<>();

    /**
     * Данный метод записывает учетную
     * запись в кеш.
     * Если с указанным id не связана
     * ни одна запись, то добавляем
     * эту запись.
     * Если возвращаемый счет != null,
     * то добавление произошло.
     * @param account лицевой счет пользователя.
     * @return true, если лиц счет был добавлен.
     */
    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) != null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), accounts.get(account.id()), account);
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id, accounts.get(id));
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> donor  = getById(fromId);
        Optional<Account> receiver = getById(toId);
        if (donor.isPresent() && receiver.isPresent()) {
            if (donor.get().amount() >= amount) {
                update(new Account(fromId, donor.get().amount() - amount));
                update(new Account(toId, receiver.get().amount() + amount));
                return true;
            }
        }
        return false;
    }
}
