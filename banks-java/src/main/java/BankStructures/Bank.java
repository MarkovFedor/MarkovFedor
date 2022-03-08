package BankStructures;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.DebitAccount;
import Accounts.DepositeAccount;
import Clients.Client;
import Entities.AccountStorage;
import com.sun.jdi.Value;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bank
{
    private static int iD = 0;
    private int _dailyDepositePercent;
    private int _transferLimit;
    private int _commission;
    private String _name;
    private int _creditLimit;
    private int _withdrawLimit;
    private AccountStorage _accounts;
    private int _id;
    public Bank(String name)
    {
        _id = iD++;
        _name = name;
        _accounts = new AccountStorage();
    }

    public int GetId()
    {
        return _id;
    }

    public String GetName()
    {
        return _name;
    }

    public void SetCreditLimit(int creditLimit)
    {
        _creditLimit = creditLimit;
    }

    public int GetCreditLimit()
    {
        return _creditLimit;
    }

    public void SetWithdrawLimit(int limit)
    {
        if (limit > 0)
        {
            _withdrawLimit = limit;
        }
    }

    public int GetWithdrawLimit()
    {
        return _withdrawLimit;
    }

    public int GetDailyDepositePercent()
    {
        return _dailyDepositePercent;
    }

    public void SetDailyDepositePercent(int percent)
    {
        _dailyDepositePercent = percent;
    }

    public Account GetAccountById(int id) throws Exception {
        System.out.println(id);
        for(Map.Entry<Client, List<Account>> entry: _accounts.GetStorage().entrySet()) {
            for (Account account : entry.getValue()) {
                if(account.GetId() == id) {
                    return account;
                }
            }
        }
        throw new Exception("Нет такого id");
    }

    public DepositeAccount OpenDepositAccount(Client client, int amount)
    {
        DepositeAccount account;
        account = new DepositeAccount(this, client, _dailyDepositePercent, amount);
        _accounts.AddClientAccount(client, account);
        return account;
    }

    public CreditAccount OpenCreditAccount(Client client)
    {
        var account = new CreditAccount(this, client);
        _accounts.AddClientAccount(client, account);
        return account;
    }

    public void SetTransferLimit(int limit)
    {
        if (limit > 0)
        {
            _transferLimit = limit;
        }
    }

    public int GetTransferLimit()
    {
        return _transferLimit;
    }

    public void SetCommission(int commission)
    {
        if (commission > 0)
        {
            _commission = commission;
        }
    }

    public int GetCommission()
    {
        return _commission;
    }

    public DebitAccount OpenDebitAccount(Client client)
    {
        var account = new DebitAccount(this, client);
        _accounts.AddClientAccount(client, account);
        return account;
    }
}
