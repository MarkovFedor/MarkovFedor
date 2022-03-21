package com.kotiki.dto;

import entities.Breed;
import entities.Cat;

import java.util.*;

public class CatDTO {
    private Long id;
    private String name;
    private Breed breed;
    private Long ownerId;
    private Calendar dateOfBirth;
    private Set<Long> friendsId;

    public CatDTO() {
        friendsId = new HashSet<>();
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

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Set<Long> getFriendsId() {
        return friendsId;
    }

    public void setFriendsId(Set<Cat> friends) {
        for(Cat friend: friends) {
            friendsId.add(friend.getId());
        }
    }

    public Calendar getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Calendar dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }
}
