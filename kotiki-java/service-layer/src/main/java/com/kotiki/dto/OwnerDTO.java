package com.kotiki.dto;

import entities.Cat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OwnerDTO {
    private Long id;
    private String name;
    private String dateOfBirth;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
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

