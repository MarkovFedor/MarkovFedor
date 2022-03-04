package Accounts;

import BankStructures.Bank;
import Clients.Client;

import java.time.LocalDate;

public class DepositeAccount extends Account {
    private int _amount;
    private int _percents;
    private int _startAmount;
    private LocalDate _endOfDeposite;
    private boolean _isEndOfDeposite;

    public DepositeAccount(Bank bank, Client owner, int percents, int startAmount)
    {
        super(bank, owner);
        _percents = percents;
        _startAmount = startAmount;
        _endOfDeposite = LocalDate.now().plusYears(1);
        DefineIsEndOfDeposite();
    }

    @Override
    public void Raise(int amount) {
        if (IsCorrectAmount(amount)) {
            _amount += amount;
        }
    }

    @Override
    public void Withdraw(int amount) throws Exception {
        if (IsCorrectAmount(amount) && amount <= _amount && _isEndOfDeposite) {
            if (!GetOwner().IsReliable()) {
                if (amount > GetBank().GetWithdrawLimit()) {
                    throw new Exception("Статус запрещает");
                } else {
                    _amount -= amount;
                }
            } else {
                _amount -= amount;
            }
        }
    }

    public void DailyUpdate() {
        return;
    }

    public void RaiseDailyPercents() {
        int percent = GetBank().GetDailyDepositePercent();
        _amount += _amount * percent;
    }

    @Override
    public void WithdrawCommission() {
        return;
    }

    @Override
    public void RaisePercents() {
        if (!_isEndOfDeposite) {
            _amount += _startAmount * _percents;
        }
    }

    private void DefineIsEndOfDeposite() {
        if (_endOfDeposite.isBefore(LocalDate.now())) {
            _isEndOfDeposite = true;
        } else {
            _isEndOfDeposite = false;
        }
    }

    private boolean IsCorrectAmount(int amount) {
        return amount > 0;
    }
}