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
@Table(name= "Cats")
public class Cat extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="CAT_ID")
    private long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "DateOfBirth")
    private Calendar dateOfBirth;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(name = "Breed")
    @Enumerated(EnumType.STRING)
    private Breed breed;

    @ManyToMany
    private Set<Cat> friends = new HashSet<>();
    public Cat() {

    }
    public Cat(String name) {
        this();
        this.name = name;
    }
    public Cat(String name, Calendar dateOfBirth,Owner owner, Breed breed) {
        this();
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.owner = owner;
        this.breed = breed;
    }
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        this.name = name;
    }

    public Calendar getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Calendar _dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
        this.owner.addCat(this);
    }

    public Owner getOwner() {
        return owner;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed _breed) {
        this.breed = breed;
    }

    public Set<Cat> getFriends() {
        return friends;
    }

    public void addFriend(Cat friend) {
        friend.getFriends().add(this);
        friends.add(friend);
    }

    @Override
    public String toString() {
        return name;
    }
}
