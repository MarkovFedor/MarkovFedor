package Clients;

public interface IBuilder
{
    IBuilder Create();
    IBuilder AddName(String name);
    IBuilder AddSurname(String surname);
    IBuilder AddPassport(String passport);
    IBuilder AddAddress(String address);
    Client Build();
}
