import Breeds.Breed;
import Cats.Cat;
import Cats.CatDao;
import Owners.Owner;
import Owners.OwnerDao;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.GregorianCalendar;

public class DataModule {
    private static SessionFactory _sessionFactory;
    public static void main(String[] args) {
        _sessionFactory = new Configuration().configure().buildSessionFactory();
        DataModule main = new DataModule();
        System.out.println("Adding some cats and owners in DB");

        Owner owner0 = new Owner("Миша", new GregorianCalendar(2002, 4, 22));
        Owner owner1 = new Owner("Володя", new GregorianCalendar(2001, 3, 10));

        Cat cat0 = new Cat("Пушистик", new GregorianCalendar(2018, 0,0),owner0, Breed.Abyssian);
        Cat cat1 = new Cat("Снежок", new GregorianCalendar(2020, 9,4),owner0, Breed.American_Shorthair);
        Cat cat2 = new Cat("Персик", new GregorianCalendar(2014, 10,22),owner0, Breed.Bombay);
        Cat cat3 = new Cat("Тигр", new GregorianCalendar(2015, 5,15),owner1, Breed.Birman);

        CatDao catDao = new CatDao();
        catDao.save(cat0);
        catDao.save(cat1);
        catDao.save(cat2);
        catDao.save(cat3);

        cat0.addFriend(cat3);
        cat0.addFriend(cat2);
        cat1.addFriend(cat2);
        catDao.merge(cat0);
        catDao.merge(cat1);
        catDao.merge(cat2);
        catDao.merge(cat3);

        OwnerDao ownerDao = new OwnerDao();
        ownerDao.save(owner0);
        ownerDao.save(owner1);
    }
}