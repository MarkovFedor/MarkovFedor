package Cats;

import Breeds.Breed;
import Common.BaseEntity;
import Owners.Owner;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Calendar;
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
    private Calendar _dateOfBirth;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private Owner _owner;

    @Column(name = "Breed")
    @Enumerated(EnumType.STRING)
    private Breed _breed;

    @ManyToMany
    private Set<Cat> _friends = new HashSet<>();
    public Cat() {

    }
    public Cat(String name) {
        this();
        _name = name;
    }
    public Cat(String name, Calendar dateOfBirth,Owner owner, Breed breed) {
        this();
        _name = name;
        _dateOfBirth = dateOfBirth;
        _owner = owner;
        _breed = breed;
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

    public void setOwner(Owner owner) {
        _owner = owner;
        _owner.addCat(this);
    }

    public Owner getOwner() {
        return _owner;
    }

    public Breed getBreed() {
        return _breed;
    }

    public void setBreed(Breed _breed) {
        this._breed = _breed;
    }

    public Set<Cat> getFriends() {
        return _friends;
    }

    public void addFriend(Cat friend) {
        friend.getFriends().add(this);
        _friends.add(friend);
    }

    @Override
    public String toString() {
        return _name;
    }
}
