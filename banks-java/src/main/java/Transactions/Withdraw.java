package Transactions;

import Accounts.Account;
import BankStructures.Bank;
import Clients.Client;

public class Withdraw implements ITransactionStrategy {
    private Client _author;
    private int _amount;
    private Bank _bank;
    private int _idOfAccount;

    public Withdraw(Client author, int amount, Bank bank, int id) {
        _author = author;
        _amount = amount;
        _bank = bank;
        _idOfAccount = id;
    }

    public void Execute() throws Exception {
        Account account = _bank.GetAccountById(_idOfAccount);
        if (account.GetOwner().equals(_author)) {
            System.out.println(_author.GetId());
            account.Withdraw(_amount);
            return;
        }

        throw new Exception("Транзакция невозможна");
    }

    public Client GetAuthor() {
        return _author;
    }
}
