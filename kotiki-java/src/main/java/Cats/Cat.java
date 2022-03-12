package Cats;

import Common.BaseEntity;
import Owner.Owner;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Cats")
public class Cat extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="CAT_ID")
    private long _id;

    @Column(name = "Name")
    private String _name;

    @Column(name = "DateOfBirth")
    private Date _dateOfBirth;

    @Column(name = "Breed")
    private String _breed;
    @ManyToMany
    private Set<Cat> _friends;
    public Cat() {
        _friends = new HashSet<>();
    }
    public Cat(String name) {
        this();
        _name = name;
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

    public Date getDateOfBirth() {
        return _dateOfBirth;
    }

    public void setDateOfBirth(Date _dateOfBirth) {
        this._dateOfBirth = _dateOfBirth;
    }

    public String getBreed() {
        return _breed;
    }

    public void setBreed(String _breed) {
        this._breed = _breed;
    }

    public Set<Cat> getFriends() {
        return _friends;
    }

    public void setFriends(Set<Cat> _friends) {
        this._friends = _friends;
    }

    public void addFriend(Cat friend){
        friend.getFriends().add(this);
        _friends.add(friend);
    }

    @Override
    public String toString() {
        return _name;
    }
}
