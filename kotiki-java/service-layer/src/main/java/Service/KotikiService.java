package Service;

import Cats.Cat;
import Cats.CatDao;
import Owners.Owner;
import Owners.OwnerDao;

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
    public List<Cat> findFriendsOfCat(String name) {
        var cat = _catDao.findByName(name);
        return new ArrayList<>(cat.getFriends());
    }

    @Override
    public List<Cat> findAllCatsOfOwner(String name) {
        var owner = _ownerDao.findByName(name);
        return new ArrayList<>(owner.getCats());
    }
}
