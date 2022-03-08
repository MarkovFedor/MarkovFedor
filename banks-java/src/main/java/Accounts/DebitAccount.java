package Accounts;

import BankStructures.Bank;
import Clients.Client;

public class DebitAccount extends Account {
    private int _amount;

    public DebitAccount(Bank bank, Client owner)
    {
        super(bank, owner);
    }
    @Override
    public void WithdrawCommission() {
        return;
    }

    public int GetAmount() {
        return _amount;
    }
    @Override
    public void RaisePercents() {
        return;
    }

    public void Update() {
        return;
    }
    @Override
    public void Raise(int amount) throws Exception {
        if (amount > 0) {
            _amount += amount;
        } else {
            throw new Exception("Некорректная сумма");
        }
    }
    @Override
    public void Withdraw(int amount) throws Exception {
        if (amount > 0 && _amount - amount >= 0) {
            if (!GetOwner().IsReliable() && amount > GetBank().GetWithdrawLimit()) {
                throw new Exception("Неподтвержденный клиент списывает слишком много");
            }

            _amount -= amount;
            return;
        }

        throw new Exception("Невозможная транзакция");
    }
}
