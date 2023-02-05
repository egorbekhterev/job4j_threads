package ru.job4j.thread.cache;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
public final class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean rsl = true;
        accounts.putIfAbsent(account.id(), account);
        if (accounts.get(account.id()) == null) {
            rsl = false;
        }
        return rsl;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), accounts.get(account.id()), account);
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id, accounts.get(id));
    }

    public synchronized Optional<Account> getById(int id) {
        Optional<Account> rsl = Optional.empty();
        for (Map.Entry<Integer, Account> entry : accounts.entrySet()) {
            if (entry.getKey().equals(id)) {
                rsl = Optional.of(entry.getValue());
                break;
            }
        }
        return rsl;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        var from = getById(fromId);
        var to = getById(toId);
        if (from.isPresent() && to.isPresent() && from.get().amount() - amount >= 0) {
            update(new Account(from.get().id(), from.get().amount() - amount));
            update(new Account(to.get().id(), to.get().amount() + amount));
            rsl = true;
        }
        return rsl;
    }
}