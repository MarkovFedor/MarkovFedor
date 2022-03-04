package Transactions;

import Accounts.Account;
import BankStructures.Bank;
import Clients.Client;
import Transactions.ITransactionStrategy;

public class Transfer implements ITransactionStrategy {
    private Client _author;
    private Bank _withdrawBank;
    private Bank _raiseBank;
    private int _withdrawId;
    private int _raiseId;
    private int _amount;

    public Transfer(Client author, Bank withdrawBank, int withdrawId, Bank raiseBank, int raiseId, int amount) throws Exception {
        _author = author;
        _withdrawBank = withdrawBank;
        _raiseBank = raiseBank;
        _withdrawId = withdrawId;
        _raiseId = raiseId;
        _amount = amount;
        if (amount < 0) throw new Exception("Некорректная сумма перевода");
    }

    public void Execute() {
        try {
            Account withdrawAccount = _withdrawBank.GetAccountById(_withdrawId);
            Account raiseAccount = _raiseBank.GetAccountById(_raiseId);
            withdrawAccount.Withdraw(_amount);
            raiseAccount.Raise(_amount);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public Client GetAuthor() {
        return _author;
    }
}
