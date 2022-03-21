package service;

import cats.Cat;
import cats.ICatsDao;
import common.BaseEntity;
import common.IDao;
import exceptions.NotFoundByIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import owner.IOwnerDao;
import owner.Owner;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class KotikiService {

    @Autowired
    private IOwnerDao ownerDao;

    @Autowired
    private ICatsDao catsDao;

    @Autowired
    private IDao dao;

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
        dao.save(entity);
        return entity.getId();
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
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