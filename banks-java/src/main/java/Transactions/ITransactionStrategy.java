package Transactions;

import Clients.Client;

public interface ITransactionStrategy
{
    void Execute() throws Exception;
    Client GetAuthor();
}
