package com.kotiki.service;

import entities.Cat;
import dao.ICatsDao;
import com.kotiki.exceptions.NotFoundByIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import dao.IOwnerDao;
import entities.Owner;

import java.util.List;
import java.util.Optional;

@Service
@ComponentScan("common")
public class KotikiService {
    @Autowired
    private IOwnerDao ownerDao;

    @Autowired
    private ICatsDao catsDao;

    public KotikiService() {}

    public Cat findCatById(Long id) throws NotFoundByIdException {
        Cat cat = catsDao.findById(id)
                .orElse(null);
        if(cat == null) {
            throw new NotFoundByIdException("Не найден");
        }
        return cat;
    }

    public Owner findOwnerById(Long id) throws NotFoundByIdException {
        Owner owner = ownerDao.findById(id)
                .orElse(null);
        if(owner == null) {
            throw new NotFoundByIdException("Не найден");
        }
        return owner;
    }

    public Long saveNewCat(Cat cat) {
        catsDao.save(cat);
        System.out.println(cat.getName());
        return cat.getId();
    }

    public Long saveNewOwner(Owner owner) {
        ownerDao.save(owner);
        return owner.getId();
    }

    public void deleteCatById(Long id) {
        catsDao.deleteById(id);
    }

    public void deleteOwnerById(Long id) {
        ownerDao.deleteById(id);
    }

    public List<Cat> getAllCats() {
        List<Cat> cats = catsDao.findAll();
        return cats;
    }

    public List<Owner> getAllOwners() {
        List<Owner> owners = ownerDao.findAll();
        return owners;
    }

    public void deleteAllCats() {
        catsDao.deleteAll();
    }

    public void deleteAllOwners() {
        ownerDao.deleteAll();
    }

    public void addFriend(Long id, Long friendId) throws NotFoundByIdException {
        Optional<Cat> cat = catsDao.findById(id);
        Optional<Cat> friend = catsDao.findById(friendId);
        if(cat.isPresent() & friend.isPresent()) {
            cat.get().addFriend(friend.get());
        } else {
            throw new NotFoundByIdException("Not found lol ahahah");
        }
    }

    public void addCat(Long id, Long catId) throws NotFoundByIdException {
        Optional<Owner> owner = ownerDao.findById(id);
        Optional<Cat> cat = catsDao.findById(catId);
        if(owner.isPresent() & cat.isPresent()) {
            owner.get().addCat(cat.get());
        } else {
            throw new NotFoundByIdException("Not found lol ahahha");
        }
    }
}