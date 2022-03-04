package Clients;

public class ClientBuilder implements IBuilder {
    private Client _client;

    public ClientBuilder() {
        Create();
    }

    public IBuilder SetClient(Client client) {
        _client = client;
        return this;
    }

    public IBuilder Create() {
        _client = new Client();
        return this;
    }

    public IBuilder AddName(String name) {
        _client.SetName(name);
        return this;
    }

    public IBuilder AddSurname(String surname) {
        _client.SetSurname(surname);
        return this;
    }

    public IBuilder AddAddress(String address) {
        _client.SetAddress(address);
        return this;
    }

    public IBuilder AddPassport(String passport) {
        _client.SetPassport(passport);
        return this;
    }

    public Client Build() {
        Client result = _client;
        Create();

        return result;
    }
}
