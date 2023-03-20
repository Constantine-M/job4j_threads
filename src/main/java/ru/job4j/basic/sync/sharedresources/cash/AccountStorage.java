package ru.job4j.basic.sync.sharedresources.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        if (!isExist(account)) {
            accounts.put(account.id(), new Account(account.id(), account.amount()));
            return true;
        }
        return false;
    }

    public boolean update(Account account) {
        if (isExist(account)) {
            add(account);
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        boolean result = false;
        return result;
    }

    public Optional<Account> getById(int id) {
        Optional<Account> account = Optional.ofNullable(accounts.get(id));
        if (account.isEmpty()) {
            throw new IllegalStateException(("Not found account by id = " + id));
        }
        return account;
        /*return Optional.ofNullable(accounts.get(id));*/
    }

    public boolean transfer(int fromId, int toId, int amount) {
        return false;
    }

    private boolean isExist(Account account) {
        return accounts.containsKey(account.id());
    }
}
