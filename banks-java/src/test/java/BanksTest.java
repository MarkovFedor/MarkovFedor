import Accounts.DebitAccount;
import BankStructures.Bank;
import BankStructures.CentralBank;
import Clients.Client;
import Clients.ClientBuilder;
import Transactions.Replenishment;
import Transactions.Withdraw;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static junit.framework.TestCase.assertEquals;

public class BanksTest {
    private CentralBank _centralBank;
    private ClientBuilder _clientBuilder;
    @Before
    public void setUp() throws Exception {
        _centralBank = new CentralBank();
        _clientBuilder = new ClientBuilder();
    }

    @Test
    public void Replaineshment() throws Exception {
        Bank tinkoff = _centralBank.CreateBank("Tinkoff");
        Bank sberbank = _centralBank.CreateBank("Sberbank");

        tinkoff.SetCommission(10);
        tinkoff.SetCreditLimit(5000);
        tinkoff.SetDailyDepositePercent(5);
        tinkoff.SetTransferLimit(1000);
        tinkoff.SetWithdrawLimit(1000);

        sberbank.SetCommission(10);
        sberbank.SetCreditLimit(5000);
        sberbank.SetDailyDepositePercent(5);
        sberbank.SetTransferLimit(1000);
        sberbank.SetWithdrawLimit(1000);
        Client Alex = _clientBuilder
                .AddName("Alex")
                .AddSurname("Milkovich")
                .Build();
        Client Fedor = _clientBuilder
                .AddName("Fedor")
                .AddSurname("Markov")
                .Build();
        _centralBank.RegisterClient(Alex);
        _centralBank.RegisterClient(Fedor);

        DebitAccount alexAccount = tinkoff.OpenDebitAccount(Alex);
        var fedorAccount = sberbank.OpenDebitAccount(Fedor);

        _centralBank.DoTransaction(new Replenishment(10000, Alex, tinkoff, alexAccount.GetId()));
        _centralBank.DoTransaction(new Replenishment(5000, Fedor, sberbank, fedorAccount.GetId()));
        assertEquals(10000, alexAccount.GetAmount());
        assertEquals(5000, fedorAccount.GetAmount());
    }

    @Test
    public void TryToWithdrawByReliableClient()
    {
        Bank tinkoff = _centralBank.CreateBank("Tinkoff");

        tinkoff.SetCommission(10);
        tinkoff.SetCreditLimit(5000);
        tinkoff.SetDailyDepositePercent(5);
        tinkoff.SetTransferLimit(1000);
        tinkoff.SetWithdrawLimit(1000);
        Client Alex = _clientBuilder
                .AddName("Alex")
                .AddSurname("Milkovich")
                .AddPassport("8-800-555-35-35")
                .AddAddress("Pomoyka 228")
                .Build();

        DebitAccount alexAccount = tinkoff.OpenDebitAccount(Alex);
        try {
            _centralBank.DoTransaction(new Replenishment(10000, Alex, tinkoff, alexAccount.GetId()));
            _centralBank.DoTransaction(new Withdraw(Alex, 2000, tinkoff, alexAccount.GetId()));
        }catch(Exception e) {
            System.out.println(e);
        }

        assertEquals(8000, alexAccount.GetAmount());
    }

    @Test
    public void TryWithdrawMoreThanHave() {
        Bank tinkoff = _centralBank.CreateBank("Tinkoff");

        tinkoff.SetCommission(10);
        tinkoff.SetCreditLimit(5000);
        tinkoff.SetDailyDepositePercent(5);
        tinkoff.SetTransferLimit(1000);
        tinkoff.SetWithdrawLimit(1000);
        Client Alex = _clientBuilder
                .AddName("Alex")
                .AddSurname("Milkovich")
                .AddPassport("8-800-555-35-35")
                .AddAddress("Pomoyka 228")
                .Build();

        DebitAccount alexAccount = tinkoff.OpenDebitAccount(Alex);
        assertThrows(Exception.class, () -> {
            _centralBank.DoTransaction(new Replenishment(10000, Alex, tinkoff, alexAccount.GetId()));
            _centralBank.DoTransaction(new Withdraw(Alex, 11000, tinkoff, alexAccount.GetId()));
        });
    }
}
