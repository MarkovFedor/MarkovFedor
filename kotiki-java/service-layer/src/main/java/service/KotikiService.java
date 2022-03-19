package service;

import cats.Cat;
import cats.CatDao;
import owner.Owner;
import owner.OwnerDao;

import java.util.ArrayList;
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
    public List<Cat> findFriendsOfCat(Long id) {
        var cat = _catDao.findById(id);
        return new ArrayList<>(cat.getFriends());
    }

    @Override
    public List<Cat> findAllCatsOfOwner(String name) {
        var owner = _ownerDao.findByName(name);
        return new ArrayList<>(owner.getCats());
    }
}
