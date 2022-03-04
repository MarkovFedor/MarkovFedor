package Accounts;

import BankStructures.Bank;
import Clients.Client;
import Exceptions.CreditLimitException;

public class CreditAccount extends Account {
    private int _amount;
    private int _limit;
    private Boolean _isCommission;
    private int _commission;

    public CreditAccount(Bank bank, Client owner)
    {
        super(bank, owner);
        _commission = bank.GetCommission();
    }
    @Override
    public void Withdraw(int amount) throws CreditLimitException {
        if (_amount - amount < _limit) {
            throw new CreditLimitException("Превышен лимит");
        } else {
            _amount -= amount;
            CheckCommissionStatus();
        }
    }
    @Override
    public void Raise(int amount) {
        if (amount > 0) {
            _amount += amount;
        }

        CheckCommissionStatus();
    }

    public int GetAmount() {
        return _amount;
    }

    public int GetLimit() {
        return _limit;
    }

    public Boolean GetCommissionStatus() {
        return _isCommission;
    }

    public int GetCommission() {
        return _commission;
    }
    @Override
    public void RaisePercents() {
        return;
    }
    @Override
    public void WithdrawCommission() {
        if (_amount < 0) {
            _amount -= -_amount * _commission;
        }
    }

    private void CheckCommissionStatus() {
        if (_amount > 0) {
            _isCommission = false;
        } else {
            _isCommission = true;
        }
    }
}