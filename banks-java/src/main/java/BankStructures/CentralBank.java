package BankStructures;

import Clients.Client;
import Transactions.ITransactionStrategy;

import java.util.ArrayList;
import java.util.List;

public class CentralBank {
    private List<ITransactionStrategy> _transactionsHistory;
    private List<Bank> _banks;
    private List<Client> _registratedClients;

    public CentralBank() {
        _transactionsHistory = new ArrayList<ITransactionStrategy>();
        _registratedClients = new ArrayList<Client>();
        _banks = new ArrayList<Bank>();
    }

    public Bank CreateBank(String name) {
        var bank = new Bank(name);
        _banks.add(bank);
        return bank;
    }

    public void DoTransaction(ITransactionStrategy transaction) {
        _transactionsHistory.add(transaction);
        try {
            transaction.Execute();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public List<Client> GetRegistratedClients() {
        return _registratedClients;
    }

    public Client RegisterClient(Client client) {
        _registratedClients.add(client);
        return client;
    }

    public Client FindClient(String name, String surname) throws Exception {
        _registratedClients.stream().filter(client -> client.GetName() == name && client.GetSurname() == surname );

        throw new Exception("Нет такого клиента");
    }

    public Bank FindBank(String name) throws Exception {
        _banks.stream().filter(bank -> bank.GetName() == name);

        throw new Exception("Нет такого банка");
    }
}