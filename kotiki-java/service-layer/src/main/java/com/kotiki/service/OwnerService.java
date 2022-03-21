package com.kotiki.service;

import com.kotiki.exceptions.NotFoundByIdException;
import dao.ICatsDao;
import dao.IOwnerDao;
import entities.Cat;
import entities.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@ComponentScan("common")
public class OwnerService {
    @Autowired
    private IOwnerDao ownerDao;

    @Autowired
    private ICatsDao catsDao;

    public OwnerService() {}

    public Owner findOwnerById(Long id) throws NotFoundByIdException {
        Owner owner = ownerDao.findById(id)
                .orElse(null);
        if(owner == null) {
            throw new NotFoundByIdException("Не найден");
        }
        return owner;
    }

    public Long saveNewOwner(Owner owner) {
        ownerDao.save(owner);
        return owner.getId();
    }

    public void deleteOwnerById(Long id) {
        ownerDao.deleteById(id);
    }

    public List<Owner> getAllOwners() {
        List<Owner> owners = ownerDao.findAll();
        return owners;
    }

    public void deleteAllOwners() {
        ownerDao.deleteAll();
    }

    public void addCat(Long id, Long catId) throws NotFoundByIdException {
        Optional<Owner> owner = ownerDao.findById(id);
        Optional<Cat> cat = catsDao.findById(catId);
        if(owner.isPresent() & cat.isPresent()) {
            owner.get().addCat(cat.get());
            cat.get().setOwner(owner.get());
            catsDao.save(cat.get());
            ownerDao.save(owner.get());
        } else {
            throw new NotFoundByIdException("Not found lol ahahha");
        }
    }
}
