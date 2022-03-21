package entities;

import entities.Cat;
import entities.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
@Entity
@Table(name = "owner")
public class Owner extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "OWNER_ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DateOfBirth")
    private Calendar dateOfBirth;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private List<Cat> cats = new ArrayList<>();

    public List<Cat> getCats() {
        return cats;
    }

    public Owner() {
    }

    public Owner(String name, Calendar dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
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

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Calendar _dateOfBirth) {
        this.dateOfBirth = _dateOfBirth;
    }

    public void addCat(Cat cat) {
        if(!cats.contains(cat)) {
            cats.add(cat);
        }
    }
    public void setCats(List<Cat> _cats) {
        this.cats = _cats;
    }
}
