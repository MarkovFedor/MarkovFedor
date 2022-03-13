package Owners;

import Cats.Cat;
import Common.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
@Entity
@Table(name = "Owners")
public class Owner extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "OWNER_ID")
    private long _id;

    @Column(name = "NAME")
    private String _name;

    @Column(name = "DateOfBirth")
    private Calendar _dateOfBirth;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private List<Cat> _cats = new ArrayList<>();

    public List<Cat> getCats() {
        return _cats;
    }

    public Owner() {
    }

    public Owner(String name, Calendar dateOfBirth) {
        _name = name;
        _dateOfBirth = dateOfBirth;
    }

    @Override
    public Long getId() {
        return _id;
    }

    @Override
    public void setId(Long id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public Calendar getDateOfBirth() {
        return _dateOfBirth;
    }

    public void setDateOfBirth(Calendar _dateOfBirth) {
        this._dateOfBirth = _dateOfBirth;
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
