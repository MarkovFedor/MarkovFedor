package dto;

import cats.Cat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class OwnerDTO {
    private Long id;
    private String name;
    private Calendar dateOfBirth;
    private List<Long> catsId;

    public OwnerDTO() {
        catsId = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

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

    public void setDateOfBirth(Calendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Long> getCatsId() {
        return catsId;
    }

    public void setCatsId(List<Cat> cats) {
        for(Cat cat:cats) {
            catsId.add(cat.getId());
        }
    }
}

