package com.kotiki.service;

import entities.Cat;
import dao.ICatsDao;
import entities.BaseEntity;
import dao.IDao;
import com.kotiki.exceptions.NotFoundByIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import dao.IOwnerDao;
import entities.Owner;

import java.util.List;

@Service
@ComponentScan("common")
public class KotikiService {
    @Autowired
    private IDao daoBase;

    @Autowired
    private IOwnerDao ownerDao;

    @Autowired
    private ICatsDao catsDao;

    public KotikiService() {}

    public Cat findCatById(Long id) throws NotFoundByIdException {
        Cat cat = catsDao.find(id);
        if(cat == null) {
            throw new NotFoundByIdException("Не найден");
        }
        return cat;
    }

    public Owner findOwnerById(Long id) throws NotFoundByIdException {
        Owner owner = ownerDao.find(id);
        if(owner == null) {
            throw new NotFoundByIdException("Не найден");
        }
        return owner;
    }

    public Long saveNewEntity(BaseEntity entity) {
        daoBase.save(entity);
        return entity.getId();
    }

    public void deleteById(Long id) {
        daoBase.deleteById(id);
    }

    public List<Cat> getAllCats() {
        List<Cat> cats = catsDao.findAll();
        return cats;
    }

    public List<Owner> getAllOwners() {
        List<Owner> owners = ownerDao.findAll();
        return owners;
    }
}