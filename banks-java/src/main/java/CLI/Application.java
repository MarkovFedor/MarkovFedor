package CLI;

import BankStructures.Bank;
import BankStructures.CentralBank;
import Clients.Client;
import Clients.ClientBuilder;
import Transactions.Replenishment;
import Transactions.Transfer;
import Transactions.Withdraw;

import java.util.Scanner;

public class Application
{
    private CentralBank _centralBank;
    Scanner scanner;
    public Application()
    {
        scanner = new Scanner(System.in);
    }

    public void Start() throws Exception {
        System.out.println("INFO: Приложение запущено");
        Resume();
    }

    public void Reset()
    {
        _centralBank = null;
    }

    private void Resume() throws Exception {
        while (true)
        {
            System.out.print(">> ");
            String argument = scanner.nextLine();
            switch (argument)
            {
                case "/help":
                    Help();
                    break;
                case "/createcentralbank":
                    CentralBankCreate();
                    break;
                case "/createbank":
                    BankCreate();
                    break;
                case "/registerclient":
                    RegisterClient();
                    break;
                case "/editclient":
                    EditClient();
                    break;
                case "/editbank":
                    EditBank();
                    break;
                case "/transaction":
                    ProvideTransaction();
                    break;
            }
        }
    }

    private void CentralBankCreate()
    {
        _centralBank = new CentralBank();
    }

    private void BankCreate()
    {
        System.out.println("Введите название банка");
        System.out.print(">> ");
        String name = scanner.nextLine();
        Bank bank = _centralBank.CreateBank(name);
        System.out.println("Введите процент по депозиту");
        System.out.print(">> ");
        String depositePercent = scanner.nextLine();
        int percent = Integer.valueOf(depositePercent);
        bank.SetDailyDepositePercent(percent / 30);
        System.out.println("Введите лимит на переводы");
        System.out.print(">> ");
        String limitString = scanner.nextLine();
        int limit = Integer.valueOf(limitString);
        bank.SetTransferLimit(limit);
        System.out.println("Введите комиссию");
        System.out.print(">> ");
        String commissionString = scanner.nextLine();
        int commission = Integer.valueOf(commissionString);
        bank.SetCommission(commission);
        System.out.println("Введите кредитный лимит");
        System.out.print(">> ");
        String creditLimitString = scanner.nextLine();
        int creditLimit = Integer.valueOf(creditLimitString);
        bank.SetCreditLimit(creditLimit);
        System.out.println("Введите лимит на снятие");
        System.out.print(">> ");
        String withdrawLimitString = scanner.nextLine();
        int withdrawLimit = Integer.valueOf(withdrawLimitString);
        bank.SetWithdrawLimit(withdrawLimit);
    }

    private void RegisterClient()
    {
        ClientBuilder builder = new ClientBuilder();
        System.out.println("Введите имя (обязательно");
        System.out.print(">> ");
        String name = scanner.nextLine();
        System.out.println("Введите фамилию(обязательно");
        System.out.print(">> ");
        String surname = scanner.nextLine();
        System.out.println("Введите адрес или нажмите enter");
        System.out.print(">> ");
        String address = scanner.nextLine();
        System.out.println("Введите паспорт или нажмите enter");
        System.out.print(">> ");
        String passport = scanner.nextLine();
        Client client = builder
                .AddName(name)
                .AddSurname(surname)
                .AddPassport(passport)
                .AddAddress(address)
                .Build();
        _centralBank.RegisterClient(client);
    }

    private void EditClient()
    {
        try {
            var builder = new ClientBuilder();
            System.out.println("Введите имя и фамилию клиента");
            System.out.print(">> ");
            String name = scanner.nextLine();
            System.out.print(">> ");
            String surname = scanner.nextLine();
            Client client = _centralBank.FindClient(name, surname);
            System.out.println("Добавьте пасспорт");
            System.out.print(">> ");
            String passport = scanner.nextLine();
            System.out.println("Добавьте адрес");
            System.out.print(">> ");
            String address = scanner.nextLine();
            client.SetAddress(address);
            client.SetPassport(passport);
            builder
                    .SetClient(client)
                    .AddAddress(address)
                    .AddPassport(passport)
                    .Build();
        }catch(Exception e) {
            System.out.println(e);
        }
    }

    private void EditBank() throws Exception {
        System.out.println("Выберите банк для редактирования");
        System.out.print(">> ");
        String name = scanner.nextLine();
        Bank bank = _centralBank.FindBank(name);
        System.out.println("Выберите поле для редактирования");
        System.out.println("1. Проценты на депозит");
        System.out.println("2. Лимит на перевод");
        System.out.println("3. Коммиссия");
        System.out.println("4. Лимит на кредит");
        System.out.println("5. Лимит на снятие");
        String fieldString = scanner.nextLine();
        int field = Integer.valueOf(fieldString);
        switch (field)
        {
            case 1:
                System.out.print(">> ");
                String depositeString = scanner.nextLine();
                int deposite = Integer.valueOf(depositeString);
                bank.SetDailyDepositePercent(deposite / 30);
                break;
            case 2:
                System.out.print(">> ");
                String limitString = scanner.nextLine();
                int limit = Integer.valueOf(limitString);
                bank.SetTransferLimit(limit);
                break;
            case 3:
                System.out.print(">> ");
                String commissionString = scanner.nextLine();
                int commission = Integer.valueOf(commissionString);
                bank.SetCommission(commission);
                break;
            case 4:
                System.out.print(">> ");
                String creditLimieString = scanner.nextLine();
                int creditLimit = Integer.valueOf(creditLimieString);
                bank.SetCreditLimit(creditLimit);
                break;
            case 5:
                System.out.print(">> ");
                String withdrawLimitString = scanner.nextLine();
                int withdrawLimit = Integer.valueOf(withdrawLimitString);
                bank.SetWithdrawLimit(withdrawLimit);
                break;
        }
    }

    private void ProvideTransaction()
    {
        System.out.println("Выберите тип транзации");
        System.out.println("1. Перевод");
        System.out.println("2. Снятие");
        System.out.println("3. Пополнение");
        System.out.print(">> ");
        String name;
        String surname;
        String bankName;
        Bank bank;
        String idString;
        int id;
        String bankNameRaise;
        Bank bankRaise;
        String amountString;
        String argumentString = scanner.nextLine();
        int argument = Integer.valueOf(argumentString);
        switch (argument)
        {
            case 1:
                try {
                    System.out.println("Введите имя и фамилию клмента от которого будет перевод");
                    System.out.print(">> ");
                    name = scanner.nextLine();
                    System.out.print(">> ");
                    surname = scanner.nextLine();
                    Client client = _centralBank.FindClient(name, surname);
                    System.out.println("Введите банк в котором счет снятия");
                    System.out.print(">> ");
                    bankName = scanner.nextLine();
                    bank = _centralBank.FindBank(bankName);
                    System.out.println("Введите счет для снятия");
                    System.out.print(">> ");
                    idString = scanner.nextLine();
                    id = Integer.valueOf(idString);
                    System.out.println("Введите банк в котором счет пополнения");
                    System.out.print(">> ");
                    bankNameRaise = scanner.nextLine();
                    bankRaise = _centralBank.FindBank(bankNameRaise);
                    System.out.println("Введите счет для пополнения");
                    System.out.print(">> ");
                    String idRaiseString = scanner.nextLine();
                    int idRaise = Integer.valueOf(idRaiseString);
                    System.out.println("Сумма перевода");
                    System.out.print(">> ");
                    amountString = scanner.nextLine();
                    int amount = Integer.valueOf(amountString);
                    _centralBank.DoTransaction(new Transfer(client, bank, id, bankRaise, idRaise, amount));
                }catch(Exception e) {
                    System.out.println(e);
                }
                break;
            case 2:
                try {
                    System.out.println("Введите имя и фамилию клмента который будет снимать");
                    System.out.print(">> ");
                    name = scanner.nextLine();
                    System.out.print(">> ");
                    surname = scanner.nextLine();
                    Client client = _centralBank.FindClient(name, surname);
                    System.out.println("Введите банк в котором счет снятия");
                    System.out.print(">> ");
                    bankName = scanner.nextLine();
                    bank = _centralBank.FindBank(bankName);
                    System.out.println("Введите счет для снятия");
                    System.out.print(">> ");
                    idString = scanner.nextLine();
                    id = Integer.valueOf(idString);
                    System.out.println("Сумма снятия");
                    System.out.print(">> ");
                    amountString = scanner.nextLine();
                    int amount = Integer.valueOf(amountString);
                    _centralBank.DoTransaction(new Withdraw(client, amount, bank, id));
                }catch(Exception e) {
                    System.out.println(e);
                }
                break;
            case 3:
                try {
                    System.out.println("Введите имя и фамилию клмента который будет пополнять");
                    System.out.print(">> ");
                    name = scanner.nextLine();
                    System.out.print(">> ");
                    surname = scanner.nextLine();
                    Client client = _centralBank.FindClient(name, surname);
                    System.out.println("Введите банк в котором счет пополнения");
                    System.out.print(">> ");
                    bankName = scanner.nextLine();
                    bank = _centralBank.FindBank(bankName);
                    System.out.println("Введите счет для пополнения");
                    System.out.print(">> ");
                    idString = scanner.nextLine();
                    id = Integer.valueOf(idString);
                    System.out.println("Сумма пополнения");
                    System.out.print(">> ");
                    amountString = scanner.nextLine();
                    int amount = Integer.valueOf(amountString);
                    _centralBank.DoTransaction(new Replenishment(amount, client, bank, id));
                }catch(Exception e) {
                    System.out.println(e);
                }
                break;
        }
    }

    private void Help()
    {
        System.out.println("/help                -показать меню help");
        System.out.println("/createcentralbank   -создать центральный банк");
        System.out.println("/createbank          -создать банк");
        System.out.println("/registerclient      -зарегистрировать клиента");
        System.out.println("/editclient          -редактировать клиента");
        System.out.println("/editbank            -редактировать банк");
        System.out.println("/transaction         -осуществить транзакцию");
    }
}