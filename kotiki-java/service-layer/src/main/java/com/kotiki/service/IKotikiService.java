package com.kotiki.service;

import entities.Breed;
import entities.Cat;
import entities.Owner;

import java.util.GregorianCalendar;
import java.util.List;

public interface IKotikiService {
    public List<Cat> findAllCats();
    public List<Owner> findAllOwners();
    public List<Cat> findFriendsOfCat(String name);
    public List<Cat> findAllCatsOfOwner(String name);
    public Long createNewCat(String name, GregorianCalendar dateOfBirth, Breed breed, Long ownerId);
    public Long createNewOwner(String name, GregorianCalendar dateOfBirth);
}
