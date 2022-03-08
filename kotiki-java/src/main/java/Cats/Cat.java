package Cats;

import Owners.Owner;

import java.util.Date;
import java.util.List;

public class Cat {
    private String _name;
    private Date _dateOfBirth;
    private String _breed;
    private Owner _owner;
    private List<Cat> _friends;

    public Cat() {
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public Date get_dateOfBirth() {
        return _dateOfBirth;
    }

    public void set_dateOfBirth(Date _dateOfBirth) {
        this._dateOfBirth = _dateOfBirth;
    }

    public String get_breed() {
        return _breed;
    }

    public void set_breed(String _breed) {
        this._breed = _breed;
    }

    public Owner get_owner() {
        return _owner;
    }

    public void set_owner(Owner _owner) {
        this._owner = _owner;
    }

    public List<Cat> get_friends() {
        return _friends;
    }

    public void set_friends(List<Cat> _friends) {
        this._friends = _friends;
    }
}
