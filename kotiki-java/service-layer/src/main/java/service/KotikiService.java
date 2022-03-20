package service;

import breeds.Breed;
import cats.Cat;
import cats.CatDao;
import exceptions.NotFoundByIdException;
import owner.Owner;
import owner.OwnerDao;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class KotikiService implements IKotikiService{

    private final CatDao _catDao;
    private final OwnerDao _ownerDao;

    public KotikiService(CatDao catDao,OwnerDao ownerDao) {
        _catDao = catDao;
        _ownerDao = ownerDao;
    }

    @Override
    public List<Cat> findAllCats() {
        return _catDao.findAll();
    }

    @Override
    public List<Owner> findAllOwners() {
        return _ownerDao.findAll();
    }

    @Override
    public List<Cat> findFriendsOfCat(String name) {
        return null;
    }

    @Override
    public List<Cat> findFriendsOfCat(Long id) throws NotFoundByIdException {
        var cat = _catDao.find(id);
        if(cat == null) {
            throw new NotFoundByIdException("Owner или cat не найдены");
        }
        return new ArrayList<>(cat.getFriends());
    }

    @Override
    public List<Cat> findAllCatsOfOwner(String name) {
        var owner = _ownerDao.findByName(name);
        return new ArrayList<>(owner.getCats());
    }

    @Override
    public Long createNewCat(String name, GregorianCalendar dateOfBirth, Breed breed, Long ownerId) {
        return null;
    }

    @Override
    public Long createNewOwner(String name, GregorianCalendar dateOfBirth) {

    }

    public List<Cat> findAllCatsByCriteria(String name, Breed breed, GregorianCalendar dateOfBirth) {

    }

    public List<Owner> findAllOwnersByCriteria(String name, GregorianCalendar dateOfBirth) {

    }
}
