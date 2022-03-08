package Entities;

import Accounts.Account;
import Clients.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountStorage {
    private Map<Client, List<Account>> _storage;

    public AccountStorage() {
        _storage = new HashMap<Client, List<Account>>();
    }

    public Map<Client, List<Account>> GetStorage() {
        return _storage;
    }

    public void AddClientAccount(Client client, Account account) {
        if (_storage.containsKey(client)) {
            _storage.get(client).add(account);
        } else {
            _storage.put(client, new ArrayList<Account>());
            _storage.get(client).add(account);
        }
    }
}