package Transactions;

import Accounts.Account;
import BankStructures.Bank;
import Clients.Client;

import java.util.concurrent.ExecutionException;

public class Replenishment implements ITransactionStrategy {
    private int _amount;
    private Client _author;
    private Bank _bank;
    private int _id;

    public Replenishment(int amount, Client author, Bank bank, int id) throws Exception {
        if (amount < 0) throw new Exception("Недопустимая сумма");
        _amount = amount;
        _author = author;
        _bank = bank;
        _id = id;
    }

    public void Execute() {
        try {
            Account account = _bank.GetAccountById(_id);
            account.Raise(_amount);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public Client GetAuthor() {
        return _author;
    }
}