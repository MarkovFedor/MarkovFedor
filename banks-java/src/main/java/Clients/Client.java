package Clients;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private static int iD = 0;
    private String _name;
    private String _surname;
    private String _address;
    private String _passport;
    private boolean _reliable;
    private int _id;
    private List<String> _notifications;

    public Client() {
        _id = iD++;
        _notifications = new ArrayList<String>();
    }

    public void SetName(String name) {
        _name = name;
    }

    public void SetSurname(String surname) {
        _surname = surname;
    }

    public int GetId() {
        return _id;
    }

    public String GetName() {
        return _name;
    }

    public String GetSurname() {
        return _surname;
    }

    public String GetAddress() {
        return _address;
    }

    public String GetPassport() {
        return _passport;
    }

    public void SetAddress(String address) {
        _address = address;
        DefineStatus();
    }

    public void SetPassport(String passport) {
        _passport = passport;
        DefineStatus();
    }

    public boolean IsReliable() {
        return _reliable;
    }

    @Override
    public int hashCode() {
        return _id;
    }

    public List<String> GetNotifications() {
        return _notifications;
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }

    private void DefineStatus() {
        if (_address != null && _passport != null) {
            _reliable = true;
        } else {
            _reliable = false;
        }
    }
}
