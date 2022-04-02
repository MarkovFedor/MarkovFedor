package com.kotiki.service;

import com.kotiki.exceptions.NotFoundByIdException;
import dao.ICatsDao;
import dao.IOwnerDao;
import entities.Breed;
import entities.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@ComponentScan("common")
public class CatsService {
    @Autowired
    private ICatsDao catsDao;

    @Autowired
    private IOwnerDao ownerDao;

    public Cat findCatById(Long id) throws NotFoundByIdException {
        Cat cat = catsDao.findById(id)
                .orElse(null);
        if(cat == null) {
            throw new NotFoundByIdException("Не найден");
        }
        return cat;
    }

    public Long saveNewCat(Cat cat) {
        catsDao.save(cat);
        System.out.println(cat.getName());
        return cat.getId();
    }

    public void deleteCatById(Long id) {
        catsDao.deleteById(id);
    }

    public List<Cat> getAllCats() {
        List<Cat> cats = catsDao.findAll();
        return cats;
    }

    public void deleteAllCats() {
        catsDao.deleteAll();
    }

    public void addFriend(Long id, Long friendId) throws NotFoundByIdException {
        Optional<Cat> cat = catsDao.findById(id);
        Optional<Cat> friend = catsDao.findById(friendId);
        if(cat.isPresent() & friend.isPresent()) {
            cat.get().addFriend(friend.get());
            catsDao.save(cat.get());
            catsDao.save(friend.get());
        } else {
            throw new NotFoundByIdException("Not found lol ahahah");
        }
    }

    public List<Cat> getAllCatsByBreed(Breed breed) {
        List<Cat> result = catsDao.findAllByBreed(breed);
        return result;
    }
}
