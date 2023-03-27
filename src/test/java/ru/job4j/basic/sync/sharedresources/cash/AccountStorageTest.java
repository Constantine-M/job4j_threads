package ru.job4j.basic.sync.sharedresources.cash;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountStorageTest {

    @Test
    public void whenAdd() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(100);
    }

    @Test
    public void whenNotAdd() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        assertThat(storage.add(new Account(1, 200))).isFalse();
    }

    @Test
    public void whenUpdate() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.update(new Account(1, 200));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(200);
    }

    @Test
    public void whenDelete() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.delete(1);
        assertThat(storage.getById(1)).isEmpty();
    }

    @Test
    public void whenTransfer() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(0);
        assertThat(secondAccount.amount()).isEqualTo(200);
    }

    @Test
    public void whenDonorCanNotTransferThenFalse() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 99));
        storage.add(new Account(2, 100));
        assertThat(storage.transfer(1, 2, 100)).isFalse();
    }

    @Test
    public void whenReceiverDontExistThenFalse() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 1000));
        assertThat(storage.transfer(1, 2, 100)).isFalse();
    }
}