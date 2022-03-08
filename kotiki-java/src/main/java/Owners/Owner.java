package Owners;

import Cats.Cat;

import java.util.Date;
import java.util.List;

public class Owner {
    private String _name;
    private Date _dateOfBirth;
    private List<Cat> _cats;

    public Owner() {
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public Date getDateOfBirth() {
        return _dateOfBirth;
    }

    public void setDateOfBirth(Date _dateOfBirth) {
        this._dateOfBirth = _dateOfBirth;
    }

    public List<Cat> getCats() {
        return _cats;
    }

    public void addCat(Cat cat) {
        if(!_cats.contains(cat)) {
            _cats.add(cat);
        }
    }
    public void setCats(List<Cat> _cats) {
        this._cats = _cats;
    }
}
